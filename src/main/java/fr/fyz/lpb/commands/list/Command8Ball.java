package fr.fyz.lpb.commands.list;

import java.awt.Color;
import java.util.Random;

import fr.fyz.lpb.commands.ITCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Command8Ball implements ITCommand {

	@Override
	public String getName() {
		return "magicball";
	}

	@Override
	public String getDescription() {
		return "Permet de prédire le futur en posant une question !";
	}

	@Override
	public OptionData[] getOptions() {
		return new OptionData[] { new OptionData(OptionType.STRING, "question",
				"La question qui va être soumise à la boule magique", true) };
	}
	
	private String get8BallResponse() {
		String[] responses = {"C'est plausible", "Oui, clairement !", "A première vue, non", "Cela m'étonnerait", "Non", "Oui", "Parfois", "Je ne sais pas", "Je pense que oui", "Certainement, oui", "Je pense que non", "Certainement pas !", "C'est possible", "Jamais de la vie"};
		return responses[new Random().nextInt(responses.length)];
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {

			@Override
			public void onCommand(SlashCommandInteractionEvent event) {
				String question = event.getOption("question").getAsString();

				EmbedBuilder eb = new EmbedBuilder();

				if (event.getChannel().getId().equals(CommandManager.getMagicBallChannel())) {

					if (question.contains("?")) {

						eb.setTitle("🔮 Boule Magique");
						eb.setColor(Color.decode("#4B0082"));
						eb.addField("A la question", question, false);
						eb.addField("La boule magique répond... ", get8BallResponse(), false);
						event.replyEmbeds(eb.build()).queue();
					} else {
						eb.setTitle("Erreur");
						eb.setDescription(
								"Il faut que ta question contienne un point d'interrogation, comment la boule de crystal pourrais la comprendre sinon ?");
						eb.setColor(Color.red);
						event.replyEmbeds(eb.build()).setEphemeral(true).queue();
					}

				} else {
					eb.setTitle("Erreur");
					eb.setDescription("Il faut utiliser le salon <#" + CommandManager.getMagicBallChannel()
							+ "> pour utiliser la boule magique !");
					eb.setColor(Color.red);
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
				}
			}
		};
	}

}
