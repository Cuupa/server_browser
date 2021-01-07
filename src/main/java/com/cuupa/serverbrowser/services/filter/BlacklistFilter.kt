package com.cuupa.serverbrowser.services.filter

import java.io.File

class BlacklistFilter(val blacklisted: List<String>) : BrowserFileFilter {

    override fun applies(file: File): Boolean {
        return blacklisted.any { file.absolutePath.startsWith(it) }.not()
    }

    override fun toString(): String {
        return "BlacklistFilter($blacklisted)"
    }
}
