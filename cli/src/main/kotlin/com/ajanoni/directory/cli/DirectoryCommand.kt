package com.ajanoni.directory.cli

import com.ajanoni.directory.cli.data.ErrorResult
import com.fasterxml.jackson.databind.ObjectMapper
import org.eclipse.microprofile.rest.client.inject.RestClient
import picocli.CommandLine
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.ws.rs.WebApplicationException

@CommandLine.Command(name = "listdir")
class DirectoryCommand : Runnable {
    private val separator: String = "=".repeat(100)

    @field:Inject
    @field:RestClient
    protected open lateinit var directoryHandler: DirectoryHandler

    @field:Inject
    protected open lateinit var objectMapper: ObjectMapper

    @CommandLine.Option(names = ["--path"], description = ["Directory path"], required = true)
    lateinit var directoryPath: String

    override fun run() {
        try {
            printFiles()
        } catch(e: WebApplicationException) {
            if (e.response.status == 404) {
                if(e.response.hasEntity()) {
                    val rawJson = e.response.readEntity(String::class.java)
                    val error = objectMapper.readValue(rawJson, ErrorResult::class.java)
                    println(separator)
                    println(error.message)
                    println(separator)
                    return
                }

                println("Resource not found.")
            }
        } catch(e: RuntimeException) {
            println("Unexpected error.")
        }
    }

    private fun printFiles() {
        val fsResult = directoryHandler.list(directoryPath)
        println(separator)
        println("Listing directory : $directoryPath")
        println(separator)
        val dateFormat = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());
        System.out.format("%-50s%-15s%-15s%n", "File", "Size (Bytes)", "Last Modification")
        println(separator)
        fsResult.items.forEach {
            System.out.format(
                "%-50s%-15s%-15s%n", it.name, it.size,
                dateFormat.format(it.lastModification)
            )
        }
        println(separator)
        println("Total Items: ${fsResult.totalItems}\tTotal Size (Bytes): ${fsResult.totalSize}")
        println(separator)
    }
}
