package mb.funmaker.jda;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;ddEventListeners(new Listener()

import javax.security.auth.login.LoginException;

public class Bot {

    private Bot() throws LoginException {
        JDABuilder
                .createDefault(Config.get("TOKEN"))
                .a)
                .setActivity(Activity.watching("PornHub.com"))
                .build();
    }
    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
