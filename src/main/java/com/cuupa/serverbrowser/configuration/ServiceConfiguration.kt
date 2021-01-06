package com.cuupa.serverbrowser.configuration

import com.cuupa.serverbrowser.services.FileCollector
import com.cuupa.serverbrowser.services.StandardFileCollector
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ServiceConfiguration {

    @Bean
    open fun fileCollector(): FileCollector {
        return StandardFileCollector()
    }
}