package dev.afalabarce.mangaref.models.features.pagination

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingMetaInfo(
    @SerialName("totalItems") val totalItems: Long,
    @SerialName("itemCount") val itemCount: Long,
    @SerialName("itemsPerPage") val itemsPerPage: Long,
    @SerialName("totalPages") val totalPages: Long,
    @SerialName("currentPage") val currentPage: Long,
)
