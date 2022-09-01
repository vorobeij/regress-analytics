package ru.vorobeij.regress.di

import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.vorobeij.regress.RegressAnalyticsEngine
import ru.vorobeij.regress.benchmark.BenchmarkFilesProvider
import ru.vorobeij.regress.benchmark.BenchmarkFilesProviderImpl
import ru.vorobeij.regress.benchmark.BenchmarkStatisticsProcessor
import ru.vorobeij.regress.benchmark.BenchmarksParser

val dataModule = module {
    single {
        Json {
            prettyPrint = false
            ignoreUnknownKeys = true
        }
    }
    factoryOf(::BenchmarksParser)
    factoryOf(::BenchmarkStatisticsProcessor)
    single { BenchmarkFilesProviderImpl } bind BenchmarkFilesProvider::class
    factoryOf(::RegressAnalyticsEngine)
}
