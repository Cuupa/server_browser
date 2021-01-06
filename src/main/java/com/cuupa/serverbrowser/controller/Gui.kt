package com.cuupa.serverbrowser.controller

import com.cuupa.serverbrowser.services.BrowserFileObject

class Gui(var currentDir: String?, var listings: List<BrowserFileObject>?) {

    constructor() : this(null, listOf<BrowserFileObject>())
}
