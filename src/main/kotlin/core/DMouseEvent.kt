package io.github.footermandev.deuterium.core

data class DMouseEvent(
    val x: Float,
    val y: Float,
    val btn: DMouseBtn,
    val type: Type,
    val modifiers: Set<DKeyModifier> = emptySet(),
    val clickCount: Int = 1
) {
    enum class Type {
        Press, Release, Click, Move, Drag, Enter, Exit, Wheel
    }
}

enum class DMouseBtn {
    Left, Right, Middle, Button4, Button5;

    companion object {
        fun fromAwt(btn: Int): DMouseBtn = when(btn) {
            1 -> Left
            2 -> Middle
            3 -> Right
            4 -> Button4
            5 -> Button5
            else -> Left
        }
    }
}
