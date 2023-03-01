package io.madrona.njord.layers

import io.madrona.njord.model.*

/**
 * Geometry Primitives: Line
 *
 * Object: Cable, overhead
 *
 * Acronym: CBLOHD
 */
class Cblohd : Layerable() {
    override fun layers(options: LayerableOptions) = sequenceOf(
        Layer(
            id = "${key}_line",
            type = LayerType.LINE,
            sourceLayer = key,
            filter = Filters.eqTypeLineStringOrPolygon,
            paint = Paint(
                lineColor = colorFrom("CHBLK"),
                lineWidth = 0.5f,
                lineDashArray = listOf(10f, 5f)
            ),
        )
    )
}
