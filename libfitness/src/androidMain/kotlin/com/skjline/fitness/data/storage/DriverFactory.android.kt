package com.skjline.fitness.data.storage

import android.content.Context
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.skjline.fitness.data.storage.DATABASE_NAME
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.get

actual suspend fun provideDBDriver(
    schema: SqlSchema<QueryResult.Value<Unit>>,
): SqlDriver {
    val context: Context = AppComponent.get<Context>()
    return AndroidSqliteDriver(
        schema = schema,
        context = context,
        name = DATABASE_NAME,
    )
}
