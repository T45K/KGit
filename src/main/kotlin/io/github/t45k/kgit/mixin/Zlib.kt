package io.github.t45k.kgit.mixin

import java.io.ByteArrayOutputStream
import java.util.zip.Inflater

interface Zlib {
    fun decompress(compressed: ByteArray): String =
        ByteArrayOutputStream().use { outputStream ->
            val buffer = ByteArray(1024)
            Inflater()
                .also { it.setInput(compressed) }
                .also { inflater ->
                    while (!inflater.finished()) {
                        val length = inflater.inflate(buffer)
                        outputStream.write(buffer, 0, length)
                    }
                    inflater.end()
                }
            outputStream
        }.toString(Charsets.UTF_8)
}