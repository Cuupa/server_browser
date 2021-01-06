package com.cuupa.serverbrowser.services.filter

import java.io.File

interface BrowserFileFilter {

    fun applies(file: File): Boolean
}
