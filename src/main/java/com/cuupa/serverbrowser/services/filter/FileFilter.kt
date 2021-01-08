package com.cuupa.serverbrowser.services.filter

import org.apache.juli.logging.LogFactory
import java.io.File

class FileFilter(private val filter: String?) : BrowserFileFilter {

    private val log = LogFactory.getLog(FileFilter::class.java)

    constructor() : this(null)

    override fun applies(file: File): Boolean {
        if (log.isInfoEnabled) {
            log.info("Checking $file with $this")
        }
        return if (filter.isNullOrEmpty()) {
            file.isFile && !file.isDirectory
        } else {
            file.isFile && !file.isDirectory && file.name.toLowerCase().contains(filter.toLowerCase())
        }
    }

    override fun toString(): String {
        return "FileFilter($filter)"
    }
}