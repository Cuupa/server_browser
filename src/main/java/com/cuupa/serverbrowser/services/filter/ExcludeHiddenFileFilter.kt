package com.cuupa.serverbrowser.services.filter

import java.io.File

class ExcludeHiddenFileFilter : BrowserFileFilter {

    override fun applies(file: File): Boolean {
        return !file.isHidden && file.name.startsWith(".")
    }
}