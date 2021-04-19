package com.ajanoni.core.directory.service

import com.ajanoni.core.directory.service.data.FsItem
import com.ajanoni.core.directory.service.data.FsSearchResult
import com.ajanoni.core.directory.service.exception.DirectoryNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

class DirectoryService {

    private val fileOrdering: (path1: Path, path2: Path) -> Int
            = { path1, path2 -> Files.size(path1).compareTo(Files.size(path2)) }

    private val mapping: (path: Path) -> FsItem = { file ->
        FsItem(
            if (Files.isDirectory(file)) "*" + file.fileName.toString() else file.fileName.toString(),
            Files.getLastModifiedTime(file).toInstant(),
            Files.size(file)
        )
    }

    fun list(directory: String) : FsSearchResult {
        val path = Paths.get("", directory)

        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw DirectoryNotFoundException("Directory not found: $path")
        }

        val fsItems =  Files.list(path)
            .sorted(fileOrdering)
            .map(mapping)
            .collect(Collectors.toList())

        val totalSize = fsItems.sumOf { it.size }

        return FsSearchResult(fsItems, fsItems.size.toLong(), totalSize)
    }
}
