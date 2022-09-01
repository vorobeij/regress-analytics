@file:Suppress("LONG_NUMERICAL_VALUES_SEPARATED")

package ru.vorobeij.regress.benchmark.data

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.vorobeij.regress.benchmark.BenchmarksParser

val macrobenchmarkJson = """
    {
        "context": {
            "build": {
                "brand": "google",
                "device": "emulator64_arm64",
                "fingerprint": "google/sdk_gphone64_arm64/emulator64_arm64:12/SE1A.220203.002.A1/8151367:user/release-keys",
                "model": "sdk_gphone64_arm64",
                "version": {
                    "sdk": 31
                }
            },
            "cpuCoreCount": 4,
            "cpuLocked": false,
            "cpuMaxFreqHz": 2000,
            "memTotalBytes": 2061529088,
            "sustainedPerformanceModeEnabled": false
        },
        "benchmarks": [
            {
                "name": "startup",
                "params": {},
                "className": "ru.vorobeij.com.example.macrobenchmark.startup.SampleStartupBenchmark",
                "totalRunTimeNs": 15641048383,
                "metrics": {
                    "timeToInitialDisplayMs": {
                        "minimum": 21.470916,
                        "maximum": 197.771583,
                        "median": 46.892417,
                        "runs": [
                            197.771583,
                            53.469542,
                            21.470916,
                            23.507958,
                            46.892417
                        ]
                    }
                },
                "sampledMetrics": {},
                "warmupIterations": 0,
                "repeatIterations": 5,
                "thermalThrottleSleepSeconds": 0
            }
        ]
    }
""".trimIndent()

val expectedMacrobenchmarkPlatformOutput = BenchmarkPlatformOutput(
    benchmarkContext = BenchmarkContext(
        build = BenchmarkContext.Build(
            brand = "google",
            device = "emulator64_arm64",
            fingerprint = "google/sdk_gphone64_arm64/emulator64_arm64:12/SE1A.220203.002.A1/8151367:user/release-keys",
            model = "sdk_gphone64_arm64",
            version = BenchmarkContext.Build.Version(
                sdk = 31
            )
        ),
        cpuCoreCount = 4,
        cpuLocked = false,
        cpuMaxFreqHz = 2000,
        memTotalBytes = 2_061_529_088,
        sustainedPerformanceModeEnabled = false
    ),
    benchmarks = listOf(
        Benchmark(
            name = "startup",
            className = "ru.vorobeij.com.example.macrobenchmark.startup.SampleStartupBenchmark",
            totalRunTimeNs = 15_641_048_383,
            metrics = Benchmark.Metrics(
                summary = Measurements(
                    minimum = 21.470_916,
                    maximum = 197.771_583,
                    median = 46.892_417,
                    runs = listOf(
                        197.771_583,
                        53.469_542,
                        21.470_916,
                        23.507_958,
                        46.892_417
                    )
                )
            ),
            warmupIterations = 0,
            repeatIterations = 5,
            thermalThrottleSleepSeconds = 0
        )

    )
)

private val microbenchmarkJson = """
    {
        "context": {
            "build": {
                "brand": "google",
                "device": "emulator64_arm64",
                "fingerprint": "google/sdk_gphone64_arm64/emulator64_arm64:12/SE1A.220203.002.A1/8151367:user/release-keys",
                "model": "sdk_gphone64_arm64",
                "version": {
                    "sdk": 31
                }
            },
            "cpuCoreCount": 4,
            "cpuLocked": false,
            "cpuMaxFreqHz": 2000,
            "memTotalBytes": 2061529088,
            "sustainedPerformanceModeEnabled": false
        },
        "benchmarks": [
            {
                "name": "log2",
                "params": {},
                "className": "ru.vorobeij.android.app.android.benchmark.ExampleBenchmark2",
                "totalRunTimeNs": 5903298086,
                "metrics": {
                    "timeNs": {
                        "minimum": 1031.8697723827106,
                        "maximum": 1128.2586194449314,
                        "median": 1047.6389991852393,
                        "runs": [
                            1126.3506358226502,
                            1073.359391920463,
                            1045.2656222604965,
                            1036.9770629428326,
                            1045.7679685646806,
                            1042.8342323202112,
                            1076.5324924454162,
                            1046.3339177607493,
                            1049.566320479368,
                            1048.2479141097967,
                            1044.9519291261436,
                            1062.1865079774343,
                            1043.275131238333,
                            1041.4612679324678,
                            1041.1299388413897,
                            1043.2136838522706,
                            1055.4466950629635,
                            1075.1534947040564,
                            1048.4915790884995,
                            1038.9061065789338,
                            1051.3088664514598,
                            1071.3138994028527,
                            1048.503171378183,
                            1039.5545631748848,
                            1050.4270583017915,
                            1038.8691535772114,
                            1064.751549592104,
                            1038.411918193913,
                            1055.7135549344582,
                            1046.9286620393766,
                            1048.065283980157,
                            1047.212714390322,
                            1054.5120306102453,
                            1047.2079908416786,
                            1039.4277905549654,
                            1039.270943987789,
                            1041.263157351925,
                            1072.838563958705,
                            1087.3564216540672,
                            1036.7549014552242,
                            1051.130526706614,
                            1047.0734831530203,
                            1073.460380977919,
                            1048.1082600220707,
                            1031.8697723827106,
                            1128.2586194449314,
                            1032.7558709171728,
                            1063.2131269273214,
                            1058.699714318128,
                            1044.309485256959
                        ]
                    },
                    "allocationCount": {
                        "minimum": 0.0,
                        "maximum": 0.0,
                        "median": 0.0,
                        "runs": [
                            0.0,
                            0.0,
                            0.0,
                            0.0,
                            0.0
                        ]
                    }
                },
                "sampledMetrics": {},
                "warmupIterations": 191836,
                "repeatIterations": 96961,
                "thermalThrottleSleepSeconds": 0
            },
            {
                "name": "log",
                "params": {},
                "className": "ru.vorobeij.android.app.android.benchmark.ExampleBenchmark",
                "totalRunTimeNs": 5564349086,
                "metrics": {
                    "timeNs": {
                        "minimum": 1028.4636840053706,
                        "maximum": 1075.869017714063,
                        "median": 1038.0118779505392,
                        "runs": [
                            1038.0506843085452,
                            1033.9113430637967,
                            1037.9730715925332,
                            1049.1467798518775,
                            1059.6369916410413,
                            1038.4765581012603,
                            1036.7017302611632,
                            1029.9046623933475,
                            1063.9838342933865,
                            1033.888789033739,
                            1050.4289488501017,
                            1037.4646260123868,
                            1037.941498115986,
                            1029.7918814153927,
                            1042.282039066222,
                            1043.3345749057994,
                            1044.5960089219975,
                            1037.312139980077,
                            1040.0871735458443,
                            1028.7533240937244,
                            1034.1477651695614,
                            1036.4337455065183,
                            1063.4375027069168,
                            1029.7264498245918,
                            1035.0198254580102,
                            1042.0023171207067,
                            1046.9659036770756,
                            1044.7588678591537,
                            1040.5572891853265,
                            1063.2367361081035,
                            1032.9499436961323,
                            1039.0143358309151,
                            1028.4636840053706,
                            1064.5780025120187,
                            1035.4651240850621,
                            1040.905127983022,
                            1036.141842435792,
                            1037.6107995149205,
                            1034.9724652431894,
                            1036.9056477110312,
                            1048.5954675386547,
                            1040.8712806964356,
                            1052.0982285937025,
                            1035.5594114080297,
                            1039.5764758109922,
                            1030.8791524102387,
                            1033.896011087531,
                            1049.2514400796915,
                            1033.7809671272034,
                            1075.869017714063
                        ]
                    },
                    "allocationCount": {
                        "minimum": 0.0,
                        "maximum": 0.0,
                        "median": 0.0,
                        "runs": [
                            0.0,
                            0.0,
                            0.0,
                            0.0,
                            0.0
                        ]
                    }
                },
                "sampledMetrics": {},
                "warmupIterations": 211194,
                "repeatIterations": 92356,
                "thermalThrottleSleepSeconds": 0
            }
        ]
    }
""".trimIndent()

val microbenchmarkData = BenchmarkPlatformOutput(
    benchmarks = listOf(
        Benchmark(
            className = "ru.vorobeij.android.app.android.benchmark.ExampleBenchmark2",
            metrics = Benchmark.Metrics(
                summary = null,
                allocationCount = Measurements(
                    maximum = 0.0,
                    median = 0.0,
                    minimum = 0.0,
                    runs = listOf(0.0, 0.0, 0.0, 0.0, 0.0)
                ),
                timeNs = Measurements(
                    maximum = 1128.258_619_444_931_4,
                    median = 1047.638_999_185_239_3,
                    minimum = 1031.869_772_382_710_6,
                    runs = listOf(
                        1126.350_635_822_650_2,
                        1073.359_391_920_463,
                        1045.265_622_260_496_5,
                        1036.977_062_942_832_6,
                        1045.767_968_564_680_6,
                        1042.834_232_320_211_2,
                        1076.532_492_445_416_2,
                        1046.333_917_760_749_3,
                        1049.566_320_479_368,
                        1048.247_914_109_796_7,
                        1044.951_929_126_143_6,
                        1062.186_507_977_434_3,
                        1043.275_131_238_333,
                        1041.461_267_932_467_8,
                        1041.129_938_841_389_7,
                        1043.213_683_852_270_6,
                        1055.446_695_062_963_5,
                        1075.153_494_704_056_4,
                        1048.491_579_088_499_5,
                        1038.906_106_578_933_8,
                        1051.308_866_451_459_8,
                        1071.313_899_402_852_7,
                        1048.503_171_378_183,
                        1039.554_563_174_884_8,
                        1050.427_058_301_791_5,
                        1038.869_153_577_211_4,
                        1064.751_549_592_104,
                        1038.411_918_193_913,
                        1055.713_554_934_458_2,
                        1046.928_662_039_376_6,
                        1048.065_283_980_157,
                        1047.212_714_390_322,
                        1054.512_030_610_245_3,
                        1047.207_990_841_678_6,
                        1039.427_790_554_965_4,
                        1039.270_943_987_789,
                        1041.263_157_351_925,
                        1072.838_563_958_705,
                        1087.356_421_654_067_2,
                        1036.754_901_455_224_2,
                        1051.130_526_706_614,
                        1047.073_483_153_020_3,
                        1073.460_380_977_919,
                        1048.108_260_022_070_7,
                        1031.869_772_382_710_6,
                        1128.258_619_444_931_4,
                        1032.755_870_917_172_8,
                        1063.213_126_927_321_4,
                        1058.699_714_318_128,
                        1044.309_485_256_959
                    )
                )
            ),
            name = "log2",
            repeatIterations = 96961,
            thermalThrottleSleepSeconds = 0,
            totalRunTimeNs = 5_903_298_086,
            warmupIterations = 191_836
        ), Benchmark(
            className = "ru.vorobeij.android.app.android.benchmark.ExampleBenchmark",
            metrics = Benchmark.Metrics(
                summary = null,
                allocationCount = Measurements(maximum = 0.0, median = 0.0, minimum = 0.0, runs = listOf(0.0, 0.0, 0.0, 0.0, 0.0)),
                timeNs = Measurements(
                    maximum = 1075.869_017_714_063,
                    median = 1038.011_877_950_539_2,
                    minimum = 1028.463_684_005_370_6,
                    runs = listOf(
                        1038.050_684_308_545_2,
                        1033.911_343_063_796_7,
                        1037.973_071_592_533_2,
                        1049.146_779_851_877_5,
                        1059.636_991_641_041_3,
                        1038.476_558_101_260_3,
                        1036.701_730_261_163_2,
                        1029.904_662_393_347_5,
                        1063.983_834_293_386_5,
                        1033.888_789_033_739,
                        1050.428_948_850_101_7,
                        1037.464_626_012_386_8,
                        1037.941_498_115_986,
                        1029.791_881_415_392_7,
                        1042.282_039_066_222,
                        1043.334_574_905_799_4,
                        1044.596_008_921_997_5,
                        1037.312_139_980_077,
                        1040.087_173_545_844_3,
                        1028.753_324_093_724_4,
                        1034.147_765_169_561_4,
                        1036.433_745_506_518_3,
                        1063.437_502_706_916_8,
                        1029.726_449_824_591_8,
                        1035.019_825_458_010_2,
                        1042.002_317_120_706_7,
                        1046.965_903_677_075_6,
                        1044.758_867_859_153_7,
                        1040.557_289_185_326_5,
                        1063.236_736_108_103_5,
                        1032.949_943_696_132_3,
                        1039.014_335_830_915_1,
                        1028.463_684_005_370_6,
                        1064.578_002_512_018_7,
                        1035.465_124_085_062_1,
                        1040.905_127_983_022,
                        1036.141_842_435_792,
                        1037.610_799_514_920_5,
                        1034.972_465_243_189_4,
                        1036.905_647_711_031_2,
                        1048.595_467_538_654_7,
                        1040.871_280_696_435_6,
                        1052.098_228_593_702_5,
                        1035.559_411_408_029_7,
                        1039.576_475_810_992_2,
                        1030.879_152_410_238_7,
                        1033.896_011_087_531,
                        1049.251_440_079_691_5,
                        1033.780_967_127_203_4,
                        1075.869_017_714_063
                    )
                )
            ),
            name = "log",
            repeatIterations = 92356,
            thermalThrottleSleepSeconds = 0,
            totalRunTimeNs = 5_564_349_086,
            warmupIterations = 211_194
        )
    ), benchmarkContext = BenchmarkContext(
        build = BenchmarkContext.Build(
            brand = "google", device = "emulator64_arm64",
            fingerprint = "google/sdk_gphone64_arm64/emulator64_arm64:12/SE1A.220203.002.A1/8151367:user/release-keys", model = "sdk_gphone64_arm64",
            version = BenchmarkContext.Build.Version(sdk = 31)
        ), cpuCoreCount = 4, cpuLocked = false, cpuMaxFreqHz = 2000, memTotalBytes = 2_061_529_088,
        sustainedPerformanceModeEnabled = false
    )
)

internal class BenchmarksParserTest {

    private val parser = BenchmarksParser(
        Json {
            ignoreUnknownKeys = true
        }
    )

    @Test
    fun `parse macrobenchmark`() {
        Assertions.assertEquals(
            /* expected = */ expectedMacrobenchmarkPlatformOutput,
            /* actual = */ parser.parse(macrobenchmarkJson)
        )
    }

    @Test
    fun `parse microbenchmark`() {
        Assertions.assertEquals(
            microbenchmarkData,
            parser.parse(microbenchmarkJson)
        )
    }
}
