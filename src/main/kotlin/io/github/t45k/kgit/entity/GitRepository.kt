package io.github.t45k.kgit.entity

import io.github.t45k.kgit.mixin.GitConfig
import io.github.t45k.kgit.mixin.INIParser
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.notExists

class GitRepository : INIParser {
    private val worktree: Path
    private val gitDir: Path
    private val config: GitConfig

    constructor(path: Path, isInit: Boolean = false) {
        // read git repo
        if (!isInit && !path.resolve(".git").exists()) {
            throw RuntimeException("Not a Git repository $path")
        }
        worktree = path
        gitDir = path.resolve(".git")

        // read config
        if (gitDir.resolve("config").notExists()) {
            throw java.lang.RuntimeException("Configuration file missing")
        }
        config = parseINI(gitDir.resolve("config"))

        if (!isInit) {
            config["core"]?.get("repositoryformatversion")?.toInt()
                ?.takeIf { it == 0 }
                ?: throw RuntimeException("Unsupported repositoryformatversion")
        }
    }
}
