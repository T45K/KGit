package io.github.t45k.kgit.command

import io.github.t45k.kgit.entity.GitRepository
import org.assertj.core.api.Assertions.`as`
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertDoesNotThrow
import java.nio.file.Path
import kotlin.test.Test

internal class CatFileTest {
    companion object {
        // TODO: This is a temporary git object. Once I implement findObject method, OBJECT_VALUE will be replaced findObject("main.kt","main").
        const val OBJECT_HASH = "4f906e0c811fc9e230eb44819f509cd0627f2600"
    }

    private val repo: GitRepository = GitRepository.of(Path.of(".").toRealPath())

    @Test
    fun testPrettyPrinted() {
        assertThat(CatFile(repo, "-p", OBJECT_HASH).execute())
            .isEqualTo(GitWrapper.execute("cat-file", "-p", OBJECT_HASH))
    }

    @Test
    fun testType() {
        assertThat(CatFile(repo, "-t", OBJECT_HASH).execute())
            .isEqualTo(GitWrapper.execute("cat-file", "-t", OBJECT_HASH).trim()) // TODO: I want to delete this trim.
    }

    @Test
    fun testSize() {
        assertThat(CatFile(repo, "-s", OBJECT_HASH).execute())
            .isEqualTo(GitWrapper.execute("cat-file", "-s", OBJECT_HASH).trim())
    }

    @Test
    fun testEvil() {
        assertDoesNotThrow { assertThat(CatFile(repo, "-e", OBJECT_HASH).execute()) }
    }

    @Test
    fun testPrettyPrintedSuccessfulWhenHashIsShort() {
        assertThat(CatFile(repo, "-p", OBJECT_HASH.substring(0, 6)).execute())
            .isEqualTo(GitWrapper.execute("cat-file", "-p", OBJECT_HASH))
    }
}
