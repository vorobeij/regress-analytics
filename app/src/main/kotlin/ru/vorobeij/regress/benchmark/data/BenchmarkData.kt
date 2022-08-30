package ru.vorobeij.regress.benchmark.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BenchmarkData(
    @SerialName("benchmarks") val benchmarks: List<Benchmark>,
    @SerialName("context") val benchmarkContext: BenchmarkContext
)

@Serializable
data class Measurements(
    @SerialName("maximum") val maximum: Double,
    @SerialName("median") val median: Double,
    @SerialName("minimum") val minimum: Double,
    @SerialName("runs") val runs: List<Double>
)

@Serializable
data class Benchmark(
    @SerialName("className") val className: String,
    @SerialName("metrics") val metrics: Metrics,
    @SerialName("name") val name: String,
    @SerialName("params") val params: Params?,
    @SerialName("repeatIterations") val repeatIterations: Int,
    @SerialName("sampledMetrics") val sampledMetrics: SampledMetrics?,
    @SerialName("thermalThrottleSleepSeconds") val thermalThrottleSleepSeconds: Int,
    @SerialName("totalRunTimeNs") val totalRunTimeNs: Long,
    @SerialName("warmupIterations") val warmupIterations: Int
) {

    @Serializable
    data class Metrics(
        @SerialName("timeToInitialDisplayMs") val summary: Measurements? = null,    // macrobenchmark only
        @SerialName("allocationCount") val allocationCount: Measurements? = null,   // microbenchmarks only
        @SerialName("timeNs") val timeNs: Measurements? = null                      // microbenchmarks only
    )

    @Serializable
    object Params

    @Serializable
    object SampledMetrics
}

@Serializable
data class BenchmarkContext(
    @SerialName("build") val build: Build,
    @SerialName("cpuCoreCount") val cpuCoreCount: Int,
    @SerialName("cpuLocked") val cpuLocked: Boolean,
    @SerialName("cpuMaxFreqHz") val cpuMaxFreqHz: Int,
    @SerialName("memTotalBytes") val memTotalBytes: Int,
    @SerialName("sustainedPerformanceModeEnabled") val sustainedPerformanceModeEnabled: Boolean
) {

    @Serializable
    data class Build(
        @SerialName("brand") val brand: String,
        @SerialName("device") val device: String,
        @SerialName("fingerprint") val fingerprint: String,
        @SerialName("model") val model: String,
        @SerialName("version") val version: Version
    ) {

        @Serializable
        data class Version(
            @SerialName("sdk") val sdk: Int
        )
    }
}