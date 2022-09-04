package ru.vorobeij.regress.benchmark

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import ru.vorobeij.regress.implementation.local.database.DatabaseProvider

internal class FileDatabaseProviderTest {

    @Test
    fun `provide database`() {
        assertDoesNotThrow { DatabaseProvider().database }
    }
}
