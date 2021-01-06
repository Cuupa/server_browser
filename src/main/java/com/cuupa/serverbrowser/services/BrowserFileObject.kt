package com.cuupa.serverbrowser.services

import java.time.LocalDateTime

class BrowserFileObject(
    val type: FileObjectType,
    val parent: String,
    val filename: String,
    val size: Long,
    val lastAccessDate: LocalDateTime
) {
    override fun toString(): String {
        return "BrowserFileObject(type=$type, parent='$parent', filename='$filename', size=$size, lastAccessDate=$lastAccessDate)"
    }
}
