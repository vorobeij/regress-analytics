package ru.vorobeij.regress.benchmark

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.vorobeij.regress.benchmark.data.BenchmarkPlatformOutput

class BenchmarksParser(
    private val json: Json
) {

    fun parse(jsonString: String): BenchmarkPlatformOutput = json.decodeFromString(jsonString)
}
