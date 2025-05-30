package io.github.footermandev.deuterium.core

data class DPadding(
    val top: Float = 0f,
    val right: Float = 0f,
    val bottom: Float = 0f,
    val left: Float = 0f
) {
    constructor(all: Float): this(all, all, all, all)
    constructor(vertical: Float, horizontal: Float): this(vertical, horizontal, vertical, horizontal)
}
