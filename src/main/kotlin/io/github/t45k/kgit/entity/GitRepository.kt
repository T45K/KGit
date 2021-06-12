package io.github.t45k.kgit.entity

import io.github.t45k.kgit.mixin.GitConfig
import io.github.t45k.kgit.mixin.GitConfigParser
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.notExists

class GitRepository private constructor(path: Path) : GitConfigParser {
    private val worktree: Path
    private val gitDir: Path
    private val config: GitConfig

    companion object {
        fun of(path: Path): GitRepository =
            if (path.resolve(".git").exists()) {
                GitRepository(path)
            } else {
                of(path.parent ?: throw RuntimeException("Not a Git repository"))
            }

        fun init(path: Path): GitRepository {
            TODO()
        }
    }

    init {
        // read git repo
        if (path.resolve(".git").notExists()) {
            throw RuntimeException("Not a Git repository $path")
        }
        worktree = path
        gitDir = path.resolve(".git")

        // read config
        if (gitDir.resolve("config").notExists()) {
            throw java.lang.RuntimeException("Configuration file missing")
        }
        config = parseConfigFile(gitDir.resolve("config"))

        config["core"]?.get("repositoryformatversion")?.toInt()
            ?.takeIf { it == 0 }
            ?: throw RuntimeException("Unsupported repositoryformatversion")
    }

    fun resolvePathFromGitDir(vararg str: String, path: Path = gitDir): Path =
        if (str.size == 1) {
            path.listDirectoryEntries()
                .first { it.name.startsWith(str[0]) }
        } else {
            resolvePathFromGitDir(*str.sliceArray(1 until str.size), path = path.resolve(str[0]))
        }
}
