package dev.afalabarce.mangaref.models.features.example.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteExampleEntity(
    @SerialName("id")
    val remoteId: Long,
    @SerialName("title")
    val remoteTitle: String,
    @SerialName("description")
    val remoteDescription: String
)
