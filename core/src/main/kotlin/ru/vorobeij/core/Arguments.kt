package ru.vorobeij.core

import java.io.File
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required

/**
 * @property projectRootPath: root of android project with benchmarks
 * @property threshold
 * @property storageFilePath
 */
data class Arguments(
    val projectRootPath: String,
    val threshold: Int,
    val storageFilePath: String?
) {

    val benchmarkJsonRoot = "$projectRootPath/benchmarks/json"
    val benchmarkReportsRoot = "$projectRootPath/benchmarks/reports"

    init {
        File(benchmarkReportsRoot).mkdirs()
        storageFilePath?.let {
            File(storageFilePath).parentFile.mkdirs()
        }
    }

    override fun toString(): String {
        return """
            projectRootPath      = $projectRootPath       
            threshold            = $threshold 
            storageFilePath      = $storageFilePath       
            benchmarkJsonRoot    = $benchmarkJsonRoot         
            benchmarkReportsRoot = $benchmarkReportsRoot            
        """.trimIndent()
    }
    companion object {

        fun fromArgs(args: Array<String>): Arguments {
            val parser = ArgParser("regress-analytics")
            val projectRootPath by parser.option(ArgType.String, shortName = "root", description = "Project root path").required()
            val threshold by parser.option(ArgType.Int, shortName = "t", description = "Benchmark threshold, percent [0;100]").required()
            val storageFilePath by parser.option(ArgType.String, shortName = "storageFilePath", description = "Storage file path")
            parser.parse(args)

            return Arguments(
                projectRootPath = projectRootPath,
                threshold = threshold,
                storageFilePath = storageFilePath
            )
        }
    }
}
