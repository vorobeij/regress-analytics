package ru.vorobeij.core.data

/**
 * Can be negative and positive
 * Negative values means better performance, positive - poor performance
 * @property benchmarkName
 * @property regressPercentage
 */
data class BenchmarkAnalyticsResult(
    val regressPercentage: Int,
    val benchmarkName: String
)
