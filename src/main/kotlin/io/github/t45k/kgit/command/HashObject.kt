package io.github.t45k.kgit.command

import io.github.t45k.kgit.entity.GitRepository
import io.github.t45k.kgit.entity.`object`.GitObject

class HashObject(repo: GitRepository) : GitCommand(repo) {
    override fun execute(): String {
        TODO()
    }

    private fun writeObject(gitObject: GitObject) {
        TODO()
    }
}
