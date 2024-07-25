package data.datasource

import androidContext
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import fr.naddev.composemultiplatformmotus.AppDatabase

actual class SqlDriverFactory {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema,androidContext, "app.db")
    }
}