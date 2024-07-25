package data.datasource

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import fr.naddev.composemultiplatformmotus.AppDatabase

actual class SqlDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase .Schema, "app.db")
    }
}