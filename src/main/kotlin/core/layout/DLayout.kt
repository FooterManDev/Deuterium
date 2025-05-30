package io.github.footermandev.deuterium.core.layout

import io.github.footermandev.deuterium.core.DComponent
import io.github.footermandev.deuterium.core.DContainer

sealed class DLayout {

    abstract fun components(container: DContainer, components: List<DComponent>)

    object None : DLayout() {
        override fun components(
            container: DContainer,
            components: List<DComponent>
        ) {}
    }


}