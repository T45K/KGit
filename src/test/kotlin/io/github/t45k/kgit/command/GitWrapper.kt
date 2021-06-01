package io.github.t45k.kgit.command

class GitWrapper {
    companion object {
        fun execute(vararg commands: String): String =
            ProcessBuilder("git", *commands)
                .redirectErrorStream(true)
                .start()
                .inputStream
                .bufferedReader()
                .readText()
    }
}
