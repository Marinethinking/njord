package io.madrona.njord.layers

import io.madrona.njord.model.*

/**
 * Geometry Primitives: Point, Line, Area
 *
 * Object: Runway
 *
 * Acronym: RUNWAY
 *
 * Code: 117
 */
class Runway : Layerable() {

    private val areaColor = Color.CHBRN
    private val lineColor = Color.CHBLK
    private val symbol = Sprite.AIRARE02
    override fun preTileEncode(feature: ChartFeature) {
        feature.lineColor(lineColor)
        feature.areaColor(areaColor)
        feature.pointSymbol(symbol)
    }

    override fun layers(options: LayerableOptions) = sequenceOf(
        lineLayerWithColor(lineColor),
        areaLayerWithFillColor(areaColor),
        pointLayerFromSymbol(symbol),
    )
}
