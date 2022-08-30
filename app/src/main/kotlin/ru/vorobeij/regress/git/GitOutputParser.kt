package ru.vorobeij.regress.git

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val DATE_FORMAT_STRING = "EEE MMM dd HH:mm:ss yyyy"

class GitOutputParser {

    fun date(date: String): Calendar = Calendar.getInstance().apply {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT_STRING, Locale.ENGLISH)
        time = simpleDateFormat.parse(date)
    }

    fun gitShow(gitOutput: String): GitShowInfo {
        val output = gitOutput.split("\n")
        val messageLines = output.slice(3..output.lastIndex)
        val author = output[1].replace("Author: ", "").trim()

        return GitShowInfo(
            commit = output[0].replace("commit ", "").trim(),
            authorName = author.split(" ")[0],
            authorEmail = author.split(" ")[1].trim('<').trim('>'),
            date = date(output[2].replace("Date:", "").trim()),
            message = messageLines.joinToString("").trim(),
        )
    }

    /**
     * @param gitStatusOutput: `git status`
     * @return true if has uncommitted changes
     */
    fun hasUncommittedChanges(gitStatusOutput: String): Boolean = gitStatusOutput.contains("nothing to commit, working tree clean").not()

    /**
     * @property commit
     * @property authorName
     * @property authorEmail
     * @property date
     * @property message
     */
    data class GitShowInfo(
        val commit: String,
        val authorName: String,
        val authorEmail: String,
        val date: Calendar,
        val message: String
    )
}
