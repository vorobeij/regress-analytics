package ru.vorobeij.core.cmd

import java.io.File
import java.util.concurrent.TimeUnit
import ru.vorobeij.jacoco.ExcludeGenerated

@ExcludeGenerated
fun String.execute(workingDir: File): String? = try {
    val parts = this.split("\\s".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

    proc.waitFor(60, TimeUnit.MINUTES)
    proc.inputStream.bufferedReader().readText()
} catch (e: Exception) {
    e.printStackTrace()
    null
}
