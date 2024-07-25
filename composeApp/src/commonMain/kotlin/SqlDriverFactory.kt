package data.datasource

import app.cash.sqldelight.db.SqlDriver

expect class SqlDriverFactory() {
    fun createDriver(): SqlDriver
}
