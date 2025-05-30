package io.github.footermandev.deuterium.core

data class DKeyEvent(
    val key: DKey,
    val char: Char?,
    val type: Type,
    val modifiers: Set<DKeyModifier> = emptySet()
) {
    enum class Type { Press, Release }
}
