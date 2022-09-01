package ru.vorobeij.regress.benchmark.reposotory.local.file

import java.io.File
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vorobeij.regress.benchmark.BenchmarkStatisticsProcessor
import ru.vorobeij.regress.benchmark.data.Benchmark
import ru.vorobeij.regress.benchmark.data.HistoricalBenchmark
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
            benchmarks
                .map { convert(deviceFingerprint, it, gitInfo) }
                .map { json.encodeToString(it) }
                .joinToString("\n")
        )
        storageFile.appendText("\n")
    }

    override fun getAll(
        deviceFingerprint: String,
        benchmarkName: String
    ): List<HistoricalBenchmark> = storageFile.readLines()
        .filter { it.isNotBlank() }
        .map { json.decodeFromString<HistoricalBenchmark>(it) }
        .distinctBy { it.commit }
        .filter { it.deviceFingerprint == deviceFingerprint && it.name == benchmarkName }

    private fun convert(
        deviceFingerprint: String,
        benchmark: Benchmark,
        gitInfo: GitInfo
    ): HistoricalBenchmark {
        val metrics = benchmark.metrics.timeNs ?: benchmark.metrics.summary!!
        val measurement = statisticsProcessor.getMeasurement(metrics.runs)
        return HistoricalBenchmark(
            gitInfo.commit,
            benchmark.name,
            gitInfo.author.email,
            gitInfo.commitDate.timeInMillis,
            gitInfo.message,
            gitInfo.branch,
            measurement.maximum,
            measurement.median,
            measurement.minimum,
            deviceFingerprint
        )
    }
}
