package ru.vorobeij.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property benchmarks
 * @property benchmarkContext
 */
@Serializable
data class BenchmarkPlatformOutput(
    @SerialName("benchmarks") val benchmarks: List<Benchmark>,
    @SerialName("context") val benchmarkContext: BenchmarkContext
)

/**
 * @property maximum
 * @property median
 * @property minimum
 * @property runs
 */
@Serializable
data class Measurements(
    @SerialName("maximum") val maximum: Double,
    @SerialName("median") val median: Double,
    @SerialName("minimum") val minimum: Double,
    @SerialName("runs") val runs: List<Double>
)

/**
 * @property className
 * @property metrics
 * @property name
 * @property repeatIterations
 * @property thermalThrottleSleepSeconds
 * @property totalRunTimeNs
 * @property warmupIterations
 */
@Serializable
data class Benchmark(
    @SerialName("className") val className: String,
    @SerialName("metrics") val metrics: Metrics,
    @SerialName("name") val name: String,
    @SerialName("repeatIterations") val repeatIterations: Int,
    @SerialName("thermalThrottleSleepSeconds") val thermalThrottleSleepSeconds: Int,
    @SerialName("totalRunTimeNs") val totalRunTimeNs: Long,
    @SerialName("warmupIterations") val warmupIterations: Int
) {
    /**
     * @property summary
     * @property allocationCount macrobenchmark only
     * @property timeNs microbenchmarks only
     */
    @Serializable
    data class Metrics(
        @SerialName("timeToInitialDisplayMs") val summary: Measurements? = null,
        @SerialName("allocationCount") val allocationCount: Measurements? = null,
        @SerialName("timeNs") val timeNs: Measurements? = null  // microbenchmarks only
    )
}

/**
 * @property build
 * @property cpuCoreCount
 * @property cpuLocked
 * @property cpuMaxFreqHz
 * @property memTotalBytes
 * @property sustainedPerformanceModeEnabled
 */
@Serializable
data class BenchmarkContext(
    @SerialName("build") val build: Build,
    @SerialName("cpuCoreCount") val cpuCoreCount: Int,
    @SerialName("cpuLocked") val cpuLocked: Boolean,
    @SerialName("cpuMaxFreqHz") val cpuMaxFreqHz: Int,
    @SerialName("memTotalBytes") val memTotalBytes: Int,
    @SerialName("sustainedPerformanceModeEnabled") val sustainedPerformanceModeEnabled: Boolean
) {
    /**
     * @property brand
     * @property device
     * @property fingerprint
     * @property model
     * @property version
     */
    @Serializable
    data class Build(
        @SerialName("brand") val brand: String,
        @SerialName("device") val device: String,
        @SerialName("fingerprint") val fingerprint: String,
        @SerialName("model") val model: String,
        @SerialName("version") val version: Version
    ) {
        /**
         * @property sdk
         */
        @Serializable
        data class Version(
            @SerialName("sdk") val sdk: Int
        )
    }
}

fun Benchmark.fullName() = "$className.`$name`()"
