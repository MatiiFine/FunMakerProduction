package mb.funmaker.jda.command.commands.music.lavaplayer;

import mb.funmaker.jda.command.CommandContext;
import mb.funmaker.jda.command.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.URI;
import java.net.URISyntaxException;

public class PlayCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        if(ctx.getArgs().isEmpty()){
            channel.sendMessage("Poprawne użycie to `!play <link>`").queue();
            return;
        }

        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inVoiceChannel()){
            channel.sendMessage("Muszę być na kanale aby coś zapodać").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        if(!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("Żeby dodać coś do kolejki musisz być na kanale głosowym").queue();
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("Musisz być na tym samym kanale głosowym").queue();
            return;
        }

        String link = String.join(" ", ctx.getArgs());

        if(!isUrl(link)){
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance().loadAndPlay(channel, link);
    }

    private boolean isUrl(String url){
        try {
            new URI(url);
            return true;
        }catch (URISyntaxException e){
            return false;
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "Puszcza muzykę \n" +
                "Np. `!play <link>`";
    }
}