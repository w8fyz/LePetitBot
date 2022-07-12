package fr.fyz.lpb.commands.list;

import java.awt.Color;
import java.time.Instant;

import fr.fyz.lpb.Main;
import fr.fyz.lpb.commands.ITCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandSecret implements ITCommand{

	@Override
	public String getName() {
		return "secret";
	}

	@Override
	public String getDescription() {
		return "Permet d'envoyer un message anonyme dans le salon secret anonymement ! (Les abus seront sanctionnés)";
	}

	@Override
	public OptionData[] getOptions() {
		return new OptionData[] {new OptionData(OptionType.STRING, "secret", "Le secret à envoyer", true)};
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {
			
			@Override
			public void onCommand(SlashCommandInteractionEvent event) {
				EmbedBuilder eb = new EmbedBuilder();
				if(event.getChannel().getType() == ChannelType.PRIVATE) {
					eb.setTitle("🤫 Un nouveau secret");
					eb.setDescription("*\""+event.getOption("secret").getAsString()+"*\"");
					eb.setTimestamp(Instant.now());
					eb.setColor(Color.YELLOW);
					Main.getJDA().getTextChannelById(CommandManager.getIDSecretChannel()).sendMessageEmbeds(eb.build()).queue();
					event.replyEmbeds(new EmbedBuilder().setTitle("Secret envoyé !").setColor(Color.yellow).setDescription("Le secret a bien été envoyé !").build()).queue();
				} else {
					eb.setTitle("Attention !");
					eb.setDescription("Il est impossible d'utiliser la commande secrète hors des messages privés du bot !");
					eb.setColor(Color.red);
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
				}
			}
		};
	}

}
