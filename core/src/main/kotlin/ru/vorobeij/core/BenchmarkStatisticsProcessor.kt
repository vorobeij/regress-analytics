package ru.vorobeij.core

import ru.vorobeij.core.data.Measurement

fun Measurement.distanceTo(other: Measurement): Double = (maximum - other.maximum + median - other.median + minimum - other.minimum) / 3
