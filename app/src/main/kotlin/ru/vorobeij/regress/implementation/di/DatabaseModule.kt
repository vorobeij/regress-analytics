package ru.vorobeij.regress.implementation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.vorobeij.core.BenchmarksRepository
import ru.vorobeij.regress.implementation.local.BenchmarksLocalRepository
import ru.vorobeij.regress.implementation.local.BenchmarksLocalStorage
import ru.vorobeij.regress.implementation.local.database.BenchmarksDatabaseStorage
import ru.vorobeij.regress.implementation.local.database.DatabaseProvider

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
