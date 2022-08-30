package ru.vorobeij.regress.git.data

import java.io.File
import java.util.Calendar
import ru.vorobeij.jacoco.ExcludeGenerated
import ru.vorobeij.regress.cmd.execute
import ru.vorobeij.regress.git.GitOutputParser

/**
 * @property commit
 * @property author
 * @property commitDate
 * @property message
 * @property branch
 * @property isCommitted
 */
@ExcludeGenerated
data class GitInfo(
    val commit: String,
    val author: GitAuthor,
    val commitDate: Calendar,
    val message: String,
    val branch: String,
    val isCommitted: Boolean
) {

    @ExcludeGenerated
    companion object {

        fun from(folder: String): GitInfo {
            val gitShow = GitOutputParser()
                .gitShow("git show --summary".execute(File(folder)) ?: throw IllegalArgumentException("Folder is not under git?"))

            return GitInfo(
                commit = gitShow.commit,
                author = GitAuthor(gitShow.authorName, gitShow.authorEmail),
                commitDate = gitShow.date,
                message = gitShow.message,
                branch = branch(File(folder)),
                isCommitted = false
            )
        }

        private fun branch(root: File): String = "git rev-parse --abbrev-ref HEAD".execute(root) ?: ""
    }
}
