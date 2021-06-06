package io.github.t45k.kgit.entity

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.createFile
import kotlin.io.path.writeText
import kotlin.test.Test

internal class GitRepositoryTest {

    @Test
    fun testGivenPathIsRepository() {
        assertDoesNotThrow { GitRepository.of(Path.of(".")) }
    }

    @Test
    fun testGivenPathIsSubdirectoryOfRepository() {
        assertDoesNotThrow { GitRepository.of(Path.of(".", "src")) }
    }

    @Test
    fun testThrowGivenPathIsNotRepository() {
        assertThrows<RuntimeException> { GitRepository.of(Path.of(".").parent) }
    }

    @Test
    fun testThrownGivenConfigIsInvalid() {
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
        assertThrows<RuntimeException> { GitRepository.of(Path.of(".", "tmp")) }
        File(".", "tmp").deleteRecursively()
    }
}