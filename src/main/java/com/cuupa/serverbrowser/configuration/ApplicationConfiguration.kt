package com.cuupa.serverbrowser.configuration

import com.cuupa.serverbrowser.services.filter.FilterBuilder
import com.cuupa.serverbrowser.services.filter.Filters
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ServiceConfiguration::class)
open class ApplicationConfiguration {

    @Value("\${filebrowser.blacklisted}")
    private var blacklisted = listOf<String>()

    @Value("\${filebrowser.show_hidden_files:false}")
    private var showHiddenFiles = false

    @Bean
    open fun filters(): Filters {
        val filter = FilterBuilder()
            .withDirectory()
            .withFiles()
            .excludeDirectories(blacklisted)

        if (!showHiddenFiles) {
            filter.excludeHidden()
        }

        return filter.build()
    }
}