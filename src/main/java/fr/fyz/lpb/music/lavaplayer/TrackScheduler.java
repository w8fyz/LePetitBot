package fr.fyz.lpb.music.lavaplayer;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import fr.fyz.lpb.Main;
import fr.fyz.lpb.commands.list.CommandSong;
import fr.fyz.lpb.enums.RAINBOW;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class TrackScheduler extends AudioEventAdapter {

	public final AudioPlayer audioPlayer;
	public final BlockingQueue<AudioTrack> queue;
	
	public TrackScheduler(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
		this.queue = new LinkedBlockingQueue<>();
	}
	
	public void queue(AudioTrack track) {
		if(!this.audioPlayer.startTrack(track,  true)) {
			this.queue.offer(track);
		}
	}
	
	public void nextTrack() {
		this.audioPlayer.startTrack(this.queue.poll(), false);
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if(endReason.mayStartNext) {
			nextTrack();
			if(queue.size() == 0) {
				Guild guild = Main.getJDA().getGuildById("505739114600202250");
				guild.getAudioManager().closeAudioConnection();
			}
		}
	}
	
	@Override
	  public void onTrackStart(AudioPlayer player, AudioTrack track) {
		TextChannel txt = Main.getJDA().getTextChannelById("602672102205554688");
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(RAINBOW.GREEN.getColor());
		eb.setTimestamp(Instant.now());
		eb.setThumbnail("https://img.youtube.com/vi/" + track.getInfo().uri.split("=")[1] + "/0.jpg");
		eb.setAuthor("Lancement de la musique");
		eb.setTitle(track.getInfo().title, track.getInfo().uri);
		eb.addField("Auteur", track.getInfo().author, true);
		eb.addField("Durée", CommandSong.getFormatedDuration(track.getDuration()), true);
		
		txt.sendMessageEmbeds(eb.build()).queue();
	}
	
}
