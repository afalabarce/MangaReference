package dev.afalabarce.mangaref.models.features.pagination

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingDataLinks(
    @SerialName("first") val first: String?,
    @SerialName("previous") val previous: String?,
    @SerialName("next") val next: String?,
    @SerialName("last") val last: String?,
)
