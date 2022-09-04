package ru.vorobeij.regress.implementation.di

import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.vorobeij.core.BenchmarkFilesProvider
import ru.vorobeij.core.BenchmarkFilesProviderImpl
import ru.vorobeij.core.BenchmarksParser
import ru.vorobeij.core.RegressAnalyticsEngine
import ru.vorobeij.regress.implementation.BenchmarkStatisticsProcessor

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
