package com.cuupa.serverbrowser.controller

import com.cuupa.serverbrowser.services.FileCollector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/filebrowser")
class GuiController {

    @Autowired
    private var fileCollector: FileCollector? = null

    @GetMapping("")
    fun index(model: Model): String {
        val list = fileCollector?.collect("/") ?: listOf()
        model.addAttribute("gui", Gui("/", list))
        return "index"
    }

    @GetMapping("/list")
    fun index1(model: Model, @RequestParam("path") path: String): String {
        val list = fileCollector?.collect(path) ?: listOf()
        model.addAttribute("gui", Gui(path, list))
        return "index"
    }
}