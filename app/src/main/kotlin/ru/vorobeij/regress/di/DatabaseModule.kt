package ru.vorobeij.regress.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.vorobeij.regress.benchmark.reposotory.BenchmarksRepository
import ru.vorobeij.regress.benchmark.reposotory.local.BenchmarksLocalRepository
import ru.vorobeij.regress.benchmark.reposotory.local.BenchmarksLocalStorage
import ru.vorobeij.regress.benchmark.reposotory.local.database.BenchmarksDatabaseStorage
import ru.vorobeij.regress.benchmark.reposotory.local.database.DatabaseProvider

val databaseStorageModule = module {
    single {
        DatabaseProvider(
            url = "jdbc:h2:./regressH2file",
            driver = "org.h2.Driver",
            user = "vorobeij",
            password = ""
        ).database
    }
    singleOf(::BenchmarksDatabaseStorage) bind BenchmarksLocalStorage::class
    factoryOf(::BenchmarksLocalRepository) bind BenchmarksRepository::class
}
