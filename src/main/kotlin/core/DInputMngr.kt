package io.github.footermandev.deuterium.core

class DInputMngr {
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var hoveredComp: DComponent? = null
    private var focusedComp: DComponent? = null
    private val pressedKeys = mutableSetOf<DKey>()

    fun mouseMove(rootComp: DComponent?, x: Float, y: Float, modifiers: Set<DKeyModifier>) {
        val newComp = findComponentAt(rootComp, x, y)

        if(newComp != hoveredComp) {
            hoveredComp?.mouseEvent(DMouseEvent(
                lastX, lastY,
                DMouseBtn.Left, DMouseEvent.Type.Exit,
                modifiers
            ))

            newComp?.mouseEvent(DMouseEvent(
                x, y,
                DMouseBtn.Left, DMouseEvent.Type.Enter,
                modifiers
            ))

            hoveredComp = newComp
        }

        newComp?.mouseEvent(DMouseEvent(
            x, y,
            DMouseBtn.Left, DMouseEvent.Type.Move,
            modifiers
        ))

        lastX = x
        lastY = y
    }

    fun mouseClick(rootComp: DComponent?, x: Float, y: Float, btn: DMouseBtn, modifiers: Set<DKeyModifier>) {
        val comp = findComponentAt(rootComp, x, y)
        comp?.mouseEvent(DMouseEvent(
            x, y,
            btn,DMouseEvent.Type.Click,
            modifiers
        ))

        focusedComp = comp
    }

    fun mousePress(rootComp: DComponent?, x: Float, y: Float, btn: DMouseBtn, modifiers: Set<DKeyModifier>) {
        val comp = findComponentAt(rootComp, x, y)
        comp?.mouseEvent(DMouseEvent(
            x, y,
            btn, DMouseEvent.Type.Press,
            modifiers
        ))
    }

    fun mouseRelease(rootComp: DComponent?, x: Float, y: Float, btn: DMouseBtn, modifiers: Set<DKeyModifier>) {
        val comp = findComponentAt(rootComp, x, y)
        comp?.mouseEvent(DMouseEvent(
            x, y,
            btn, DMouseEvent.Type.Release,
            modifiers
        ))
    }

    fun keyPress(key: DKey, char: Char?, modifiers: Set<DKeyModifier>) {
        pressedKeys.add(key)
        focusedComp?.keyEvent(DKeyEvent(key, char, DKeyEvent.Type.Press, modifiers))
    }

    fun keyRelease(key: DKey, char: Char?, modifiers: Set<DKeyModifier>) {
        pressedKeys.remove(key)
        focusedComp?.keyEvent(DKeyEvent(key, char, DKeyEvent.Type.Release, modifiers))
    }

    private fun findComponentAt(comp: DComponent?, x: Float, y: Float): DComponent? {
        if(comp == null || !comp.visible || !comp.contains(x, y)) return null

        comp.getChildren().reversed().forEach { child ->
            val found = findComponentAt(child, x - comp.x, y - comp.y)
            if(found != null) return found
        }

        return comp
    }

    fun setFocus(comp: DComponent?) { focusedComp = comp }
    fun getFocused(): DComponent? = focusedComp

    fun isKeyPressed(key: DKey): Boolean = key in pressedKeys
}