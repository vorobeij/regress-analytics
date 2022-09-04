package ru.vorobeij.regress

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.vorobeij.core.Arguments

internal class ArgumentsTest {

    private val projectRootPath = "~/Downloads/my-test-project"
    private val threshold = 5
    private val storageFilePath = "~/Benchmark-regress.json"
    private val argsString = "-root $projectRootPath -t $threshold -storageFilePath $storageFilePath"
    private val argsFullString = """--projectRootPath $projectRootPath --threshold $threshold --storageFilePath $storageFilePath"""

    @Test
    fun `init variables`() {
        val args = Arguments("root", threshold, storageFilePath)
        Assertions.assertEquals(
            args.benchmarkJsonRoot,
            "root/benchmarks/json"
        )
        Assertions.assertEquals(
            args.benchmarkReportsRoot,
            "root/benchmarks/reports"
        )
    }

    @Test
    fun `parse arguments`() {
        val arguments = Arguments.fromArgs(argsString.split(" ").toTypedArray())
        assertArguments(arguments)
    }

    @Test
    fun `parse full text arguments`() {
        val arguments = Arguments.fromArgs(argsFullString.split(" ").toTypedArray())
        assertArguments(arguments)
    }

    private fun assertArguments(arguments: Arguments) {
        Assertions.assertEquals(projectRootPath, arguments.projectRootPath)
        Assertions.assertEquals(storageFilePath, arguments.storageFilePath)
        Assertions.assertEquals(threshold, arguments.threshold)
    }
}
