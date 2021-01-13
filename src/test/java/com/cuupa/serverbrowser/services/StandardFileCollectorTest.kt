package com.cuupa.serverbrowser.services

import com.cuupa.serverbrowser.services.filter.FilterBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class StandardFileCollectorTest {

    val directoryToTest = "src/main/java/com/cuupa/serverbrowser/services"

    private val unitToTest = StandardFileCollector()

    @Test
    fun collectEmptyString() {
        val list = unitToTest.collect("")
        assertEquals(1, list.size)
    }

    /**
     * Lists the src-path. Should be two results. "main" and "test"
     */
    @Test
    fun collectSourceDirectory() {
        val list = unitToTest.collect("src")
        assertEquals(3, list.size)
    }

    @Test
    fun collectWithoutFilters() {
        val list = unitToTest.collect(directoryToTest)
        assertEquals(6, list.size)
    }

    @Test
    fun collectWithoutFiles() {
        val list = unitToTest.collect(directoryToTest, FilterBuilder().withDirectory().build())
        assertEquals(2, list.size)
    }

    @Test
    fun collectWithoutDirectories() {
        val list = unitToTest.collect(directoryToTest, FilterBuilder().withFiles().build())
        assertEquals(5, list.size)
    }

    @Test
    fun collectDirectoryWithMatchingDirName() {
        val list = unitToTest.collect(directoryToTest, FilterBuilder().withDirectory("filter").build())
        assertEquals(2, list.size)
    }

    @Test
    fun collectDirectoryWithNonMatchingDirName() {
        val list = unitToTest.collect(directoryToTest, FilterBuilder().withDirectory("abc").build())
        assertEquals(1, list.size)
    }

    @Test
    fun collectFilesWithMatchingFileName() {
        val list = unitToTest.collect(directoryToTest, FilterBuilder().withFiles("collector").build())
        assertEquals(3, list.size)
    }
}