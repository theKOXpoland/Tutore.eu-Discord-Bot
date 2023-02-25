package me.theKOXpoland.Tutore.eu;

import me.theKOXpoland.Tutore.eu.commands.CreateEmbed;
import me.theKOXpoland.Tutore.eu.commands.ImageEmbed;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class MainClass extends ListenerAdapter {

    private JDA jda;

    public static void main(String[] args) {
        new MainClass();
    }

    public MainClass() {
        try {
            jda = JDABuilder.createDefault("Bot Token", GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_PRESENCES).build();
        } catch (LoginException ex) {
            ex.printStackTrace();
        }

        if (jda != null) {
            jda.getPresence().setActivity(Activity.watching("Your activity"));
            configureMemoryUsage(JDABuilder.createDefault(String.valueOf(jda)));

            jda.addEventListener(new CreateEmbed());
            jda.addEventListener(new ImageEmbed());
        }
    }

    public void configureMemoryUsage(JDABuilder builder) {
        builder.disableCache(
                CacheFlag.MEMBER_OVERRIDES,
                CacheFlag.ACTIVITY,
                CacheFlag.CLIENT_STATUS
        );
        builder.enableIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_PRESENCES
        );
    }
}
