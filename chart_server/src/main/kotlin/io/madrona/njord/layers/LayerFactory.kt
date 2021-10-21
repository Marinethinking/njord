package io.madrona.njord.layers

class LayerFactory(
        private val layerables: Sequence<Layerable> = sequenceOf(
                Background(),
                Seaare(),
                Depare(),
                Depcnt(),
                Slcons(),
                Ponton(),
                Pilpnt(),
                Hulkes(),
                Lndare(),
                Coalne(),
                Soundg(),
                Boyspp(),
                Lights(), //symbol lookup
                Ply(),
                Debug()
        )
) {

    fun layers(options: LayerableOptions) = layerables.map {
        it.layers(options)
    }.flatten().toList()
}