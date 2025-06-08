package dev.afalabarce.mangaref.domain.usecases.features.example

import dev.afalabarce.mangaref.domain.models.ExampleEntity
import dev.afalabarce.mangaref.domain.repository.features.example.EntityRepository
import kotlinx.coroutines.flow.Flow

class GetEntitiesUseCase(private val repository: EntityRepository) {
    operator fun invoke(): Flow<List<ExampleEntity>> = repository.getEntities()
}