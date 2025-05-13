package com.akradev.bloggen.controller

import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class ChatRequest(val prompt: String)
data class ChatReply(val reply: String)

@RestController
@RequestMapping("/api/chat")
class ChatController(
    private val chatModel: OllamaChatModel,
) {

    @PostMapping
    fun chat(@RequestBody request: ChatRequest): Any {
        val userMessage = UserMessage(request.prompt)
        val prompt = Prompt(userMessage)
        val temp = chatModel.call(prompt).results.map { response ->
            response.output
        }
        return temp
    }
}