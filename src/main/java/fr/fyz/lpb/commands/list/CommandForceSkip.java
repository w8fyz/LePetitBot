package fr.fyz.lpb.commands.list;

import java.time.Instant;
import java.util.ArrayList;

import fr.fyz.lpb.commands.ITCommand;
import fr.fyz.lpb.enums.RAINBOW;
import fr.fyz.lpb.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandForceSkip implements ITCommand {

	private static ArrayList<Member> vote_skip = new ArrayList<>();

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

				if (PlayerManager.getInstance().getMusicManager(event.getGuild()).audioPlayer
						.getPlayingTrack() != null) {

					if (vote_skip.size() < Math.sqrt(
							event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().size()-1 / 2)) {
						if(vote_skip.contains(event.getMember())) {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setColor(RAINBOW.YELLOW.getColor());
							eb.setTimestamp(Instant.now());
							eb.setDescription("Ton vote a déjà été pris en compte, résultat actuel : " + vote_skip.size()
									+ "/" + Math.sqrt(event.getGuild().getSelfMember().getVoiceState().getChannel()
											.getMembers().size()-1 / 2));
							eb.setTitle("Erreur");
							event.replyEmbeds(eb.build()).setEphemeral(true).queue();
							return;
						}
						EmbedBuilder eb = new EmbedBuilder();
						eb.setColor(RAINBOW.YELLOW.getColor());
						eb.setTimestamp(Instant.now());
						eb.setDescription("Ajout d'une demande de skip de la musique actuelle : " + vote_skip.size()
								+ "/" + Math.sqrt(event.getGuild().getSelfMember().getVoiceState().getChannel()
										.getMembers().size()-1 / 2));
						eb.setTitle("Voteskip");
						event.replyEmbeds(eb.build()).setEphemeral(true).queue();
						vote_skip.add(event.getMember());
						return;
					}

					PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.nextTrack();

					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.YELLOW.getColor());
					eb.setTimestamp(Instant.now());
					eb.setDescription("La musique actuelle a été skip, lancement de la prochaine.");
					eb.setTitle("Musique suivante lancée");
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
				} else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.RED.getColor());
					eb.setTimestamp(Instant.now());
					eb.setDescription("Aucune musique n'est en lecture.");
					eb.setTitle("Erreur");
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
				}

			}
		};
	}

}
