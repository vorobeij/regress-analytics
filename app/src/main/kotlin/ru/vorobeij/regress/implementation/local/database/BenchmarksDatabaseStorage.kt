package ru.vorobeij.regress.implementation.local.database

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction
import ru.vorobeij.core.data.Benchmark
import ru.vorobeij.core.data.HistoricalBenchmark
import ru.vorobeij.core.data.fullName
import ru.vorobeij.core.git.data.GitInfo
import ru.vorobeij.regress.implementation.BenchmarkStatisticsProcessor
import ru.vorobeij.regress.implementation.local.BenchmarksLocalStorage

// todo use suspend functions https://github.com/JetBrains/Exposed/wiki/Transactions#working-with-coroutines
class BenchmarksDatabaseStorage(
    private val database: Database,
    private val statisticsProcessor: BenchmarkStatisticsProcessor
) : BenchmarksLocalStorage {

    override fun clear() {
        transaction(database) {
            SchemaUtils.drop(HistoricalBenchmarkTable)
        }
    }

    override fun save(
        benchmarks: List<Benchmark>,
        deviceFingerprint: String,
        gitInfo: GitInfo
    ) {
        transaction(database) {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(HistoricalBenchmarkTable)

            benchmarks.forEach { benchmark: Benchmark ->
                try {
                    HistoricalBenchmarkTable.insert {
                        insert(deviceFingerprint, benchmark, gitInfo, it)
                    }
                } catch (e: Exception) {
                    HistoricalBenchmarkTable.replace {
                        insert(deviceFingerprint, benchmark, gitInfo, it)
                    }
                }
            }
        }
    }

    /**
     * Get all benchmarks for the same device and benchmark name, ie history of this exact benchmark
     */
    override fun getAll(
        deviceFingerprint: String,
        benchmarkName: String
    ): List<HistoricalBenchmark> = transaction(database) {
        HistoricalBenchmarkTable
            .select {
                (HistoricalBenchmarkTable.fullName eq benchmarkName) and (HistoricalBenchmarkTable.deviceFingerprint eq deviceFingerprint)
            }.map { it: ResultRow ->
                val commitData = it[HistoricalBenchmarkTable.commitDate]
                val instant: Instant = commitData.atZone(ZoneId.systemDefault()).toInstant()
                val date: Date = Date.from(instant)
                val calendar = Calendar.getInstance().apply {

                    time = date
                }
                HistoricalBenchmark(
                    commit = it[HistoricalBenchmarkTable.commit],
                    fullName = it[HistoricalBenchmarkTable.fullName],
                    authorEmail = it[HistoricalBenchmarkTable.authorEmail],
                    commitDate = calendar.timeInMillis,
                    message = it[HistoricalBenchmarkTable.message],
                    branch = it[HistoricalBenchmarkTable.branch],
                    maximum = it[HistoricalBenchmarkTable.maximum],
                    median = it[HistoricalBenchmarkTable.median],
                    minimum = it[HistoricalBenchmarkTable.minimum],
                    deviceFingerprint = it[HistoricalBenchmarkTable.deviceFingerprint]
                )
            }.toList()
    }

    private fun HistoricalBenchmarkTable.insert(
        deviceFingerprint: String,
        benchmark: Benchmark,
        gitInfo: GitInfo,
        it: UpdateBuilder<*>
    ) {
        val metrics = benchmark.metrics.timeNs ?: benchmark.metrics.summary!!
        val measurement = statisticsProcessor.getMeasurement(metrics.runs)
        it[commit] = gitInfo.commit
        it[fullName] = benchmark.fullName()
        it[authorEmail] = gitInfo.author.email
        it[commitDate] = LocalDateTime.ofInstant(gitInfo.commitDate.toInstant(), TimeZone.getDefault().toZoneId())
        it[message] = gitInfo.message
        it[branch] = gitInfo.branch
        it[maximum] = measurement.maximum
        it[median] = measurement.median
        it[minimum] = measurement.minimum
        it[HistoricalBenchmarkTable.deviceFingerprint] = deviceFingerprint
    }
}
