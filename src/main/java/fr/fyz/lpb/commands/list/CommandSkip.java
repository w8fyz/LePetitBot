package fr.fyz.lpb.commands.list;

import java.time.Instant;

import fr.fyz.lpb.commands.ITCommand;
import fr.fyz.lpb.enums.RAINBOW;
import fr.fyz.lpb.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandSkip implements ITCommand{

	@Override
	public String getName() {
		return "skip";
	}

	@Override
	public String getDescription() {
		return "Permet de skip la musique actuelle";
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
				
				if(!event.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.RED.getColor());
					eb.setTimestamp(Instant.now());
					eb.setDescription("Erreur.");
					eb.setTitle("Tu n'as pas la permission de faire ça !");
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
					return;
				}
				
				PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.nextTrack();
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(RAINBOW.YELLOW.getColor());
				eb.setTimestamp(Instant.now());
				eb.setDescription("La musique actuelle a été skip, lancement de la prochaine.");
				eb.setTitle("Musique suivante lancée");
				event.replyEmbeds(eb.build()).setEphemeral(true).queue();
			}
		};
	}

	
	
}
