package dev.afalabarce.mangaref.domain.repository.features.example

import dev.afalabarce.mangaref.domain.models.ExampleEntity
import kotlinx.coroutines.flow.Flow

interface EntityRepository {
    fun getEntities(): Flow<List<ExampleEntity>>
}