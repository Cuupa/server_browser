package com.cuupa.serverbrowser.controller

import com.cuupa.serverbrowser.services.FileCollector
import com.cuupa.serverbrowser.services.filter.BlacklistFilter
import com.cuupa.serverbrowser.services.filter.FilterBuilder
import org.apache.commons.io.IOUtils.copy
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

    @Autowired
    private var fileCollector: FileCollector? = null

    @Value("\${filebrowser.downloadable:false}")
    private var downloadable = false

    @Value("\${filebrowser.blacklisted}")
    private var blacklisted = listOf<String>()

    @GetMapping("")
    fun index(model: Model): String {
        val list = fileCollector?.collect("/", filters()) ?: listOf()
        model.addAttribute("gui", Gui("/", list, downloadable))
        return "index"
    }

    @GetMapping("/list")
    fun index1(model: Model, @RequestParam("path") path: String): String {
        val list = fileCollector?.collect(path, filters()) ?: listOf()
        model.addAttribute("gui", Gui(path, list, downloadable))
        return "index"
    }

    @GetMapping("/download")
    fun getFile(
        @RequestParam("fileName") fileName: String,
        response: HttpServletResponse
    ): String {
        try {
            val filter = filters().find { it is BlacklistFilter }
            filter?.let {
                if (filter.applies(File(fileName))) {
                    copy(FileInputStream(fileName), response.outputStream)
                    response.flushBuffer()
                }
            }
        } catch (ex: IOException) {
        }
        return "index"
    }

    private fun filters() = FilterBuilder().withDirectory().withFiles().excludeDirectories(blacklisted).build()

}