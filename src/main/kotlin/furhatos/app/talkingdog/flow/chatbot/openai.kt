package furhatos.app.talkingdog.flow.chatbot

import com.theokanning.openai.OpenAiService
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import furhatos.flow.kotlin.DialogHistory
import furhatos.flow.kotlin.Furhat

/** API Key to GPT3 language model. Get access to the API and generate your key from: https://openai.com/api/ **/
val serviceKey = "sk-proj-3QlW4MC2T5LUrj5Y9EqIT3BlbkFJt5S0Xf5zly3DwXyJL5qC"

val service = OpenAiService(serviceKey)

class OpenAIChatbot(val description: String, val userName: String, val agentName: String) {

    val service = OpenAiService(serviceKey)

    var temperature = 0.9
    var maxTokens = 200
    var topP = 1.0
    var frequencyPenalty = 0.0
    var presencePenalty = 0.6

    fun getNextResponse(): String {
        val history = Furhat.dialogHistory.all.takeLast(10).mapNotNull {
            when (it) {
                is DialogHistory.ResponseItem -> ChatMessage("user", it.response.text)
                is DialogHistory.UtteranceItem -> ChatMessage("assistant", it.toText())
                else -> null
            }
        }

        // Add system message to set the context or description
        val systemMessage = ChatMessage("system", description)

        val messages = mutableListOf(systemMessage).apply { addAll(history) }

        val chatCompletionRequest = ChatCompletionRequest.builder()
            .model("gpt-4o")
            .messages(messages)
            .maxTokens(maxTokens)
            .temperature(temperature)
            .topP(topP)
            .frequencyPenalty(frequencyPenalty)
            .presencePenalty(presencePenalty)
            .build()

        return try {
            val response = service.createChatCompletion(chatCompletionRequest)
            val reply = response.choices.first().message.content.trim()
            reply
        } catch (e: Exception) {
            println("Problem with connection to OpenAI: ${e.message}")
            "I am not sure what to say"
        }
    }
}
