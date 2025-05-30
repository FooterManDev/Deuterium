package io.github.footermandev.deuterium.core.layout

import io.github.footermandev.deuterium.core.DComponent
import io.github.footermandev.deuterium.core.DContainer

data class Grid(
    val columns: Int,
    val rows: Int = 1,
    val spacing: Float = 0f
): DLayout() {
    override fun components(
        container: DContainer,
        components: List<DComponent>
    ) {
        val availableWidth = container.width - container.padding.left - container.padding.right
        val availableHeight = container.height - container.padding.top - container.padding.bottom

        val cellWidth = (availableWidth - spacing * (columns - 1)) / columns
        val cellHeight = (availableHeight - spacing * (rows - 1)) / rows

        components.forEachIndexed { index, c ->
            val row = index / columns
            val col = index % columns

            if(row >= rows) return@forEachIndexed

            val x = container.padding.left + col + (cellWidth  + spacing)
            val y = container.padding.top  + row + (cellHeight + spacing)

            c.setBounds(x, y, cellWidth, cellHeight)
        }
    }
}