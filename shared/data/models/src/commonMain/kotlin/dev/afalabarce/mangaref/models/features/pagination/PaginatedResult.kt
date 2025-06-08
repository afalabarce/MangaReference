package dev.afalabarce.mangaref.models.features.pagination

data class PaginatedResult<T>(
    val meta: PagingMetaInfo,
    val links: PagingDataLinks,
    val items: List<T>,
)
