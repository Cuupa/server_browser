package com.cuupa.serverbrowser.services

import com.cuupa.serverbrowser.services.FileObjectType.DIRECTORY
import com.cuupa.serverbrowser.services.FileObjectType.FILE
import com.cuupa.serverbrowser.services.filter.BrowserFileFilter
import com.cuupa.serverbrowser.services.filter.MatchAllFileFilter
import org.apache.commons.logging.LogFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

class StandardFileCollector : FileCollector {

    private val log = LogFactory.getLog(StandardFileCollector::class.java)

    override fun collect(dir: String?, filters: List<BrowserFileFilter>): List<BrowserFileObject> {
        if (log.isInfoEnabled) {
            log.info("Collecting files in '$dir' with filter '$filters'")
        }
        if (!dir.isNullOrBlank()) {
            try {
                val filelist = getFileList(dir, filters)
                filelist.add(getParentDirectory(filelist))
                return filelist.sortedWith(
                    compareBy<BrowserFileObject> { it.type }.reversed()
                        .thenBy { it.displayText.toLowerCase() })
            } catch (ex: NoSuchFileException) {
                if (log.isWarnEnabled) {
                    log.warn(ex)
                }
            }
        }
        return listOf(
            BrowserFileObject(
                DIRECTORY,
                "",
                "../",
                "../",
                0,
                getLastModifiedLocalDateTime(0)
            )
        )
    }

    private fun getParentDirectory(filelist: MutableList<BrowserFileObject>): BrowserFileObject {
        val element = filelist.firstOrNull()
        val parent = element?.parent?.split("/")?.dropLast(1)?.joinToString("/", "", "") ?: ""
        return BrowserFileObject(
            DIRECTORY,
            "",
            parent,
            "../",
            0,
            getLastModifiedLocalDateTime(0)
        )
    }

    private fun getFileList(dir: String?, filters: List<BrowserFileFilter>): MutableList<BrowserFileObject> {
        return Files.list(Path.of(dir)).filter { filters.any { filter -> filter.applies(it.toFile()) } }
            .map { it.toFile() }
            .map {
                BrowserFileObject(
                    getType(it),
                    it.parent,
                    it.name,
                    it.name,
                    it.length(),
                    getLastModifiedLocalDateTime(it.lastModified())
                )
            }.collect(Collectors.toList())
    }

    override fun collect(dir: String?): List<BrowserFileObject> {
        return collect(dir, listOf(MatchAllFileFilter()))
    }

    private fun getLastModifiedLocalDateTime(lastModified: Long): LocalDateTime =
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(lastModified),
            TimeZone.getDefault().toZoneId()
        )

    private fun getType(it: File): FileObjectType {
        return if (it.isFile && !it.isDirectory) {
            FILE
        } else {
            DIRECTORY
        }
    }
}