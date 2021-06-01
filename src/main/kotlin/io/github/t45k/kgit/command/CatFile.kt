package io.github.t45k.kgit.command

import java.io.ByteArrayOutputStream
import java.lang.RuntimeException
import java.util.zip.Inflater
import kotlin.io.path.Path
import kotlin.io.path.readBytes
import kotlin.text.Charsets.UTF_8

class CatFile(private val option: String, private val hash: String) : GitCommand {
    override fun execute(): String {
        val content = Path(".git", "objects", hash.substring(0, 2), hash.substring(2)).readBytes()
        val decompressed = decompress(content)
        val type = decompressed.substringBefore(' ')
        val contentSize = decompressed.substringAfter(' ').substringBefore('\u0000')
        val prettyPrinted = decompressed.substring(decompressed.indexOf('\u0000') + 1)
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

    private fun decompress(compressed: ByteArray): String =
        Inflater()
            .also { it.setInput(compressed) }
            .let { inflater ->
                val buffer = ByteArray(1024)
                val outputStream = ByteArrayOutputStream()

                while (!inflater.finished()) {
                    val length = inflater.inflate(buffer)
                    outputStream.write(buffer, 0, length)
                }

                inflater.end()
                outputStream.also { it.close() }
            }.toString(UTF_8)
}
