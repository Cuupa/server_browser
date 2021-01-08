package com.cuupa.serverbrowser.controller

import com.cuupa.serverbrowser.services.FileCollector
import com.cuupa.serverbrowser.services.filter.Filters
import org.apache.commons.io.IOUtils.copy
import org.apache.juli.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/filebrowser")
class GuiController {

    private val log = LogFactory.getLog(GuiController::class.java)

    @Autowired
    private var fileCollector: FileCollector? = null

    @Autowired
    private var filters: Filters? = null

    @Value("\${filebrowser.downloadable:false}")
    private var downloadable = false

    private val path =
        File(ClassLoader.getSystemClassLoader()?.getResource(".")?.path ?: System.getProperty("user.home")).absolutePath

    @GetMapping("")
    fun index(model: Model): String {
        val list = fileCollector?.collect(path, filters) ?: listOf()
        model.addAttribute(Gui.name, Gui(path, list, downloadable))
        return "index"
    }

    @GetMapping("/list")
    fun index1(model: Model, @RequestParam("path") path: String): String {
        val list = fileCollector?.collect(path, filters) ?: listOf()
        model.addAttribute(Gui.name, Gui(path, list, downloadable))
        return "index"
    }

    @GetMapping("/download")
    fun getFile(
        @RequestParam("fileName") fileName: String,
        response: HttpServletResponse
    ): String {
        try {
            if (filters?.applies(File(fileName)) == true) { // make sure the target is not blacklisted etc
                copy(FileInputStream(fileName), response.outputStream)
                response.flushBuffer()
            } else {
                log.error("Download of $fileName denied")
            }
        } catch (ex: IOException) {
            log.error(fileName, ex)
        }
        return "index"
    }
}