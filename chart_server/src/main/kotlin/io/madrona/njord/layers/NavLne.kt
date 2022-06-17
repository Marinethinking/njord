package io.madrona.njord.layers

import io.madrona.njord.model.*

/**
 * Navigation Line
 *
 * Geometric primitives: L
 * Set Attribute_A:	(!)CATNAV; DATEND; DATSTA; (!)ORIENT; PEREND; PERSTA; STATUS;
 * Set Attribute_B:	INFORM; NINFOM; NTXTDS; SCAMAX; SCAMIN; TXTDSC;
 * Set Attribute_C:	RECDAT; RECIND; SORDAT; SORIND;
 * Definition:
 * A navigation line is a straight line extending towards an area of navigational interest and generally generated by two navigational aids or one navigational aid and a bearing. (Service Hydrographique et Oc?anographique de la Marine, France)
 * References
 * INT 1:	IM 1-3;
 * S-4:	433-433.5;
 * Remarks:
 * The portion of a navigation line that a ship should use for navigation is known as a recommended track.
 * The extent of the navigation line depends on the visibility of the navigational aid(s).
 * The attribute 'orientation' (ORIENT) specifies the orientation of the navigation line measured from the water towards the navigational aid(s).
 * The recommended track is that portion of a 'navigation line' that a ship should use for navigation. (see here )
 * Distinction:
 * recommended route; recommended track;
 */
class NavLne : Layerable {
    override val key = "NAVLNE"
    override fun layers(options: LayerableOptions): Sequence<Layer> {
        return sequenceOf(
            Layer(
                id = "${key}_line",
                type = LayerType.LINE,
                sourceLayer = key,
                filter = listOf(
                    Filters.all,
                    Filters.eqTypeLineString
                ),
                paint = Paint(
                    lineColor = colorFrom("CHBLK"),
                    lineWidth = 0.5f,
                    lineDashArray = listOf(10f, 5f)
                )
            ),
            Layer(
                id = "${key}_label",
                type = LayerType.SYMBOL,
                sourceLayer = key,
                filter = listOf(
                    Filters.any,
                    Filters.eqTypeLineString
                ),
                layout = Layout(
                    textFont = listOf(Font.ROBOTO_BOLD),
                    textJustify = Anchor.CENTER,
                    textField = listOf("get", "INFORM"),
                    textSize = 14f,
                    symbolPlacement = Placement.LINE,
                ),
                paint = Paint(
                    textColor = colorFrom("CHBLK"),
                    textHaloColor = colorFrom("CHWHT"),
                    textHaloWidth = 2.5f
                )
            )
        )
    }
}