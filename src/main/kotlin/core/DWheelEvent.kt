package io.github.footermandev.deuterium.core

data class DWheelEvent(
    val x: Float,
    val y: Float,
    val deltaX: Float,
    val deltaY: Float,
    val modifiers: Set<DKeyModifier> = emptySet()
)
