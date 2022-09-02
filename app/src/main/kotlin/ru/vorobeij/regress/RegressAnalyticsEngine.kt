package ru.vorobeij.regress

import ru.vorobeij.regress.benchmark.BenchmarkFilesProvider
import ru.vorobeij.regress.benchmark.BenchmarkPerformanceException
import ru.vorobeij.regress.benchmark.BenchmarksParser
import ru.vorobeij.regress.benchmark.reposotory.BenchmarksRepository
import ru.vorobeij.regress.git.data.GitInfo

class RegressAnalyticsEngine(
    private val benchmarkParser: BenchmarksParser,
    private val benchmarksRepository: BenchmarksRepository,
    private val benchmarkFilesProvider: BenchmarkFilesProvider
) {

    fun analyse(arguments: Arguments) {
        val gitInfo = GitInfo.from(arguments.projectRootPath)

        val results =
            benchmarkFilesProvider
                .files(arguments.benchmarkJsonRoot)
                .flatMap { jsonString ->
                    val benchmarkData = benchmarkParser.parse(jsonString)
                    benchmarksRepository.save(benchmarkData, gitInfo)
                }.toList()

        val errors = results.joinToString("\n") {
            if (it.regressPercentage > arguments.threshold) {
                "Benchmark ${it.benchmarkName} is ${it.regressPercentage}% slower!"
            } else {
                ""
            }
        }

        if (errors.isNotBlank()) {
            throw BenchmarkPerformanceException(errors)
        }
    }
}
