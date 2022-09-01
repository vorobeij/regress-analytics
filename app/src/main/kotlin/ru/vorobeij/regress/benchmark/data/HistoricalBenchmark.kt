package ru.vorobeij.regress.benchmark.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property commit
 * @property name
 * @property authorEmail
 * @property commitDate
 * @property message
 * @property branch
 * @property maximum
 * @property median
 * @property minimum
 * @property deviceFingerprint
 */
@Serializable
data class HistoricalBenchmark(
    @SerialName("commit") val commit: String,
    @SerialName("name") val name: String,
    @SerialName("authorEmail") val authorEmail: String,
    @SerialName("commitDate") val commitDate: Long,
    @SerialName("message") val message: String,
    @SerialName("branch") val branch: String,
    @SerialName("maximum") val maximum: Double,
    @SerialName("median") val median: Double,
    @SerialName("minimum") val minimum: Double,
    @SerialName("deviceFingerprint") val deviceFingerprint: String
)
