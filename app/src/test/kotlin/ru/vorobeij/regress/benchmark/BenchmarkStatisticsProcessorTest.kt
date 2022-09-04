@file:Suppress(
    "FLOAT_IN_ACCURATE_CALCULATIONS",
    "TOO_LONG_FUNCTION",
    "LONG_NUMERICAL_VALUES_SEPARATED"
)

package ru.vorobeij.regress.benchmark

import org.junit.jupiter.api.Test
import ru.vorobeij.core.data.Measurement
import ru.vorobeij.regress.implementation.BenchmarkStatisticsProcessor

internal class BenchmarkStatisticsProcessorTest {

    private val benchmarkStatisticsProcessor = BenchmarkStatisticsProcessor()
    private val step = 10

    @Test
    fun `all measurements are equal`() {
        val measurement = Measurement(1126.0, 1073.0, 1045.0)
        val regress = benchmarkStatisticsProcessor.regress(
            measurements = (0..100).map { measurement },
            newMeasurement = measurement
        )
        assert(regress == 0) { regress }
    }

    @Test
    fun `new measurement higher`() {
        val regress = benchmarkStatisticsProcessor.regress(
            measurements = (0..100).map { Measurement(1126.0 + Math.random() % step, 1073.0 + Math.random() % step, 1045.0 + Math.random() % step) },
            newMeasurement = Measurement(1326.0, 1273.0, 1245.0)
        )
        assert(regress > 10) { regress }
    }

    @Test
    fun `new measurement lower`() {
        val regress = benchmarkStatisticsProcessor.regress(
            measurements = (0..100).map { Measurement(1126.0 + Math.random() % step, 1073.0 + Math.random() % step, 1045.0 + Math.random() % step) },
            newMeasurement = Measurement(926.0, 973.0, 945.0)
        )
        assert(regress < 0) { regress }
    }

    @Test
    fun `get measurements`() {
        val values = listOf(
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
        val measurements = benchmarkStatisticsProcessor.getMeasurement(values)
        assert(measurements.minimum < measurements.maximum)
        assert(measurements.median < measurements.maximum)
        assert(measurements.median > measurements.minimum)
        assert(measurements.minimum >= values.min()) { "${measurements.minimum} >= ${values.min()}" }
        assert(measurements.maximum <= values.max()) { "${measurements.maximum} <= ${values.max()}" }
    }
}
