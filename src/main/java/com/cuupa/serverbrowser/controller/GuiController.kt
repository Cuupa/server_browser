package com.cuupa.serverbrowser.controller

import com.cuupa.serverbrowser.services.FileCollector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GuiController {

    @Autowired
    private var fileCollector: FileCollector? = null

    @GetMapping("/")
    fun index(model: Model): String {
        val list = fileCollector!!.collect("/")
        model.addAttribute("gui", Gui("/", list))
        return "index"
    }

    @GetMapping("/dir")
    fun index1(@ModelAttribute("gui") gui: Gui, model: Model, @RequestParam("path") path: String): String {
        if (path.contains("bootstrap") || path.contains("sw.js")) {
            return "index"
        }
        val list = fileCollector?.collect(path)
        model.addAttribute("gui", Gui(path, list))
        return "index"
    }

    private fun getDir(gui: Gui, dir: String): String {
        val dir = if (gui.currentDir != null) {
            "${gui.currentDir}/$dir"
        } else {
            "/$dir"
        }
        return dir
    }
}