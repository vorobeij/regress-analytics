package ru.vorobeij.core.git.data

import java.io.File
import java.util.Calendar
import ru.vorobeij.core.cmd.execute
import ru.vorobeij.core.git.GitOutputParser
import ru.vorobeij.jacoco.ExcludeGenerated

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
                isCommitted = "git status".execute(File(folder))!!.contains("nothing to commit, working tree clean")
            )
        }

        private fun branch(root: File): String = "git rev-parse --abbrev-ref HEAD".execute(root) ?: ""
    }
}
