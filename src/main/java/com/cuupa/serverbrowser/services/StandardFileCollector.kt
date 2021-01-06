package com.cuupa.serverbrowser.services

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

    val log = LogFactory.getLog(StandardFileCollector::class.java)

    override fun collect(dir: String?, filters: List<BrowserFileFilter>): List<BrowserFileObject> {
        if (log.isInfoEnabled) {
            log.info("Collecting files in '$dir' with filter '$filters'")
        }
        return if (dir.isNullOrBlank()) {
            listOf()
        } else {
            try {

                Files.list(Path.of(dir)).filter { filters.any { filter -> filter.applies(it.toFile()) } }
                    .map { it.toFile() }
                    .map {
                        BrowserFileObject(
                            getType(it),
                            it.parent,
                            it.name,
                            it.length(),
                            getLastModifiedLocalDateTime(it.lastModified())
                        )
                    }.collect(Collectors.toList())
            } catch (ex: NoSuchFileException) {
                return listOf()
            }
        }
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
            FileObjectType.FILE
        } else {
            FileObjectType.DIRECTORY
        }
    }
}