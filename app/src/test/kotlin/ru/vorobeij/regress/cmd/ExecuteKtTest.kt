package ru.vorobeij.regress.cmd

import java.io.File
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.vorobeij.core.cmd.execute

internal class ExecuteKtTest {

    @Test
    fun echo() {
        assertEquals(
            "test\n",
            "echo test".execute(File("."))
        )
    }
}
