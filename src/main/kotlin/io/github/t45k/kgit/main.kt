package io.github.t45k.kgit

import io.github.t45k.kgit.command.create

fun main(args: Array<String>) {
    println(create(args).execute())
}
