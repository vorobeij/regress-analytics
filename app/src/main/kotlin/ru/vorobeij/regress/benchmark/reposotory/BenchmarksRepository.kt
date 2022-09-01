package ru.vorobeij.regress.benchmark.reposotory

import ru.vorobeij.regress.benchmark.data.BenchmarkAnalyticsResult
import ru.vorobeij.regress.benchmark.data.BenchmarkPlatformOutput
import ru.vorobeij.regress.git.data.GitInfo

/**
 * Repository for benchmarks
 * Can use
 * - remote server
 * - local database
 */
interface BenchmarksRepository {

    /**
     * Save new entry to a storage and tell, what regression do we have
     *
     * @param benchmarkPlatformOutput
     * @param gitInfo
     * @return regress info
     */
    fun save(
        benchmarkPlatformOutput: BenchmarkPlatformOutput,
        gitInfo: GitInfo
    ): List<BenchmarkAnalyticsResult>
}
