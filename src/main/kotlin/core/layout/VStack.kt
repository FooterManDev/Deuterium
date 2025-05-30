package io.github.footermandev.deuterium.core.layout

import io.github.footermandev.deuterium.core.DComponent
import io.github.footermandev.deuterium.core.DContainer

data class VStack(
    val spacing: Float = 0f,
    val alignment: Alignment = Alignment.Start
): DLayout() {
    override fun components(
        container: DContainer,
        components: List<DComponent>
    ) {
        var currentY = container.padding.top

        components.forEach { c ->
            val x = when(alignment) {
                Alignment.Start -> container.padding.left
                Alignment.Center -> (container.width - c.width) / 2f
                Alignment.End -> container.width - c.width - container.padding.right
            }

            c.setBounds(x, currentY, c.width, c.height)
            currentY += c.height + spacing
        }
    }
}
