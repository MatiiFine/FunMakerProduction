package mb.funmaker.jda.command.commands.music;

import mb.funmaker.jda.command.CommandContext;
import mb.funmaker.jda.command.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if(selfVoiceState.inVoiceChannel()){
            channel.sendMessage("Aktualnie pracuję na innym kanale").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("Aby mnie przywołać musisz być na jakmiś kanale głosowym").queue();
            return;
        }

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        final VoiceChannel memberChanel = memberVoiceState.getChannel();

        audioManager.openAudioConnection(memberChanel);
        channel.sendMessageFormat("Dołączam do `\uD83D\uDD0A %s`", memberChanel.getName()).queue();
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Dodaje bota muzycznego na twój kanał głosowy";
    }
}
