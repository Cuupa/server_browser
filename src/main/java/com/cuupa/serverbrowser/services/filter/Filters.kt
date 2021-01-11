package com.cuupa.serverbrowser.services.filter

import java.io.File
import java.nio.file.Path
import java.util.stream.Stream

class Filters(val filterList: List<BrowserFileFilter>) {

    fun apply(stream: Stream<Path>): Stream<Path> {
        return stream.filter { blacklistFilter()?.applies(it.toFile()) ?: true }
            .filter { filterList.any { filter -> filter.applies(it.toFile()) } }
    }

    fun applies(file: File): Boolean {
        return blacklistFilter()?.applies(file) ?: false
                && filterList.any { filter -> filter.applies(file) }
    }

    private fun blacklistFilter() = filterList.find { it is BlacklistFilter }

    override fun toString(): String {
        return filterList.joinToString(", ", "", "")
    }
}
