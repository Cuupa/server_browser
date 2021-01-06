package com.cuupa.serverbrowser.services.filter

import java.io.File

class DirectoryFilter(private val filter: String?) : BrowserFileFilter {

    constructor() : this(null)

    override fun applies(file: File): Boolean {
        return if (filter.isNullOrEmpty()) {
            !file.isFile && file.isDirectory
        } else {
            !file.isFile && file.isDirectory && file.name.toLowerCase().contains(filter.toLowerCase())
        }
    }
}