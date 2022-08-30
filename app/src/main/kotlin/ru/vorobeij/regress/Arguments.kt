package ru.vorobeij.regress

import java.io.File
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required

/**
 * @property projectRootPath: root of android project with benchmarks
 */
data class Arguments(
    val projectRootPath: String
) {

    val benchmarkJsonRoot = "$projectRootPath/benchmarks/json"
    val benchmarkReportsRoot = "$projectRootPath/benchmarks/reports"

    init {
        File(benchmarkReportsRoot).mkdirs()
    }

    companion object {

        fun fromArgs(args: Array<String>): Arguments {
            val parser = ArgParser("regress-analytics")
            val projectRootPath by parser.option(ArgType.String, shortName = "root", description = "Project root path").required()
            parser.parse(args)

            return Arguments(projectRootPath)
        }
    }
}
