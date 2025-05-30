package io.github.footermandev.deuterium.core

object DColors {
    const val White = 0xffffffff.toInt()
    const val Black = 0xff000000.toInt()
    const val Empty = 0x00000000

    const val Blue = 0xFF2196F3.toInt()
    const val Green = 0xFF4CAF50.toInt()
    const val Red = 0xFFF44336.toInt()
    const val Orange = 0xFFFF9800.toInt()

    fun rgb(r: Int, g: Int, b: Int): Int     = (0xff shl 24) or (r shl 16) or (g shl 8) or b
    fun rgba(r: Int, g: Int, b: Int, a: Int) = (a shl 24) or (r shl 16) or(g shl 8) or b
}