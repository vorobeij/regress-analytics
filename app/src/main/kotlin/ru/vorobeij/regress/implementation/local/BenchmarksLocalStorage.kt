package ru.vorobeij.regress.implementation.local

import ru.vorobeij.core.data.Benchmark
import ru.vorobeij.core.data.HistoricalBenchmark
import ru.vorobeij.core.git.data.GitInfo

interface BenchmarksLocalStorage {

    fun clear()
    fun save(
        benchmarks: List<Benchmark>,
        deviceFingerprint: String,
        gitInfo: GitInfo
    )

    /**
     * Get all benchmarks for the same device and benchmark name, ie history of this exact benchmark
     *
     * @param deviceFingerprint
     * @param benchmarkName
     * @return list of historical benchmarks
     */
    fun getAll(
        deviceFingerprint: String,
        benchmarkName: String
    ): List<HistoricalBenchmark>
}
