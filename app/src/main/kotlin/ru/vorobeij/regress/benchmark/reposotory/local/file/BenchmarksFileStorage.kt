package ru.vorobeij.regress.benchmark.reposotory.local.file

import java.io.File
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vorobeij.regress.benchmark.BenchmarkStatisticsProcessor
import ru.vorobeij.regress.benchmark.data.Benchmark
import ru.vorobeij.regress.benchmark.data.HistoricalBenchmark
import ru.vorobeij.regress.benchmark.data.fullName
import ru.vorobeij.regress.benchmark.reposotory.local.BenchmarksLocalStorage
import ru.vorobeij.regress.git.data.GitInfo

class BenchmarksFileStorage(
    private val storageFile: File,
    private val json: Json,
    private val statisticsProcessor: BenchmarkStatisticsProcessor
) : BenchmarksLocalStorage {

    init {
        storageFile.parentFile.mkdirs()
    }

    override fun clear() {
        storageFile.writeText("")
    }

    override fun save(
        benchmarks: List<Benchmark>,
        deviceFingerprint: String,
        gitInfo: GitInfo
    ) {
        storageFile.appendText(
            benchmarks.joinToString("\n") {
                json.encodeToString(convert(deviceFingerprint, it, gitInfo))
            }
        )
        storageFile.appendText("\n")
    }

    override fun getAll(
        deviceFingerprint: String,
        benchmarkName: String
    ): List<HistoricalBenchmark> {
        return if (storageFile.exists()) {
            storageFile.readLines()
                .filter { it.isNotBlank() }
                .map { json.decodeFromString<HistoricalBenchmark>(it) }
                .filter { it.deviceFingerprint == deviceFingerprint && it.fullName == benchmarkName }
                .distinctBy { it.commit }
        } else {
            emptyList()
        }
    }

    private fun convert(
        deviceFingerprint: String,
        benchmark: Benchmark,
        gitInfo: GitInfo
    ): HistoricalBenchmark {
        val metrics = benchmark.metrics.timeNs ?: benchmark.metrics.summary!!
        val measurement = statisticsProcessor.getMeasurement(metrics.runs)
        return HistoricalBenchmark(
            commit = gitInfo.commit,
            fullName = benchmark.fullName(),
            authorEmail = gitInfo.author.email,
            commitDate = gitInfo.commitDate.timeInMillis,
            message = gitInfo.message,
            branch = gitInfo.branch,
            maximum = measurement.maximum,
            median = measurement.median,
            minimum = measurement.minimum,
            deviceFingerprint = deviceFingerprint
        )
    }
}
