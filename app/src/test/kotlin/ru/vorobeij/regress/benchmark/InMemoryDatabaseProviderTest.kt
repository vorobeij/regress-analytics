package ru.vorobeij.regress.benchmark

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import ru.vorobeij.regress.implementation.local.database.DatabaseProvider

internal class InMemoryDatabaseProviderTest {

    @Test
    fun `create in memory database`() {
        assertDoesNotThrow { DatabaseProvider().database }
    }
}
