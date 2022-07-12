package fr.fyz.lpb.commands.list;

import fr.fyz.lpb.Main;
import fr.fyz.lpb.commands.ITCommand;
import fr.fyz.lpb.enums.RAINBOW;
import fr.fyz.lpb.enums.Rules;
import fr.fyz.lpb.listeners.RolesListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildMessageChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu.Builder;

public class CommandSetup implements ITCommand {

	@Override
	public String getName() {
		return "setup";
	}

	@Override
	public String getDescription() {
		return "Permet le setup des channels utilisés";
	}

	@Override
	public OptionData[] getOptions() {
		return new OptionData[] { new OptionData(OptionType.STRING, "setup", "Le setup a lancer", true),
				new OptionData(OptionType.CHANNEL, "channel", "Le channel du setup", true) };
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {

			@Override
			public void onCommand(SlashCommandInteractionEvent event) {
				GuildMessageChannel txt = event.getOption("channel").getAsMessageChannel();
				String setup = event.getOption("setup").getAsString();
				event.reply("Lancement du setup...").setEphemeral(true).queue();
				switch (setup) {

				case "roles":

					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Séléction de rôles - Pronoms");
					eb.setImage("https://i.imgur.com/pY2itr8.png");
					eb.setDescription(
							"Vous pouvez sélectionner à l'aide de la barre de sélection vos pronoms préférés");
					eb.setColor(RAINBOW.ORANGE.getColor());

					Builder menu = SelectMenu.create("pronouns");
					menu.setMinValues(0);
					for (Role r : RolesListener.getPronounsRole()) {
						menu.addOption(r.getName(), r.getId());
					}
					menu.setMaxValues(menu.getOptions().size());
					txt.sendMessageEmbeds(eb.build()).setActionRow(menu.build()).queue();

					EmbedBuilder eb2 = new EmbedBuilder();
					eb2.setTitle("Séléction de rôles - Intérêts");
					eb2.setImage("https://i.imgur.com/SGSMCMl.png");
					eb2.setDescription(
							"Vous pouvez sélectionner à l'aide de la barre de sélection les activités qui vont intéresse");
					eb2.setColor(RAINBOW.ORANGE.getColor());

					Builder menu2 = SelectMenu.create("interests");
					menu2.setMinValues(0);
					for (Role r : RolesListener.getInterestsRole()) {
						menu2.addOption(r.getName(), r.getId());
					}
					menu2.setMaxValues(menu2.getOptions().size());
					txt.sendMessageEmbeds(eb2.build()).setActionRow(menu2.build()).queue();
					
					break;
				case "rules":
					EmbedBuilder eb3 = new EmbedBuilder();
					eb3.setTitle("Règles du Serveur");
					eb3.setDescription("Bienvenue sur le discord Le p'tit monde ! \nVoici les règles pour pouvoir profiter au mieux du serveur\n\n");
					for(Rules rule : Rules.values()) {
						eb3.addField(rule.geTitle(), rule.getDesc(), false);
					}
					eb3.setColor(RAINBOW.ORANGE.getColor());
					eb3.setImage("https://i.imgur.com/hZK6ABv.png");
					txt.sendMessageEmbeds(eb3.build()).queue();
					break;
				case "dmpromote":
					EmbedBuilder eb4 = new EmbedBuilder();
					eb4.setTitle("Coucou toi ! (Clique ici pour le discord)", "https://discord.gg/invite/FXS9zMm");
					eb4.setDescription("Salut ! Moi c'est le p'tit bot ! Je m'occupe du discord **Le P'tit Monde**.\nJe viens de voir que tu es dessus et je voulais te prévenir, la dernière version datant de 2019, le discord **a réouvert** ! N'hésite pas à venir faire un tour !\nMerci d'avance si tu le fais 💛");
					eb4.setThumbnail(Main.getJDA().getSelfUser().getEffectiveAvatarUrl());
					eb4.setColor(RAINBOW.YELLOW.getColor());
					for(Member member : event.getGuild().getMembers()) {
						try {
							System.out.println(member.getUser().getAsTag());
							member.getUser().openPrivateChannel().complete().sendMessageEmbeds(eb4.build()).queue();
						} catch (Exception e) {
							System.out.println("Exception : "+e.getMessage());
						}
					}
					break;
				case "annonceGithub":
					txt.sendMessage("C'est trop la classe, merci Fyz 😎").queue();
					break;
				default:
					event.getChannel().sendMessage("Erreur, setup introuvable !").queue();
					break;
				}
			}
		};
	}

}
