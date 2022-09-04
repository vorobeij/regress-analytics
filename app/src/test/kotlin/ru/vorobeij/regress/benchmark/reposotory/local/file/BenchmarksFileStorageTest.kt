package ru.vorobeij.regress.benchmark.reposotory.local.file

import java.util.UUID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject
import ru.vorobeij.core.data.fullName
import ru.vorobeij.core.git.data.GitInfo
import ru.vorobeij.regress.benchmark.ClosingKoinTest
import ru.vorobeij.regress.benchmark.data.expectedMacrobenchmarkPlatformOutput
import ru.vorobeij.regress.benchmark.data.microbenchmarkData
import ru.vorobeij.regress.implementation.di.dataModule
import ru.vorobeij.regress.implementation.di.fileStorageModule
import ru.vorobeij.regress.implementation.local.BenchmarksLocalStorage

internal class BenchmarksFileStorageTest : ClosingKoinTest(
    listOf(
        dataModule,
        fileStorageModule("./android-benchmarks/test/test.json")
    )
) {

    private val benchmarksDatabaseStorage: BenchmarksLocalStorage by inject()

    @Test
    fun `database save`() {
        benchmarksDatabaseStorage.clear()
        val gitInfo = GitInfo.from("./").copy(isCommitted = true)
        benchmarksDatabaseStorage.save(
            expectedMacrobenchmarkPlatformOutput.benchmarks,
            expectedMacrobenchmarkPlatformOutput.benchmarkContext.build.fingerprint,
            gitInfo
        )
        expectedMacrobenchmarkPlatformOutput.benchmarks.forEach {
            assert(
                benchmarksDatabaseStorage.getAll(
                    expectedMacrobenchmarkPlatformOutput.benchmarkContext.build.fingerprint,
                    it.fullName()
                ).isNotEmpty()
            )
        }
    }

    @Test
    fun `create table twice and create 3 different rows`() {
        benchmarksDatabaseStorage.clear()
        val gitInfo = GitInfo.from("./").copy(isCommitted = true)
        benchmarksDatabaseStorage.save(
            expectedMacrobenchmarkPlatformOutput.benchmarks,
            expectedMacrobenchmarkPlatformOutput.benchmarkContext.build.fingerprint,
            gitInfo.copy(commit = UUID.randomUUID().toString())
        )
        benchmarksDatabaseStorage.save(
            expectedMacrobenchmarkPlatformOutput.benchmarks,
            expectedMacrobenchmarkPlatformOutput.benchmarkContext.build.fingerprint,
            gitInfo.copy(commit = UUID.randomUUID().toString())
        )
        benchmarksDatabaseStorage.save(
            microbenchmarkData.benchmarks,
            microbenchmarkData.benchmarkContext.build.fingerprint + "sd",
            gitInfo.copy(commit = UUID.randomUUID().toString())
        )
        expectedMacrobenchmarkPlatformOutput.benchmarks.forEach {
            Assertions.assertEquals(
                /* expected = */ 2,
                /* actual = */ benchmarksDatabaseStorage.getAll(expectedMacrobenchmarkPlatformOutput.benchmarkContext.build.fingerprint, it.fullName()).size
            )
        }
    }
}
