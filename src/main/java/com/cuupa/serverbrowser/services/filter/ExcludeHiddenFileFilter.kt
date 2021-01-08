package com.cuupa.serverbrowser.services.filter

import org.apache.juli.logging.LogFactory
import java.io.File

class ExcludeHiddenFileFilter : BrowserFileFilter {

    private val log = LogFactory.getLog(ExcludeHiddenFileFilter::class.java)

    override fun applies(file: File): Boolean {
        if (log.isInfoEnabled) {
            log.info("Checking $file with $this")
        }
        return !file.isHidden && file.name.startsWith(".")
    }

    override fun toString(): String {
        return "ExcludeHiddenFileFilter()"
    }
}