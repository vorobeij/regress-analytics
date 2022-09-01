package ru.vorobeij.regress.benchmark

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

abstract class ClosingKoinTest(
    private val modules: List<Module> = emptyList()
) : KoinTest {

    @BeforeEach
    fun before() {
        startKoin {
            modules(
                *modules.toTypedArray()
            )
        }
    }

    @AfterEach
    fun autoClose() {
        stopKoin()
    }
}
