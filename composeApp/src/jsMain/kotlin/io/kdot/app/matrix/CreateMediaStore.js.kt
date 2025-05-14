package io.kdot.app.matrix

import net.folivo.trixnity.client.media.opfs.OpfsMediaStore
import okio.FileSystem
import okio.NodeJsFileSystem
import org.koin.core.module.Module
import org.koin.dsl.module
import web.fs.FileSystemGetDirectoryOptions
import web.navigator.navigator

actual fun platformCreateMediaStoreModule(): Module = module {
    single<CreateMediaStore> {
        val rootPath = get<RootPath>().resolveMedia()
        CreateMediaStore {
            var opfsDirectory = navigator.storage.getDirectory()
            val options = FileSystemGetDirectoryOptions(create = true)
            for (segment in rootPath.segments) {
                opfsDirectory = opfsDirectory.getDirectoryHandle(segment, options)
            }
            OpfsMediaStore(opfsDirectory)
        }
    }
}

actual val platformFileSystem: FileSystem = NodeJsFileSystem