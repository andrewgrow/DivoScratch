package divo.scratch.api.models

import kotlinx.serialization.Serializable

@Serializable
data class HealthDTO(
    val status: String? = null,
    val timestamp: Long? = null
)