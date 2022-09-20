package fr.fyz.lpb.commands.list;

import java.time.Instant;

import fr.fyz.lpb.commands.ITCommand;
import fr.fyz.lpb.enums.RAINBOW;
import fr.fyz.lpb.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandForceSkip implements ITCommand{

	@Override
	public String getName() {
		return "forceskip";
	}

	@Override
	public String getDescription() {
		return "Permet de forceskip la musique actuelle";
	}

	@Override
	public OptionData[] getOptions() {
		return null;
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {
			
			@Override
			public void onCommand(SlashCommandInteractionEvent event) {
				
				
				PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.nextTrack();
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(RAINBOW.YELLOW.getColor());
				eb.setTimestamp(Instant.now());
				eb.setDescription("La musique actuelle a �t� forceskip, lancement de la prochaine.");
				eb.setTitle("Musique suivante lanc�e");
				event.replyEmbeds(eb.build()).queue();
			}
		};
	}

	
	
}
