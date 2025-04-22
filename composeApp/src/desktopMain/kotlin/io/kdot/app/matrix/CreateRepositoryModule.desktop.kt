package io.kdot.app.matrix

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import net.folivo.trixnity.client.store.repository.room.TrixnityRoomDatabase
import net.folivo.trixnity.client.store.repository.room.createRoomRepositoriesModule
import okio.FileSystem
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformCreateRepositoriesModule(): Module = module {
    single<CreateRepositoriesModule> {
        val fileSystem = FileSystem.SYSTEM
        val rootPath = get<RootPath>()

        object : CreateRepositoriesModule {
            override suspend fun generateDatabaseKey(): ByteArray? {
                return null
            }

            override suspend fun create(databaseKey: ByteArray?): Module {
                fileSystem.createDirectories(
                    rootPath.resolveDatabase(),
                    mustCreate = false
                )
                return createRoomRepositoriesModule(db())
            }

            override suspend fun load(databaseKey: ByteArray?): Module {
                return createRoomRepositoriesModule(db())

            }

            fun db(): RoomDatabase.Builder<TrixnityRoomDatabase> {
                return Room.databaseBuilder<TrixnityRoomDatabase>(
                    rootPath.resolveDatabase().resolve(DB_NAME).toString()
                ).apply {
                    setDriver(BundledSQLiteDriver())
                }
            }
        }
    }
}