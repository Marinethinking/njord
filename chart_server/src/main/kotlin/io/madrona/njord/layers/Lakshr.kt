package io.madrona.njord.layers

import io.madrona.njord.model.*

/**
 * Geometry Primitives: Line, Area
 *
 * Object: Lake shore
 *
 * Acronym: LAKSHR
 *
 * Code: 70
 */
class Lakshr : Layerable() {
    private val lineColor = Color.CSTLN

    override fun preTileEncode(feature: ChartFeature) {
        feature.lineColor(lineColor)
    }

    override fun layers(options: LayerableOptions) = sequenceOf(
        lineLayerWithColor(color = lineColor, style = LineStyle.Solid, width = 1f),
    )
}
