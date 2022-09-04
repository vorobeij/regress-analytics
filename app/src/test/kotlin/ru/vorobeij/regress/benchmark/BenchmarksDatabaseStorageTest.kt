package ru.vorobeij.regress.benchmark

import java.util.UUID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject
import ru.vorobeij.core.data.fullName
import ru.vorobeij.core.git.data.GitInfo
import ru.vorobeij.regress.benchmark.data.expectedMacrobenchmarkPlatformOutput
import ru.vorobeij.regress.benchmark.data.microbenchmarkData
import ru.vorobeij.regress.di.testDatabaseModule
import ru.vorobeij.regress.implementation.di.dataModule
import ru.vorobeij.regress.implementation.di.databaseStorageModule
import ru.vorobeij.regress.implementation.local.database.BenchmarksDatabaseStorage

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
        expectedMacrobenchmarkPlatformOutput.benchmarks.forEach {
            assert(benchmarksDatabaseStorage.getAll(expectedMacrobenchmarkPlatformOutput.benchmarkContext.build.fingerprint, it.fullName()).isNotEmpty())
        }
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
        expectedMacrobenchmarkPlatformOutput.benchmarks.forEach {
            Assertions.assertEquals(
                /* expected = */ 2,
                /* actual = */ benchmarksDatabaseStorage.getAll(expectedMacrobenchmarkPlatformOutput.benchmarkContext.build.fingerprint, it.fullName()).size
            )
        }
    }
}
