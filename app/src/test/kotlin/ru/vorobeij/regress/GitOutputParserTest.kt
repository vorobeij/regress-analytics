package ru.vorobeij.regress

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.vorobeij.core.git.GitOutputParser
import ru.vorobeij.core.git.data.GitShowInfo

internal class GitOutputParserTest {

    @Test
    fun `git show`() {
        val gitOutput = """
commit e9b0a9a92c42ddd2ce8ceca302f43e53a547b34d
Author: Evgenii <vorobeievgeniy@gmail.com>
Date:   Mon Aug 29 11:36:33 2022 +0300

    Macrobenchmarks load benchmark data
        """.trimIndent()

        Assertions.assertEquals(
            GitShowInfo(
                commit = "e9b0a9a92c42ddd2ce8ceca302f43e53a547b34d",
                authorName = "Evgenii",
                authorEmail = "vorobeievgeniy@gmail.com",
                date = GitOutputParser().date("Mon Aug 29 11:36:33 2022 +0300"),
                message = "Macrobenchmarks load benchmark data",
            ),
            GitOutputParser().gitShow(gitOutput)
        )
    }

    @Test
    fun `parse date`() {
        Assertions.assertEquals(
            "Mon Aug 29 11:36:33 MSK 2022",
            GitOutputParser().date("Mon Aug 29 11:36:33 2022 +0300").time.toString()
        )
    }

    @Test
    fun `has uncommitted changes`() {
        Assertions.assertEquals(
            false,
            GitOutputParser().hasUncommittedChanges(
                """
                On branch master
                nothing to commit, working tree clean
                """.trimIndent()
            )
        )
    }

    @Test
    fun `does not have uncommitted changes`() {
        Assertions.assertEquals(
            true,
            GitOutputParser().hasUncommittedChanges(
                """
                
On branch master
Your branch is up to date with 'origin/master'.

Changes to be committed:
  (use "git restore --staged <file>..." to unstage)
        new file:   summary.txt

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git restore <file>..." to discard changes in working directory)
        modified:   ci/macrobenchmarks.sh
        modified:   ci/microbenchmarks.sh
        modified:   fastlane/README.md
        modified:   fastlane/report.xml
                """.trimIndent()
            )
        )
    }
}
