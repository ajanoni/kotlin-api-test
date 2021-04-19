package com.ajanoni.directory.cli

import io.quarkus.picocli.runtime.annotations.TopCommand
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import picocli.CommandLine
import javax.enterprise.inject.Default
import javax.inject.Inject

@QuarkusMain
@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = [DirectoryCommand::class])
class EntryCommand: QuarkusApplication {

    @field:Default
    @field:Inject
    lateinit var factory: CommandLine.IFactory

    override fun run(vararg args: String?): Int {
        return CommandLine(this, factory).execute(*args)
    }

}
