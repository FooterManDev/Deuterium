package io.github.footermandev.deuterium.core

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint
import org.jetbrains.skia.RRect
import org.jetbrains.skia.Rect

abstract class DComponent {
    var x: Float = 0f
    var y: Float = 0f
    var width: Float  = 0f
    var height: Float = 0f
    var visible: Boolean = true
    var enabled: Boolean = true

    protected var parent: DContainer? = null
    protected val children = mutableListOf<DComponent>()

    var bgColor: Int? = null
    var foregroundColor: Int? = 0xFF000000.toInt()
    var cornerRadius: Float = 0f
    var padding: DPadding = DPadding()
    var margin: DMargin = DMargin()

    var onMouseClick: ((DMouseEvent) -> Unit)? = null
    var onMouseMove: ((DMouseEvent) -> Unit)? = null
    var onMouseEnter: (() -> Unit)? = null
    var onMouseExit: (() -> Unit)? = null
    var onKeyPress: ((DKeyEvent) -> Unit)? = null
    var onKeyRelease: ((DKeyEvent) -> Unit)? = null

    internal fun getChildren(): List<DComponent> = children

    fun render(canvas: Canvas) {
        if(!visible) return

        canvas.save()
        canvas.translate(x, y)

        bgColor?.let { color ->
            val paint = Paint().apply { this.color = color }
            val rect = Rect.makeWH(width, height)
            if(cornerRadius > 0f) {
                val rrect = RRect.makeXYWH(0f, 0f, width, height, cornerRadius, cornerRadius)
                canvas.drawRRect(rrect, paint)
            } else canvas.drawRect(rect, paint)
        }

        onRender(canvas)

        children.forEach { child -> child.render(canvas) }

        canvas.restore()
    }

    protected abstract fun onRender(canvas: Canvas)

    fun setBounds(x: Float, y: Float, width: Float, height: Float) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        onBoundsChanged()
    }

    protected open fun onBoundsChanged() {}

    fun add(comp: DComponent) {
        children.add(comp)
        comp.parent = this as? DContainer
    }

    fun remove(comp: DComponent) {
        children.remove(comp)
        comp.parent = null
    }

    fun contains(x: Float, y: Float): Boolean = x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height

    internal fun mouseEvent(e: DMouseEvent): Boolean {
        if(!visible || !enabled) return false

        val localEvent = e.copy(
            x = e.x - this.x,
            y = e.y - this.y
        )

        if(contains(e.x, e.y)) {
            when(e.type) {
                DMouseEvent.Type.Click -> onMouseClick?.invoke(localEvent)
                DMouseEvent.Type.Move  -> onMouseMove?.invoke(localEvent)
                DMouseEvent.Type.Enter -> onMouseEnter?.invoke()
                DMouseEvent.Type.Exit  -> onMouseExit?.invoke()
                else -> {}
            }
            return true
        }
        return false
    }

    internal fun keyEvent(e: DKeyEvent): Boolean {
        if(!visible || !enabled) return false

        when(e.type) {
            DKeyEvent.Type.Press -> onKeyPress?.invoke(e)
            DKeyEvent.Type.Release -> onKeyPress?.invoke(e)
        }
        return true
    }
}