package mb.funmaker.jda;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

    private Bot() throws LoginException {
        JDABuilder
                .createDefault(Config.get("TOKEN"))
                .addEventListeners(new Listener())
                .setActivity(Activity.playing("Ekwador Manieczki"))
                .build();
    }
    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
