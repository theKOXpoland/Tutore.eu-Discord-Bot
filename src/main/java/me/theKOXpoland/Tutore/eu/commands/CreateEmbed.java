package me.theKOXpoland.Tutore.eu.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CreateEmbed extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        final String[] message = event.getMessage().getContentRaw().split(" ");
        if (event.getMember() != null) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!message[0].equalsIgnoreCase("%ce")) {
                    return;
                }
                if (message.length < 2) {
                    Message completedMessage = event.getChannel().sendMessage("Aby poprawnie wyslać komende, użyj: \n`%ce r:g:b wiadomość`").complete();
                    event.getMessage().delete().queue();
                    completedMessage.delete().queueAfter(5, TimeUnit.SECONDS);
                    return;
                }

                String stripped = message[1];
                String[] splitted = stripped.split(":");

                String r = splitted[0];
                String g = splitted[1];
                String b = splitted[2];

                Integer red, green, blue;
                try {
                    red = Integer.parseInt(r);
                    green = Integer.parseInt(g);
                    blue = Integer.parseInt(b);
                } catch (NumberFormatException ex)  {
                    Message completedMessage = event.getChannel().sendMessage("Formatowanie koloru niepoprawne! \n Musisz użyć formatu RGB w postaci `red:green:blue` \n `%ce r:g:b wiadomość` \n  ```Przykład: \n \n %ce 255:0:0 czerwony!``` ").complete();
                    event.getMessage().delete().queue();
                    completedMessage.delete().queueAfter(8, TimeUnit.SECONDS);
                    return;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < message.length; i++) {
                    sb.append(message[i]);
                    sb.append(" ");
                }
                String tekst = sb.toString();

                EmbedBuilder embed = new EmbedBuilder();
                embed.setDescription(tekst);
                embed.setColor(new Color(red, green, blue).getRGB());
                event.getChannel().sendMessage(embed.build()).queue();
                event.getMessage().delete().queue();
            }
        }
    }
}