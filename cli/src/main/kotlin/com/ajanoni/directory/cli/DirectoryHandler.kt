package com.ajanoni.directory.cli

import com.ajanoni.directory.cli.data.FsSearchResult
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.*


@Path("/directory")
@RegisterRestClient(configKey="directory-api")
interface DirectoryHandler {

    @GET
    @Produces("application/json")
    fun list(@QueryParam("directoryPath") directoryPath: String?) : FsSearchResult

}
