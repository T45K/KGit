package io.github.t45k.kgit.mixin

import java.nio.file.Path
import kotlin.io.path.readLines

interface INIParser {
    fun parseINI(path: Path): GitConfig = parseINI(path.readLines())

    fun parseINI(lines: List<String>): GitConfig =
        extractHeaders(lines)
            .let { headers ->
                headers.mapIndexed { index, header ->
                    val lastLine = if (index < headers.size - 1) {
                        headers[index + 1].first
                    } else {
                        lines.size
                    }
                    header.second to parseKeyValues(lines.subList(header.first + 1, lastLine))
                }.toMap()
            }

    private fun extractHeaders(lines: List<String>): List<Pair<Int, String>> =
        lines.mapIndexed { index, line -> index to line }
            .filter { (_, line) -> line.trim().let { it.startsWith("[") && it.endsWith("]") } }
            .map { it.first to it.second.substringAfter("[").substringBefore("]") }

    private fun parseKeyValues(lines: List<String>): Map<String, String> =
        lines.filter { it.contains("=") }
            .map { it.split("=") }
            .filter { it.size == 2 }
            .associate { it[0].trim() to it[1].trim() }
}

/*
 * INI format
 * [header1]
 *   key1 = value1
 *   key2 = value2
 * [header2]
 *   key3 = value3
 */
typealias GitConfig = Map<String, Map<String, String>>
