package ru.vorobeij.regress

import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import ru.vorobeij.jacoco.ExcludeGenerated
import ru.vorobeij.regress.di.dataModule
import ru.vorobeij.regress.di.databaseStorageModule
import ru.vorobeij.regress.di.fileStorageModule

object RegressAnalytics {

    @JvmStatic
    @ExcludeGenerated
    fun main(args: Array<String>) {
        @Suppress("COMMENTED_OUT_CODE")
        // val arguments = Arguments.fromArgs(args)
        val arguments = Arguments(
            projectRootPath = "/Users/sj/AndroidApps/AndroidAppTemplate",
            threshold = 5,
            storageFilePath = "/Users/sj/Documents/android-benchmarks/test.json"
        )

        val koinApp: KoinApplication = koinApplication {
            modules(
                dataModule,
                arguments.storageFilePath?.let { fileStorageModule(it) } ?: databaseStorageModule
            )
        }
        val regressAnalyticsEngine: RegressAnalyticsEngine = koinApp.koin.get()

        regressAnalyticsEngine.analyse(arguments)
    }
}
