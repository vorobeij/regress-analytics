package ru.vorobeij.core.git.data

import ru.vorobeij.jacoco.ExcludeGenerated

/**
 * @property name
 * @property email
 */
@ExcludeGenerated
data class GitAuthor(
    val name: String,
    val email: String
)
