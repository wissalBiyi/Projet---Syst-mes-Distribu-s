package net.biyi.ebankbot.discord;

import com.zgamelogic.discord.annotations.DiscordController; // Vérifiez le bon package selon votre IDE
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.biyi.ebankbot.agents.EbankAIAgent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.ai.chat.prompt.Prompt; // Nécessaire pour créer le Prompt

@DiscordController
public class DiscordBot {

    private final EbankAIAgent ebankAiAgent;

    public DiscordBot(EbankAIAgent ebankAiAgent) {
        this.ebankAiAgent = ebankAiAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String query = event.getMessage().getContentRaw();

        // On enveloppe le texte dans un objet Prompt pour correspondre à la signature de l'agent
        String response = ebankAiAgent.chat(new Prompt(query));

        event.getChannel().sendMessage(response).queue();
    }
}