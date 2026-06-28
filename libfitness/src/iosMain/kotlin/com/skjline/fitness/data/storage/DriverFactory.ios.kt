package com.skjline.fitness.data.storage

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.skjline.fitness.data.storage.DATABASE_NAME

actual suspend fun provideDBDriver(
    schema: SqlSchema<QueryResult.Value<Unit>>
): SqlDriver {
    return NativeSqliteDriver(
        schema = schema,
        name = DATABASE_NAME,
    )
}
