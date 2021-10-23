package mb.funmaker.jda.command.commands.music.lavaplayer;

import mb.funmaker.jda.command.CommandContext;
import mb.funmaker.jda.command.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
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

        PlayerManager.getInstance()
                .loadAndPlay(channel, "https://www.youtube.com/watch?v=iS0EqNnr7pM");
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