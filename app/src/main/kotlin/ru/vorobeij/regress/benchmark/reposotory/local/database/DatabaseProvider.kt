package ru.vorobeij.regress.benchmark.reposotory.local.database

import java.sql.Connection
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager

/**
 * https://github.com/JetBrains/Exposed/wiki/DataBase-and-DataSource
 */
data class DatabaseProvider(
    private val url: String = "jdbc:h2:tcp://localhost/~/test",
    private val driver: String = "org.h2.Driver",
    private val user: String = "sa",
    private val password: String = ""
) {

    val database: Database by lazy {
        val db = Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_READ_COMMITTED
        db
    }
}
