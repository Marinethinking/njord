package io.madrona.njord.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.madrona.njord.ChartsConfig
import io.madrona.njord.Singletons
import io.madrona.njord.ext.KtorHandler
import io.madrona.njord.ext.fromString
import io.madrona.njord.layers.LayerFactory
import io.madrona.njord.layers.LayerableOptions
import io.madrona.njord.layers.Theme
import io.madrona.njord.model.Depth
import io.madrona.njord.model.Source
import io.madrona.njord.model.SourceType
import io.madrona.njord.model.Style

class StyleHandler(
    private val config: ChartsConfig = Singletons.config,
    private val layerFactory: LayerFactory = Singletons.layerFactory
) : KtorHandler {
    override val route = "/v1/style/{depth}/{theme}"

    override suspend fun handleGet(call: ApplicationCall) {
        fromString<Depth>(call.parameters["depth"])?.let { depth ->
            fromString<Theme>(call.parameters["theme"])?.let { theme ->
                val name = "${theme.name.lowercase()}_simplified"
                call.respond(
                    Style(
                        name = name,
                        glyphsUrl = "${config.externalBaseUrl}/v1/content/fonts/{fontstack}/{range}.pbf",
                        spriteUrl = "${config.externalBaseUrl}/v1/content/sprites/$name",
                        sources = mapOf(
                            Source.SENC to Source(
                                type = SourceType.VECTOR,
                                tileJsonUrl = "${config.externalBaseUrl}/v1/tile_json"
                            )
                        ),
                        layers = layerFactory.layers(LayerableOptions(depth, theme)),
                        version = 8
                    )
                )
            }
        } ?: call.respond(HttpStatusCode.NotFound, Any())
    }
}
