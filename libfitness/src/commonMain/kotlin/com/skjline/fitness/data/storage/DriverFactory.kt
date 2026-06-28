package com.skjline.fitness.data.storage

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

const val DATABASE_NAME = "SkjlineDatabase.db"

expect suspend fun provideDBDriver(
    schema: SqlSchema<QueryResult.Value<Unit>>
): SqlDriver
