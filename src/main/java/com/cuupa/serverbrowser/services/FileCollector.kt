package com.cuupa.serverbrowser.services

import com.cuupa.serverbrowser.services.filter.BrowserFileFilter

/**
 * Standard interface for listing files in a specific directory
 *
 */
interface FileCollector {

    /**
     * Lists the content of a directory with a filter
     *
     * @author - Simon Thiel
     *
     * @param dir - The directory to list
     * @param filter - A list of filters
     * @return Returns a list of @see com.cuupa.serverbrowser.services.BrowserFileObject
     * If no files are found, applicable according to the filter or an error occures, it returns an empty list
     * @throws AccessDeniedException If the program has no access to a directory
     */
    fun collect(dir: String?, filters: List<BrowserFileFilter>): List<BrowserFileObject>

    /**
     * Lists the content of a directory with a MatchAllFileFilter
     * @author - Simon Thiel
     *
     * @param dir - The directory to list
     * @return Returns a list of BrowserFileObject.
     * If no files are found, applicable according to the filter or an error occures, it returns an empty list
     */
    fun collect(dir: String?): List<BrowserFileObject>

}
