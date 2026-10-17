package net.biyi.ebankbot.controllers;

import org.springframework.http.MediaType;
import net.biyi.ebankbot.agents.EbankAIAgent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class EbankChatbotController {
    private final EbankAIAgent ebankAIAgent;

    public EbankChatbotController(EbankAIAgent ebankAIAgent){
        this.ebankAIAgent = ebankAIAgent;
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestParam(name="query", defaultValue = "Bonjour") String query){
        // Utilise chatGroq (au lieu de .chat(new Prompt(...)))
        return ebankAIAgent.chatGroq(query);
    }

    @GetMapping(value = "/chatStream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> chatStream(@RequestParam(name="query", defaultValue="Bonjour") String query){
        // Utilise chatStreamGroq (correction de l'orthographe par rapport à chatStreamQroq)
        return ebankAIAgent.chatStreamGroq(query);
    }

    @GetMapping(value = "/chatOpen", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chatOpen(@RequestParam(name="query", defaultValue="Bonjour") String query){
        return ebankAIAgent.chatOpenai(query);
    }

    @GetMapping(value = "/chatStreamOpen", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> chatStreamOpen(@RequestParam(name="query", defaultValue="Bonjour") String query){
        return ebankAIAgent.chatStreamOpenai(query);
    }
}