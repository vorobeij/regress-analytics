package ru.vorobeij.regress.benchmark.reposotory.local.database

import java.time.LocalDateTime
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object HistoricalBenchmarkTable : Table() {

    val commit: Column<String> = varchar("commit_hash", 50)
    val name: Column<String> = varchar("name", 50)
    val authorEmail: Column<String> = varchar("authorEmail", 50)
    val commitDate: Column<LocalDateTime> = datetime("commitDate")
    val message: Column<String> = text("message")
    val branch: Column<String> = varchar("branch", 50)
    val maximum: Column<Double> = double("maximum")
    val median: Column<Double> = double("median")
    val minimum: Column<Double> = double("minimum")
    val deviceFingerprint: Column<String> = varchar("deviceFingerprint", 100)
    override val primaryKey: PrimaryKey = PrimaryKey(commit, name = "commit_hash")
}
