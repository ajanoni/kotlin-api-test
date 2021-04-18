package com.ajanoni.core.api

import com.ajanoni.core.directory.service.DirectoryService
import javax.enterprise.context.Dependent
import javax.enterprise.inject.Produces

@Dependent
class Config {

    @Produces
    fun directoryService(): DirectoryService {
        return DirectoryService()
    }

}
