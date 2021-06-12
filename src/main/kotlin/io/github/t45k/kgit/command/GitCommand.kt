package io.github.t45k.kgit.command

import io.github.t45k.kgit.entity.GitRepository

sealed class GitCommand(protected val repo: GitRepository) {
    abstract fun execute(): String
}
