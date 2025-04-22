package io.kdot.app.matrix

import net.folivo.trixnity.client.store.repository.room.TrixnityRoomDatabase
import okio.FileSystem
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import net.folivo.trixnity.client.store.repository.room.createRoomRepositoriesModule
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual fun platformCreateRepositoriesModule(): Module = module {

    single<CreateRepositoriesModule> {
        val fileSystem = FileSystem.SYSTEM
        val rootPath = get<RootPath>()
        val context = androidContext()
        object : CreateRepositoriesModule {
            override suspend fun generateDatabaseKey(): ByteArray? {
                return null
            }

            override suspend fun create(databaseKey: ByteArray?): Module {
                fileSystem.createDirectories(rootPath.resolveDatabase(), mustCreate = false)
                return createRoomRepositoriesModule(db(context))
            }

            override suspend fun load(databaseKey: ByteArray?): Module {
                return createRoomRepositoriesModule(db(context))
            }


            private fun db(context: Context): RoomDatabase.Builder<TrixnityRoomDatabase> =
                Room.databaseBuilder<TrixnityRoomDatabase>(
                    context, rootPath.resolveDatabase().resolve(DB_NAME).toString()
                ).apply {
                    setDriver(BundledSQLiteDriver())
                }
        }
    }
}
