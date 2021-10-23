package mb.funmaker.jda.command.commands.music.lavaplayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import mb.funmaker.jda.command.CommandContext;
import mb.funmaker.jda.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if(queue.isEmpty()){
            channel.sendMessage("Kolejka jest pusta").queue();
            return;
        }

        final int trackCount = Math.min(queue.size(), 10);
        final List<AudioTrack> trackList = new ArrayList<>(queue);
        final MessageAction messageAction = channel.sendMessage("**Aktualna kolejka:**\n");

        for(int i=0;i<trackCount;i++){
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();

            messageAction.append('#')
                    .append(String.valueOf(i+1))
                    .append(" `")
                    .append(info.title)
                    .append(" by ")
                    .append(info.author)
                    .append("` [")
                    .append(formatTime(track.getDuration()))
                    .append("`]\n");
        }

        if(trackList.size() > trackCount){
            messageAction.append("oraz `")
                    .append(String.valueOf(trackList.size() - trackCount))
                    .append("` więcej...");
        }
        messageAction.queue();
    }

    private String formatTime(long timeMillis){
        final long hours = timeMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeMillis % TimeUnit.MINUTES.toMillis(1)/ TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getHelp() {
        return "Pokazuje utwory dodane do kolejki";
    }
}
