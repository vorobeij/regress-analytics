package ru.vorobeij

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ArgumentsTest {

    private val projectRootPath = "~/Downloads/my-test-project"
    private val argsString = "-root $projectRootPath"
    private val argsFullString = "--projectRootPath $projectRootPath"

    @Test
    fun `parse arguments`() {
        val arguments = Arguments.fromArgs(argsString.split(" ").toTypedArray())
        Assertions.assertEquals(
            /* expected = */ projectRootPath,
            /* actual = */ arguments.projectRootPath
        )
    }

    @Test
    fun `parse full text arguments`() {
        val arguments = Arguments.fromArgs(argsFullString.split(" ").toTypedArray())
        Assertions.assertEquals(
            /* expected = */ projectRootPath,
            /* actual = */ arguments.projectRootPath
        )
    }
}
