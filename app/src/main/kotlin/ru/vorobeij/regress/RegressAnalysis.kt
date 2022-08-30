package ru.vorobeij.regress

import java.io.File
import ru.vorobeij.jacoco.ExcludeGenerated
import ru.vorobeij.regress.benchmark.BenchmarksParser
import ru.vorobeij.regress.benchmark.data.BenchmarkData
import ru.vorobeij.regress.git.data.GitInfo

object RegressAnalytics {

    @JvmStatic
    @ExcludeGenerated
    fun main(args: Array<String>) {
        // val arguments = Arguments.fromArgs(args)
        val arguments = Arguments("/Users/sj/AndroidApps/AndroidAppTemplate")

        val gitInfo = GitInfo.from(arguments.projectRootPath)
        val benchmarkParser = BenchmarksParser()
        val benchmarkData: Sequence<BenchmarkData> = File(arguments.benchmarkJsonRoot).walk()
            .filter { it.isFile }
            .map { benchmarkParser.parse(it.readText()) }
    }
}