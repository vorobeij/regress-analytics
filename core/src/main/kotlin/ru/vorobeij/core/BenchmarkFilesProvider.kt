package ru.vorobeij.core

import java.io.File

interface BenchmarkFilesProvider {

    fun files(root: String): Sequence<String>
}

object BenchmarkFilesProviderImpl : BenchmarkFilesProvider {

    override fun files(root: String): Sequence<String> = File(root).walk()
        .filter { it.isFile && it.name.endsWith(".json") }
        .map { it.readText() }
}
