package io.madrona.njord.layers

import io.madrona.njord.model.*
import io.madrona.njord.util.logger

abstract class Layerable(
    customKey: String? = null
) {
    val log = logger()
    abstract fun layers(options: LayerableOptions): Sequence<Layer>
    val key: String = customKey ?: javaClass.simpleName.uppercase()

    open fun preTileEncode(feature: ChartFeature) {
        log.warn("layer $key preTileEncode not handled")
    }

    private var pointLayerFromSymbolId = 0

    /**
     *
     */
    fun pointLayerFromSymbol(
        symbol: Sprite? = null,
        anchor: Anchor = Anchor.BOTTOM,
        iconOffset: List<Float>? = null,
        iconAllowOverlap: Boolean = true,
        iconKeepUpright: Boolean = false,
        iconRotate: Any? = null,
        iconRotationAlignment: IconRotationAlignment? = null,
    ): Layer {
        return Layer(
            id = "${key}_point_${++pointLayerFromSymbolId}",
            type = LayerType.SYMBOL,
            sourceLayer = key,
            filter = listOf(Filters.any, Filters.eqTypePoint),
            layout = Layout(
                symbolPlacement = Placement.POINT,
                iconImage = symbol ?: listOf("get", "SY"),
                iconAnchor = anchor,
                iconOffset = iconOffset,
                iconAllowOverlap = iconAllowOverlap,
                iconKeepUpright = iconKeepUpright,
                iconRotate = iconRotate,
                iconRotationAlignment = iconRotationAlignment,
            )
        )
    }

    private var lineLayerWithColorId = 0
    fun lineLayerWithColor(
        color: Color? = null,
        style: LineStyle = LineStyle.Solid,
        width: Float = 2f,
        filter: List<Any>? = null,
    ): Layer {
        return Layer(
            id = "${key}_line_${++lineLayerWithColorId}",
            type = LayerType.LINE,
            sourceLayer = key,
            filter = filter ?: Filters.eqTypeLineStringOrPolygon,
            paint = Paint(
                lineColor = color?.let { colorFrom(color.name) } ?: Filters.lineColor,
                lineWidth = width,
                lineDashArray = style.lineDashArray
            )
        )
    }

    private var lineLayerWithLabelId = 0
    fun lineLayerWithLabel(
        label: Label,
        labelColor: Color = Color.CHBLK,
        highlightColor: Color = Color.CHWHT,
    ) : Layer {
        return Layer(
            id = "${key}_label_${++lineLayerWithLabelId}",
            type = LayerType.SYMBOL,
            sourceLayer = key,
            filter = Filters.eqTypeLineStringOrPolygon,
            layout = Layout(
                textFont = listOf(Font.ROBOTO_BOLD),
                textJustify = Anchor.CENTER,
                textField = label.label,
                textSize = 14f,
                symbolPlacement = Placement.LINE,
            ),
            paint = Paint(
                textColor = colorFrom(labelColor.name),
                textHaloColor = colorFrom(highlightColor.name),
                textHaloWidth = 2.5f
            )
        )
    }

    private var lineLayerWithPatternId = 0
    fun lineLayerWithPattern(symbol: Sprite? = null): Layer {
        return Layer(
            id = "${key}_line_symbol_${++lineLayerWithPatternId}",
            type = LayerType.SYMBOL,
            sourceLayer = key,
            filter = Filters.eqTypeLineStringOrPolygon,
            layout = Layout(
                symbolPlacement = Placement.LINE,
                iconImage = symbol?.name ?: listOf("get", "LP"),
                iconAnchor = Anchor.CENTER,
                iconKeepUpright = false,
            )
        )
    }

    private var lineLayerWithPattern2Id = 0
    fun lineLayerWithPattern(
        symbol: Sprite? = null,
        iconRotate: Any? = null,
        iconSize: Float? = null,
        spacing: Float? = null,
        allowOverlap: Boolean,
    ): Layer {
        return Layer(
            id = "${key}_line_symbol_${++lineLayerWithPattern2Id}",
            type = LayerType.SYMBOL,
            sourceLayer = key,
            filter = Filters.eqTypeLineStringOrPolygon,
            layout = Layout(
                symbolPlacement = Placement.LINE,
                iconImage = symbol?.name ?: listOf("get", "LP"),
                iconRotate = iconRotate,
                iconRotationAlignment = IconRotationAlignment.MAP,
                iconAllowOverlap = allowOverlap,
                iconSize = iconSize,
                symbolSpacing = spacing,
            )
        )
    }

    private var lineLayerWithTextId = 0
    fun lineLayerWithText(textKey: String): Layer {
       return Layer(
            id = "${key}_label${++lineLayerWithTextId}",
            type = LayerType.SYMBOL,
            sourceLayer = key,
            filter = Filters.eqTypeLineString,
            layout = Layout(
                textFont = listOf(Font.ROBOTO_BOLD),
                textJustify = Anchor.CENTER,
                textField = listOf("get", textKey),
                textSize = 14f,
                symbolPlacement = Placement.LINE,
            ),
            paint = Paint(
                textColor = colorFrom("CHBLK"),
                textHaloColor = colorFrom("CHWHT"),
                textHaloWidth = 2.5f
            )
        )
    }

    private var areaLayerWithFillColorId = 0
    fun areaLayerWithFillColor(color: Color? = null): Layer {
        return Layer(
            id = "${key}_fill_${++areaLayerWithFillColorId}",
            type = LayerType.FILL,
            sourceLayer = key,
            filter = Filters.eqTypePolyGon,
            paint = Paint(
                fillColor = color?.let { colorFrom(it.name) } ?: Filters.areaFillColor
            ),
        )
    }

    private var areaLayerWithFillPatternId = 0
    fun areaLayerWithFillPattern(symbol: Sprite? = null): Layer {
        return Layer(
            id = "${key}_fill_pattern_${++areaLayerWithFillColorId}",
            type = LayerType.FILL,
            sourceLayer = key,
            filter = Filters.eqTypePolyGon,
            paint = Paint(
                fillPattern = symbol?.name ?: listOf("get", "AP")
            )
        )
    }

    private var areaLayerWithSingleSymbolId = 0
    fun areaLayerWithSingleSymbol(
        symbol: Sprite? = null,
        iconOffset: List<Float>? = null
    ): Layer {
        return Layer(
            id = "${key}_area_symbol${++areaLayerWithSingleSymbolId}",
            type = LayerType.SYMBOL,
            sourceLayer = key,
            filter = Filters.eqTypePolyGon,
            layout = Layout(
                symbolPlacement = Placement.POINT,
                iconImage = symbol ?: listOf("get", "SY"),
                iconOffset = iconOffset,
                iconAnchor = Anchor.CENTER,
                iconKeepUpright = false,
            )
        )
    }

    fun areaLayerWithPointSymbol(
        symbol: Sprite? = null,
        anchor: Anchor = Anchor.CENTER,
        iconRotate: Any? = null,
        iconRotationAlignment: IconRotationAlignment? = null,
        iconAllowOverlap: Boolean = true,
        iconOffset: List<Float>? = null,
    ): Layer {
        return Layer(
            id = "${key}_area_point",
            type = LayerType.SYMBOL,
            sourceLayer = key,
            filter = listOf(Filters.all, Filters.eqTypePolyGon, listOf("!=", "EA", true)),
            layout = Layout(
                symbolPlacement = Placement.POINT,
                iconImage = symbol ?: listOf("get", "SY"),
                iconAnchor = anchor,
                iconRotate = iconRotate,
                iconRotationAlignment = iconRotationAlignment,
                iconAllowOverlap = iconAllowOverlap,
                iconKeepUpright = false,
                iconOffset = iconOffset,
            )
        )
    }
}

data class LayerableOptions(
    val depth: Depth
)

fun ChartFeature.excludeAreaPointSymbol() {
    props["EA"] = true
}

fun ChartFeature.pointSymbol(symbol: Sprite) {
    props["SY"] = symbol.name
}

fun ChartFeature.areaPattern(pattern: Sprite) {
    props["AP"] = pattern.name
}

fun ChartFeature.areaColor(color: Color) {
    props["AC"] = color.name
}

fun ChartFeature.linePattern(pattern: Sprite) {
    props["LP"] = pattern.name
}

fun ChartFeature.lineColor(color: Color) {
    props["LC"] = color.name
}

abstract class LayerableTodo : Layerable() {
    override fun preTileEncode(feature: ChartFeature) {
        log.warn("layer $key preTileEncode not handled")
        feature.pointSymbol(Sprite.QUESMRK1)
        feature.areaPattern(Sprite.QUESMRK1)
        feature.linePattern(Sprite.QUESMRK1)
    }

    override fun layers(options: LayerableOptions) = sequenceOf(
        areaLayerWithFillColor(Color.RADLO),
        areaLayerWithFillPattern(),
        lineLayerWithColor(color = Color.CHBLK),
        lineLayerWithColor(color = Color.LITRD, LineStyle.DashLine),
        lineLayerWithPattern(),
        pointLayerFromSymbol(),
    ).also {
        log.warn("layer $key layers not handled")
    }
}
