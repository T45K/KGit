package io.github.t45k.kgit.command

import java.lang.RuntimeException

fun create(args: Array<String>): GitCommand =
    when (args[0]) {
        "cat-file" -> CatFile(args[1], args[2])
        else -> throw RuntimeException()
    }
