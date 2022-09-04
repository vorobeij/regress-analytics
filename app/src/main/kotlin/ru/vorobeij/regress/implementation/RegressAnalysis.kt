package ru.vorobeij.regress.implementation

import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import ru.vorobeij.core.Arguments
import ru.vorobeij.core.RegressAnalyticsEngine
import ru.vorobeij.jacoco.ExcludeGenerated
import ru.vorobeij.regress.implementation.di.dataModule
import ru.vorobeij.regress.implementation.di.databaseStorageModule
import ru.vorobeij.regress.implementation.di.fileStorageModule

@Suppress("COMMENTED_OUT_CODE")
object RegressAnalytics {

    @JvmStatic
    @ExcludeGenerated
    fun main(args: Array<String>) {
        val arguments = Arguments.fromArgs(args)
        // val arguments = Arguments(
        // projectRootPath = "/Users/sj/AndroidApps/AndroidAppTemplate",
        // threshold = 5,
        // storageFilePath = "/Users/sj/AndroidApps/AndroidAppTemplate/benchmarks/reports/benchmarks-2.json"
        // )

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
