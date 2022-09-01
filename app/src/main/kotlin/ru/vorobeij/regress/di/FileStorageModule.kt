package ru.vorobeij.regress.di

import java.io.File
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.vorobeij.regress.benchmark.reposotory.BenchmarksRepository
import ru.vorobeij.regress.benchmark.reposotory.local.BenchmarksLocalRepository
import ru.vorobeij.regress.benchmark.reposotory.local.BenchmarksLocalStorage
import ru.vorobeij.regress.benchmark.reposotory.local.file.BenchmarksFileStorage

fun fileStorageModule(storageFilePath: String) = module {
    single { File(storageFilePath) }
    singleOf(::BenchmarksFileStorage) bind BenchmarksLocalStorage::class
    factoryOf(::BenchmarksLocalRepository) bind BenchmarksRepository::class
}
