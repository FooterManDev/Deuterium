package io.github.footermandev.deuterium.core

import io.github.footermandev.deuterium.later
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import org.jetbrains.skiko.SkikoRenderDelegate
import java.awt.Dimension
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE

class DeuteriumWindow(
    title: String = "Deuterium Application",
    width: Int = 800,
    height: Int = 600
) {

    private val frame = JFrame(title)
    private val skiaLayer = SkiaLayer()
    private var rootContainer: DContainer? = null
    private val inputMngr = DInputMngr()

    init {
        frame.defaultCloseOperation = EXIT_ON_CLOSE
        frame.size = Dimension(width, height)
        frame.add(skiaLayer)

        setupSkiaLayer()
        inputHandler()
    }

    private fun setupSkiaLayer() {
        skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, object : SkikoRenderDelegate {
            override fun onRender(
                canvas: Canvas,
                width: Int,
                height: Int,
                nanoTime: Long
            ) {
                canvas.clear(0xffffffff.toInt())
                
                rootContainer?.let { root ->
                    root.setBounds(0f, 0f, width.toFloat(), height.toFloat())
                    root.render(canvas)
                }
            }
        })
    }
    
    private fun inputHandler() {
        skiaLayer.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val btn = DMouseBtn.fromAwt(e.button)
                val modifiers = DKeyModifier.fromAwt(e.modifiersEx)
                inputMngr.mouseClick(rootContainer, e.x.toFloat(), e.y.toFloat(), btn, modifiers)
                redraw()
            }

            override fun mousePressed(e: MouseEvent) {
                val btn = DMouseBtn.fromAwt(e.button)
                val modifiers = DKeyModifier.fromAwt(e.modifiersEx)
                inputMngr.mousePress(rootContainer, e.x.toFloat(), e.y.toFloat(), btn, modifiers)
                redraw()
            }

            override fun mouseReleased(e: MouseEvent) {
                val btn = DMouseBtn.fromAwt(e.button)
                val modifiers = DKeyModifier.fromAwt(e.modifiersEx)
                inputMngr.mouseRelease(rootContainer, e.x.toFloat(), e.y.toFloat(), btn, modifiers)
                redraw()

            }

            override fun mouseDragged(e: MouseEvent) {
                val modifiers = DKeyModifier.fromAwt(e.modifiersEx)
                inputMngr.mouseMove(rootContainer, e.x.toFloat(), e.y.toFloat(), modifiers)
            }

            override fun mouseMoved(e: MouseEvent) {
                val modifiers = DKeyModifier.fromAwt(e.modifiersEx)
                inputMngr.mouseMove(rootContainer, e.x.toFloat(), e.y.toFloat(), modifiers)
                redraw()
            }
        })

        skiaLayer.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                val key = DKey.fromCode(e.keyCode)
                val char = if(e.keyChar != KeyEvent.CHAR_UNDEFINED) e.keyChar else null
                val modifiers = DKeyModifier.fromAwt(e.modifiersEx)
                inputMngr.keyPress(key, char, modifiers)
                redraw()
            }

            override fun keyReleased(e: KeyEvent) {
                val key = DKey.fromCode(e.keyCode)
                val char = if(e.keyChar != KeyEvent.CHAR_UNDEFINED) e.keyChar else null
                val modifiers = DKeyModifier.fromAwt(e.modifiersEx)
                inputMngr.keyRelease(key, char, modifiers)
                redraw()
            }
        })

        skiaLayer.isFocusable = true
    }

    fun setContent(container: DContainer) {
        rootContainer = container
        skiaLayer.needRedraw()
    }

    inline fun content(block: DContainer.() -> Unit) {
        val container = object : DContainer() {}
        container.block()
        setContent(container)
    }

    fun show() {
        later {
            frame.isVisible = true
            skiaLayer.requestFocus()
        }
    }
    
    fun redraw() { skiaLayer.needRedraw() }

    fun getInputMngr(): DInputMngr = inputMngr
}