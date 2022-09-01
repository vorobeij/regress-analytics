package ru.vorobeij.regress.di

import org.koin.dsl.module
import ru.vorobeij.regress.benchmark.reposotory.local.database.DatabaseProvider

val testDatabaseModule = module {
    single {
        DatabaseProvider(
            url = "jdbc:h2:mem:regular",
            driver = "org.h2.Driver",
            user = "vorobeij",
            password = ""
        ).database
    }
}
