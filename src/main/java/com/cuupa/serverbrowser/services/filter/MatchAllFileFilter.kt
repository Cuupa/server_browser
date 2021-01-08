package com.cuupa.serverbrowser.services.filter

import org.apache.juli.logging.LogFactory
import java.io.File

class MatchAllFileFilter : BrowserFileFilter {

    private val log = LogFactory.getLog(MatchAllFileFilter::class.java)

    override fun applies(file: File): Boolean {
        if (log.isInfoEnabled) {
            log.info("Checking $file with $this")
        }
        return true
    }

    override fun toString(): String {
        return "MatchAllFileFilter"
    }
}