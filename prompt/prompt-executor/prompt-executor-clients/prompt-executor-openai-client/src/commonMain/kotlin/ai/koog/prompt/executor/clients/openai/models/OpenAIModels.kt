package ai.koog.prompt.executor.clients.openai.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OpenAIModelsResponse(
    val data: List<OpenAIModel> = emptyList(),
    @SerialName("object")
    val objectType: String? = null,
)

@Serializable
internal data class OpenAIModel(
    val id: String,
    @SerialName("object")
    val objectType: String? = null,
    val created: Long? = null,
    @SerialName("owned_by")
    val ownedBy: String? = null,
)
