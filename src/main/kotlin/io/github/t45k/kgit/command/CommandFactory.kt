package io.github.t45k.kgit.command

import io.github.t45k.kgit.entity.GitRepository
import java.lang.RuntimeException
import java.nio.file.Path

fun create(args: Array<String>): GitCommand {
    val repo = GitRepository.of(Path.of(".").toRealPath())
    return when (args[0]) {
        "cat-file" -> CatFile(repo, args[1], args[2])
        else -> throw RuntimeException()
    }
}
