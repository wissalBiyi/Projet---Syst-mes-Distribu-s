package net.biyi.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class EbankAIAgent {
    private final ChatClient chatClient;

    public EbankAIAgent(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        Vous êtes un assistant qui se charge de répondre aux questions de l'utilisateur à propos des clients et des comptes bancaires, en fonction du contexte fourni.
                        Si aucun contexte n'est fourni, répondez avec JE NE SAIS PAS
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(tools)
                .build();
    }

    public String chatGroq(String query){
        return chatClient.prompt(query)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "7"))
                .call().content();
    }

    public String chatOpenai(String query){
        return chatClient.prompt(query)
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID, "conversation-1"
                ))
                .call().content();
    }

    public Flux<String> chatStreamGroq(String query){
        return chatClient.prompt(query)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "7"))
                .stream().content();
    }

    public Flux<String> chatStreamOpenai(String query){
        return chatClient.prompt(query)
                .stream().content();
    }
}