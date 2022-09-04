package ru.vorobeij.regress.implementation

import org.nield.kotlinstatistics.descriptiveStatistics
import ru.vorobeij.core.data.Measurement
import ru.vorobeij.core.distanceTo

class BenchmarkStatisticsProcessor {

    fun getMeasurement(runs: List<Double>): Measurement {
        val stats = runs.descriptiveStatistics
        return Measurement(
            maximum = stats.percentile(80.0),
            median = stats.mean,
            minimum = stats.percentile(20.0)
        )
    }

    fun regress(
        measurements: List<Measurement>,
        newMeasurement: Measurement
    ): Int {

        if (measurements.size < 10) {
            return 0
        }

        val measurementMin = getMeasurement(measurements.map { it.minimum })
        val measurementMedian = getMeasurement(measurements.map { it.median })
        val measurementMax = getMeasurement(measurements.map { it.maximum })

        val measurementDistance = newMeasurement.distanceTo(measurementMax) +
            newMeasurement.distanceTo(measurementMedian) +
            newMeasurement.distanceTo(measurementMin)
        return (measurementDistance / 3 / measurementMedian.median * 100).toInt()
    }
}
