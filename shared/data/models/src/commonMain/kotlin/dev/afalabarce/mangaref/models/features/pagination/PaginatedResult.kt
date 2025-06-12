package dev.afalabarce.mangaref.models.features.pagination

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResult<T>(
    @SerialName("meta") val meta: PagingMetaInfo,
    @SerialName("links") val links: PagingDataLinks,
    @SerialName("items") val items: List<T>,
)
