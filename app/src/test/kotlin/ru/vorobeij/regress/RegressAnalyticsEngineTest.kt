package ru.vorobeij.regress

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.koin.test.get
import ru.vorobeij.regress.benchmark.BenchmarkFilesProvider
import ru.vorobeij.regress.benchmark.BenchmarkPerformanceException
import ru.vorobeij.regress.benchmark.ClosingKoinTest
import ru.vorobeij.regress.benchmark.data.BenchmarkAnalyticsResult
import ru.vorobeij.regress.benchmark.data.macrobenchmarkJson
import ru.vorobeij.regress.benchmark.reposotory.BenchmarksRepository
import ru.vorobeij.regress.di.dataModule
import ru.vorobeij.regress.di.fileStorageModule

class RegressAnalyticsEngineTest : ClosingKoinTest(
    listOf(
        dataModule,
        fileStorageModule("./android-benchmarks/test/test.json")
    )
) {

    private val benchmarksRepository: BenchmarksRepository = mockk()
    private val benchmarkFilesProvider: BenchmarkFilesProvider = mockk()
    private val regressAnalyticsEngine: RegressAnalyticsEngine by lazy {
        RegressAnalyticsEngine(
            benchmarkParser = get(),
            benchmarksRepository = benchmarksRepository,
            benchmarkFilesProvider = benchmarkFilesProvider
        )
    }
    private val arguments = Arguments(".", 10, "./test.json")

    @Test
    fun `throw error when above threshold`() {
        every { benchmarkFilesProvider.files(any()) } returns sequenceOf(macrobenchmarkJson)
        every { benchmarksRepository.save(any(), any()) } returns listOf(BenchmarkAnalyticsResult(100, "name1"))

        assertThrows<BenchmarkPerformanceException> {
            regressAnalyticsEngine.analyse(arguments)
        }
    }

    @Test
    fun `silent when better performance`() {
        every { benchmarkFilesProvider.files(any()) } returns sequenceOf(macrobenchmarkJson)
        every { benchmarksRepository.save(any(), any()) } returns listOf(BenchmarkAnalyticsResult(-10, "name1"))

        assertDoesNotThrow {
            regressAnalyticsEngine.analyse(arguments)
        }
    }
}