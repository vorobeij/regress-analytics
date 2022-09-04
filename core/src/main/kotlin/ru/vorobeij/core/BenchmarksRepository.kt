package ru.vorobeij.core

import ru.vorobeij.core.data.BenchmarkAnalyticsResult
import ru.vorobeij.core.data.BenchmarkPlatformOutput
import ru.vorobeij.core.git.data.GitInfo

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
