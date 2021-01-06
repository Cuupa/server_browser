package com.cuupa.serverbrowser.services.filter

class FilterBuilder {

    private val list = mutableListOf<BrowserFileFilter>()

    fun excludeHidden(): FilterBuilder {
        list.add(ExcludeHiddenFileFilter())
        return this
    }

    fun withFiles(): FilterBuilder {
        withFiles(null)
        return this
    }

    fun withFiles(filter: String?): FilterBuilder {
        list.add(FileFilter(filter))
        return this
    }

    fun withDirectory(): FilterBuilder {
        withDirectory(null)
        return this
    }


    fun withDirectory(filter: String?): FilterBuilder {
        list.add(DirectoryFilter(filter))
        return this
    }

    fun build(): List<BrowserFileFilter> {
        return list.toList()
    }
}
