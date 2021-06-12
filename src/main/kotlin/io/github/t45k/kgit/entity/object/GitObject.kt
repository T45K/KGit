package io.github.t45k.kgit.entity.`object`

import io.github.t45k.kgit.entity.GitRepository

sealed class GitObject(protected val repo: GitRepository) {
    abstract fun serialize():String
    abstract fun deserialize()
}

enum class Type(val value: String) {
    COMMIT("commit"), TREE("tree"), TAG("tag"), BLOB("blob")
}
