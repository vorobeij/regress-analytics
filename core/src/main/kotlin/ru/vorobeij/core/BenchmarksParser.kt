package ru.vorobeij.core

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.vorobeij.core.data.BenchmarkPlatformOutput

class BenchmarksParser(
    private val json: Json
) {

    fun parse(jsonString: String): BenchmarkPlatformOutput = json.decodeFromString(jsonString)
}
