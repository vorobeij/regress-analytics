package ru.vorobeij.regress

import org.junit.jupiter.api.Test

private val dataClassString =
    "BenchmarkData(benchmarks=[Benchmark(className=ru.vorobeij.android.app.android.benchmark.ExampleBenchmark2, metrics=Metrics(summary=null," +
        " allocationCount=Measurements(maximum=0.0, median=0.0, minimum=0.0, runs=[0.0, 0.0, 0.0, 0.0, 0.0]), timeNs=Measurements(maximum=" +
        "1128.2586194449314, median=1047.6389991852393, minimum=1031.8697723827106, runs=[1126.3506358226502, 1073.359391920463, 1045.2656222604965," +
        " 1036.9770629428326, 1045.7679685646806, 1042.8342323202112, 1076.5324924454162, 1046.3339177607493, 1049.566320479368, 1048.2479141097967, " +
        "1044.9519291261436, 1062.1865079774343, 1043.275131238333, 1041.4612679324678, 1041.1299388413897, 1043.2136838522706, 1055.4466950629635, " +
        "1075.1534947040564, 1048.4915790884995, 1038.9061065789338, 1051.3088664514598, 1071.3138994028527, 1048.503171378183, 1039.5545631748848, " +
        "1050.4270583017915, 1038.8691535772114, 1064.751549592104, 1038.411918193913, 1055.7135549344582, 1046.9286620393766, 1048.065283980157, " +
        "1047.212714390322, 1054.5120306102453, 1047.2079908416786, 1039.4277905549654, 1039.270943987789, 1041.263157351925, 1072.838563958705, " +
        "1087.3564216540672, 1036.7549014552242, 1051.130526706614, 1047.0734831530203, 1073.460380977919, 1048.1082600220707, 1031.8697723827106, " +
        "1128.2586194449314, 1032.7558709171728, 1063.2131269273214, 1058.699714318128, 1044.309485256959])), name=log2, " +
        "params=ru.vorobeij.regress.benchmark.data.Benchmark\$Params@31ff43be, repeatIterations=96961," +
        " sampledMetrics=ru.vorobeij.regress.benchmark.data.Benchmark\$SampledMetrics@5b6ec132, " +
        "thermalThrottleSleepSeconds=0, totalRunTimeNs=5903298086, warmupIterations=191836), " +
        "Benchmark(className=ru.vorobeij.android.app.android.benchmark.ExampleBenchmark," +
        " metrics=Metrics(summary=null, allocationCount=Measurements(maximum=0.0, median=0.0, minimum=0.0, runs=[0.0, 0.0, 0.0, 0.0, 0.0]), " +
        "timeNs=Measurements(maximum=1075.869017714063, median=1038.0118779505392, minimum=1028.4636840053706, runs=[1038.0506843085452, " +
        "1033.9113430637967, 1037.9730715925332, 1049.1467798518775, 1059.6369916410413, 1038.4765581012603, 1036.7017302611632, 1029.9046623933475, " +
        "1063.9838342933865, 1033.888789033739, 1050.4289488501017, 1037.4646260123868, 1037.941498115986, 1029.7918814153927, 1042.282039066222, " +
        "1043.3345749057994, 1044.5960089219975, 1037.312139980077, 1040.0871735458443, 1028.7533240937244, 1034.1477651695614, 1036.4337455065183, " +
        "1063.4375027069168, 1029.7264498245918, 1035.0198254580102, 1042.0023171207067, 1046.9659036770756, 1044.7588678591537, 1040.5572891853265," +
        " 1063.2367361081035, 1032.9499436961323, 1039.0143358309151, 1028.4636840053706, 1064.5780025120187, 1035.4651240850621, 1040.905127983022, " +
        "1036.141842435792, 1037.6107995149205, 1034.9724652431894, 1036.9056477110312, 1048.5954675386547, 1040.8712806964356, 1052.0982285937025," +
        " 1035.5594114080297, 1039.5764758109922, 1030.8791524102387, 1033.896011087531, 1049.2514400796915, 1033.7809671272034, 1075.869017714063]))," +
        " name=log, params=ru.vorobeij.regress.benchmark.data.Benchmark\$Params@31ff43be, repeatIterations=92356, " +
        "sampledMetrics=ru.vorobeij.regress.benchmark.data.Benchmark\$SampledMetrics@5b6ec132, thermalThrottleSleepSeconds=0," +
        " totalRunTimeNs=5564349086, warmupIterations=211194)], benchmarkContext=BenchmarkContext(build=Build(brand=google, " +
        "device=emulator64_arm64, fingerprint=google/sdk_gphone64_arm64/emulator64_arm64:12/SE1A.220203.002.A1/8151367:user/release-keys," +
        " model=sdk_gphone64_arm64, version=Version(sdk=31)), cpuCoreCount=4, cpuLocked=false, cpuMaxFreqHz=2000, memTotalBytes=2061529088, " +
        "sustainedPerformanceModeEnabled=false))"

class StringToKotlinClass {

    fun convert(string: String): String = string
        .replace("[", "listOf(")
        .replace("]", ")")
}

class StringConversionTest {

    @Test
    fun `convert string to data kotlin class`() {
        StringToKotlinClass().convert(dataClassString)
    }
}
