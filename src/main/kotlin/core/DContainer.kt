package io.github.footermandev.deuterium.core

import io.github.footermandev.deuterium.core.layout.DLayout
import org.jetbrains.skia.Canvas

abstract class DContainer: DComponent() {

    var layout: DLayout = DLayout.None

    override fun onRender(canvas: Canvas) {}

    override fun onBoundsChanged() {
        super.onBoundsChanged()
        performLayout()
    }

    fun performLayout() { layout.components(this, children) }

    inline fun <T: DComponent> T.configure(block: T.() -> Unit): T {
        this.block()
        this@DContainer.add(this)
        return this
    }
}