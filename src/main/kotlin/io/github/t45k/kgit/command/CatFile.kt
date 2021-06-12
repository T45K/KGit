package io.github.t45k.kgit.command

import io.github.t45k.kgit.entity.GitRepository
import io.github.t45k.kgit.mixin.Zlib
import kotlin.io.path.readBytes

class CatFile(repo: GitRepository, private val option: String, private val hash: String) : GitCommand(repo), Zlib {
    override fun execute(): String {
        val content = repo.resolvePathFromGitDir("objects", hash.substring(0, 2), hash.substring(2)).readBytes()
        val decompressed = decompress(content)
        val type = decompressed.substringBefore(' ')
        val contentSize = decompressed.substringAfter(' ').substringBefore('\u0000')
        val prettyPrinted = decompressed.substringAfter('\u0000')
        if (contentSize.toInt() != prettyPrinted.length) {
            throw RuntimeException()
        }
        return when (option) {
            "-e" -> "" // malformed or not
            "-t" -> type
            "-s" -> contentSize
            "-p" -> prettyPrinted
            else -> throw RuntimeException()
        }
    }
}
