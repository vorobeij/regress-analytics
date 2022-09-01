package ru.vorobeij.regress.benchmark

import java.util.UUID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject
import ru.vorobeij.regress.benchmark.data.expectedMacrobenchmarkPlatformOutput
import ru.vorobeij.regress.benchmark.data.microbenchmarkData
import ru.vorobeij.regress.benchmark.reposotory.local.database.BenchmarksDatabaseStorage
import ru.vorobeij.regress.di.dataModule
import ru.vorobeij.regress.di.databaseStorageModule
import ru.vorobeij.regress.di.testDatabaseModule
import ru.vorobeij.regress.git.data.GitInfo

class BenchmarksDatabaseStorageTest : ClosingKoinTest(
    listOf(
        dataModule,
        testDatabaseModule,
        databaseStorageModule
    )
) {

    @Test
    fun `database save`() {
        val benchmarksDatabaseStorage: BenchmarksDatabaseStorage by inject()
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
        val benchmarksDatabaseStorage: BenchmarksDatabaseStorage by inject()
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
