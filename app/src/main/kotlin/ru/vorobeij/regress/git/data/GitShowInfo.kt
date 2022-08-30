package ru.vorobeij.regress.git.data

import java.util.Calendar
import ru.vorobeij.jacoco.ExcludeGenerated

/**
 * @property commit
 * @property authorName
 * @property authorEmail
 * @property date
 * @property message
 */
@ExcludeGenerated
data class GitShowInfo(
    val commit: String,
    val authorName: String,
    val authorEmail: String,
    val date: Calendar,
    val message: String
)
