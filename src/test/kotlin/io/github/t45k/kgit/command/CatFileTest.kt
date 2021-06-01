package io.github.t45k.kgit.command

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

internal class CatFileTest {
    companion object {
        // TODO: This is a temporary git object. Once I implement findObject method, OBJECT_VALUE will be replaced findObject("main.kt","main").
        const val OBJECT_HASH = "4f906e0c811fc9e230eb44819f509cd0627f2600"
    }

    @Test
    fun testPrettyPrinted() {
        assertThat(CatFile("-p", OBJECT_HASH).execute())
            .isEqualTo(GitWrapper.execute("cat-file", "-p", OBJECT_HASH))
    }

    @Test
    fun testType() {
        assertThat(CatFile("-t", OBJECT_HASH).execute())
            .isEqualTo(GitWrapper.execute("cat-file", "-t", OBJECT_HASH).trim()) // TODO: I want to delete this trim.
    }

    @Test
    fun testSize() {
        assertThat(CatFile("-s", OBJECT_HASH).execute())
            .isEqualTo(GitWrapper.execute("cat-file", "-s", OBJECT_HASH).trim())
    }

    @Test
    fun testEvil() {
        assertDoesNotThrow { assertThat(CatFile("-e", OBJECT_HASH).execute()) }
    }
}
