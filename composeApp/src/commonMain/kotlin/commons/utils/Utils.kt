package commons.utils

import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
}

internal inline fun <reified R : Any> String.convertJsonToDataClass() =
    json.decodeFromString<R>(this)