package io.madrona.njord.layers

import io.madrona.njord.layers.attributehelpers.Catsil
import io.madrona.njord.layers.attributehelpers.Catsil.Companion.catsil
import io.madrona.njord.layers.attributehelpers.Convis
import io.madrona.njord.layers.attributehelpers.Convis.Companion.convis
import io.madrona.njord.model.*
import java.util.Collections

/**
 * Geometry Primitives: Point, Area
 *
 * Object: Silo / tank
 *
 * Acronym: SILTNK
 *
 * Code: 125
 */
class Siltnk : Layerable() {
    override fun preTileEncode(feature: ChartFeature) {
        val convis = feature.convis()
        when (feature.catsil()) {
            Catsil.SILO_IN_GENERAL -> {
                when (convis) {
                    Convis.VISUAL_CONSPICUOUS -> feature.pointSymbol(Sprite.SILBUI11)
                    Convis.NOT_VISUAL_CONSPICUOUS,
                    null -> feature.pointSymbol(Sprite.SILBUI01)
                }
            }

            Catsil.TANK_IN_GENERAL -> {
                when (convis) {
                    Convis.VISUAL_CONSPICUOUS -> feature.pointSymbol(Sprite.TNKCON12)
                    Convis.NOT_VISUAL_CONSPICUOUS,
                    null -> feature.pointSymbol(Sprite.TNKCON02)
                }
            }

            Catsil.GRAIN_ELEVATOR -> {
                when (convis) {
                    Convis.VISUAL_CONSPICUOUS -> feature.pointSymbol(Sprite.TOWERS03)
                    Convis.NOT_VISUAL_CONSPICUOUS,
                    null -> feature.pointSymbol(Sprite.TOWERS01)
                }
            }

            Catsil.WATER_TOWER -> {
                when (convis) {
                    Convis.VISUAL_CONSPICUOUS -> feature.pointSymbol(Sprite.TOWERS02)
                    Convis.NOT_VISUAL_CONSPICUOUS,
                    null -> feature.pointSymbol(Sprite.TOWERS12)
                }
            }

            null -> feature.pointSymbol(Sprite.SILBUI01)
        }
        when (convis) {
            Convis.VISUAL_CONSPICUOUS -> feature.areaColor(Color.LANDF)
            Convis.NOT_VISUAL_CONSPICUOUS,
            null -> Unit
        }
    }

    override fun layers(options: LayerableOptions) = sequenceOf(
        Layer(
            id = "${key}_fill",
            type = LayerType.FILL,
            sourceLayer = key,
            filter = Filters.eqTypePolyGon,
            paint = Paint(
                fillColor = Filters.areaFillColor(options = Collections.singleton(Color.LANDF), theme = options.theme)
            ),
        ),
        Layer(
            id = "${key}_line",
            type = LayerType.LINE,
            sourceLayer = key,
            filter = Filters.eqTypeLineStringOrPolygon,
            paint = Paint(
                lineColor = colorFrom(Color.CHBLK, options.theme),
                lineWidth = 2f,
            ),
        ),
        Layer(
            id = "${key}_point",
            type = LayerType.SYMBOL,
            sourceLayer = key,
            filter = listOf(Filters.any, Filters.eqTypePoint),
            layout = Layout(
                symbolPlacement = Placement.POINT,
                iconImage = listOf("get", "SY"),
                iconAnchor = Anchor.BOTTOM,
                iconAllowOverlap = true,
                iconKeepUpright = false,
            )
        ),
    )
}
