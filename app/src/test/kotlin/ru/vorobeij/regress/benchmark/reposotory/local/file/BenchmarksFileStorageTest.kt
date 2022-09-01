package ru.vorobeij.regress.benchmark.reposotory.local.file

import java.util.UUID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject
import ru.vorobeij.regress.benchmark.ClosingKoinTest
import ru.vorobeij.regress.benchmark.data.expectedMacrobenchmarkPlatformOutput
import ru.vorobeij.regress.benchmark.data.microbenchmarkData
import ru.vorobeij.regress.benchmark.reposotory.local.BenchmarksLocalStorage
import ru.vorobeij.regress.di.dataModule
import ru.vorobeij.regress.di.fileStorageModule
import ru.vorobeij.regress.git.data.GitInfo

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
        assert(benchmarksDatabaseStorage.getAll(expectedMacrobenchmarkPlatformOutput.benchmarkContext.build.fingerprint, "startup").isNotEmpty())
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
        Assertions.assertEquals(
            /* expected = */ 2,
            /* actual = */ benchmarksDatabaseStorage.getAll(expectedMacrobenchmarkPlatformOutput.benchmarkContext.build.fingerprint, "startup").size
        )
    }
}
