package com.ajanoni.core.api

import com.ajanoni.core.directory.service.DirectoryService
import com.ajanoni.core.directory.service.data.FsSearchResult
import com.ajanoni.core.directory.service.exception.DirectoryNotFoundException
import org.jboss.resteasy.reactive.server.ServerExceptionMapper
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.validation.ConstraintViolationException
import javax.validation.constraints.NotBlank
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/directory")
class DirectoryResource {

    @field:Default
    @field:Inject
    lateinit var service: DirectoryService

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listItems(@NotBlank(message = "directoryPath parameter required")
                  @QueryParam("directoryPath") directoryPath:
                  String) : FsSearchResult {
        return service.list(directoryPath)
    }

    @ServerExceptionMapper
    fun directoryNotFound(ex: DirectoryNotFoundException): Response? {
        return Response.status(Response.Status.NOT_FOUND)
            .entity("{ \"messages\": [\"${ex.message}\"] }")
            .build()
    }

    @ServerExceptionMapper
    fun validationException(ex: ConstraintViolationException): Response? {
        val messages = ex.constraintViolations.joinToString(separator = ",") { "\"${it.message}\"" }
        return Response.status(Response.Status.BAD_REQUEST)
            .entity("{ \"messages\": [$messages] }")
            .build()
    }
}
