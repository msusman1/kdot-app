package io.kdot.app.matrix

import org.koin.core.module.Module

interface CreateRepositoriesModule {
    suspend fun generateDatabaseKey(): ByteArray?
    suspend fun create(databaseKey: ByteArray?): Module
    suspend fun load(databaseKey: ByteArray?): Module
}

const val DB_NAME = "kdot-db"

expect fun platformCreateRepositoriesModule(): Module
