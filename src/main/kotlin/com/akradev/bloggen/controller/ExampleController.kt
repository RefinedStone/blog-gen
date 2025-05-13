package com.akradev.bloggen.controller

import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
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
    @Autowired private val env: Environment,
) {

    @PostMapping("/v1")
    fun chatV1(@RequestBody request: ChatRequest): Any {
        val userMessage = UserMessage(request.prompt)
        val prompt = Prompt(userMessage)
        val temp = chatModel.call(prompt).results.map { response ->
            response.output
        }
        return temp
    }

    @PostMapping("/v2")
    fun chatV2(@RequestBody request: ChatRequest): Any {
        val instruction = env.getProperty("instruction.v1") ?: ""
        val userMessage = UserMessage(request.prompt + SystemMessage(instruction))
        val prompt = Prompt(listOf(SystemMessage(instruction), userMessage))

        val response = chatModel.call(prompt)
        val reply = response.results.joinToString("\n") { it.output.toString() }

        return ChatReply(reply)
    }
}