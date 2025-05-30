package io.github.footermandev.deuterium.core.layout

import io.github.footermandev.deuterium.core.DComponent
import io.github.footermandev.deuterium.core.DContainer

data class HStack(
    val spacing: Float = 0f,
    val alignment: Alignment = Alignment.Start
): DLayout() {
    override fun components(
        container: DContainer,
        components: List<DComponent>
    ) {
        var currentX = container.padding.left

        components.forEach { c ->
            val y = when(alignment) {
                Alignment.Start -> container.padding.top
                Alignment.Center -> (container.height - c.height) / 2f
                Alignment.End -> container.height - c.height - container.padding.bottom
            }

            c.setBounds(currentX, y, c.width, c.height)
            currentX += c.width + spacing
        }
    }
}