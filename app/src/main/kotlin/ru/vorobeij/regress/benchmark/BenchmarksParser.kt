package ru.vorobeij.regress.benchmark

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.vorobeij.regress.benchmark.data.BenchmarkData

class BenchmarksParser {

    fun parse(json: String): BenchmarkData {
        return Json.decodeFromString(json)
    }
}