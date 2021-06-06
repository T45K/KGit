package io.github.t45k.kgit.mixin

import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.createFile
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class GitConfigParserTest : GitConfigParser {
    @Test
    fun testParse() {
        File(".", "tmp").deleteRecursively()
        Path.of(".")
            .resolve("tmp").createDirectory()
            .resolve(".git").createDirectory()
            .resolve("config").createFile()
            .writeText(
                """
                [core]
                repositoryformatversion = 1
            """.trimIndent()
            )
        val config = parseConfigFile(Path.of(".", "tmp", ".git", "config"))
        assertContains(config, "core")
        assertContains(config["core"]!!, "repositoryformatversion")
        assertEquals(config["core"]!!["repositoryformatversion"], "1")
        File(".", "tmp").deleteRecursively()
    }
}
