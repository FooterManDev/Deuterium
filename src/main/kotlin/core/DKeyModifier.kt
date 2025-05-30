package io.github.footermandev.deuterium.core

enum class DKeyModifier {
    Ctrl, Shift, Alt, Meta;

    companion object {
        fun fromAwt(modifiers: Int): Set<DKeyModifier> {
            val result = mutableSetOf<DKeyModifier>()

            if((modifiers and 0x02) != 0) result.add(Ctrl)
            if((modifiers and 0x01) != 0) result.add(Shift)
            if((modifiers and 0x08) != 0) result.add(Alt)
            if((modifiers and 0x04) != 0) result.add(Meta)

            return result
        }
    }
}