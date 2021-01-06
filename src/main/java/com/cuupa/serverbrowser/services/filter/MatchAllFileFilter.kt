package com.cuupa.serverbrowser.services.filter

import java.io.File


class MatchAllFileFilter : BrowserFileFilter {

    override fun applies(file: File): Boolean {
        return true
    }
}