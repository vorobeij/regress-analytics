package ru.vorobeij.regress.implementation.local

import ru.vorobeij.core.BenchmarksRepository
import ru.vorobeij.core.data.Benchmark
import ru.vorobeij.core.data.BenchmarkAnalyticsResult
import ru.vorobeij.core.data.BenchmarkPlatformOutput
import ru.vorobeij.core.data.HistoricalBenchmark
import ru.vorobeij.core.data.Measurement
import ru.vorobeij.core.data.fullName
import ru.vorobeij.core.git.data.GitInfo
import ru.vorobeij.regress.implementation.BenchmarkStatisticsProcessor

class BenchmarksLocalRepository(
    private val benchmarksDatabaseStorage: BenchmarksLocalStorage,
    private val benchmarkStatisticsProcessor: BenchmarkStatisticsProcessor
) : BenchmarksRepository {

    override fun save(
        benchmarkPlatformOutput: BenchmarkPlatformOutput,
        gitInfo: GitInfo
    ): List<BenchmarkAnalyticsResult> {

        val results = benchmarkPlatformOutput.benchmarks.map {
            extracted(benchmarkPlatformOutput, it)
        }

        if (gitInfo.isCommitted) {
            benchmarksDatabaseStorage.save(benchmarkPlatformOutput.benchmarks, benchmarkPlatformOutput.benchmarkContext.build.fingerprint, gitInfo)
        }

        return results
    }

    private fun extracted(
        benchmarkPlatformOutput: BenchmarkPlatformOutput,
        it: Benchmark
    ): BenchmarkAnalyticsResult {
        val measurements = benchmarksDatabaseStorage
            .getAll(benchmarkPlatformOutput.benchmarkContext.build.fingerprint, it.fullName())
            .sortedByDescending(HistoricalBenchmark::commitDate)
            .map { historicalBenchmark ->
                Measurement(
                    maximum = historicalBenchmark.maximum,
                    median = historicalBenchmark.median,
                    minimum = historicalBenchmark.minimum
                )
            }

        val metrics = it.metrics.timeNs ?: it.metrics.summary!!
        val regress = benchmarkStatisticsProcessor.regress(
            measurements = measurements,
            newMeasurement = Measurement(
                maximum = metrics.minimum,
                median = metrics.median,
                minimum = metrics.maximum
            )
        )

        return BenchmarkAnalyticsResult(regress, it.fullName())
    }
}
