package ru.vorobeij.regress.implementation.di

import java.io.File
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.vorobeij.core.BenchmarksRepository
import ru.vorobeij.regress.implementation.local.BenchmarksLocalRepository
import ru.vorobeij.regress.implementation.local.BenchmarksLocalStorage
import ru.vorobeij.regress.implementation.local.file.BenchmarksFileStorage

fun fileStorageModule(storageFilePath: String) = module {
    single { File(storageFilePath) }
    singleOf(::BenchmarksFileStorage) bind BenchmarksLocalStorage::class
    factoryOf(::BenchmarksLocalRepository) bind BenchmarksRepository::class
}
