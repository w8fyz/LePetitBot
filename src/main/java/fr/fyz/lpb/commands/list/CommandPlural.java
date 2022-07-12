package fr.fyz.lpb.commands.list;

import java.util.ArrayList;

import fr.fyz.lpb.commands.ITCommand;
import fr.fyz.lpb.pluralkit.PluralManager;
import fr.fyz.lpb.pluralkit.PluralMember;
import fr.fyz.lpb.pluralkit.PluralProfile;
import fr.fyz.lpb.pluralkit.PluralSystem;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandPlural implements ITCommand {

	@Override
	public String getName() {
		return "plural";
	}

	@Override
	public String getDescription() {
		return "Permet d'utiliser et de configurer le module de Pluralité";
	}

	@Override
	public OptionData[] getOptions() {
		return new OptionData[] { new OptionData(OptionType.STRING, "argument", "(system,member,format help)", true),
				new OptionData(OptionType.STRING, "command", "(create, name, delete, select, list)", true),
				new OptionData(OptionType.STRING, "name", "Nom du paramètre"),
				new OptionData(OptionType.STRING, "valeur", "Valeur utilisable dans certaines commandes") };
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {

			@Override
			public void onCommand(SlashCommandInteractionEvent event) {

				String interacting = event.getOption("argument").getAsString();

				if (interacting.equalsIgnoreCase("system") || interacting.equalsIgnoreCase("member")
						|| interacting.equalsIgnoreCase("format")) {

					PluralProfile pp = PluralManager.getProfile(event.getMember().getId());

					if (pp == null) {
						PluralManager.saveProfile(event.getMember().getId(),
								new PluralProfile(new ArrayList<>(), null));
					}

					switch (event.getOption("command").getAsString()) {
					case "create":
						if (interacting.equalsIgnoreCase("system")) {
							if (event.getOption("name") == null) {
								event.reply("Erreur : Le nom doit être défini").setEphemeral(true).queue();
								return;
							}
							String name = event.getOption("name").getAsString();
							if (pp.getSystemByName(name) == null) {
								PluralSystem system = new PluralSystem(name, new ArrayList<>());
								pp.getSystems().add(system);
								if (pp.getSelected() == null) {
									pp.setSelected(system);
								}
								PluralManager.saveProfile(event.getMember().getId(), pp);
								event.reply("Le système " + name + " a été créé !").setEphemeral(true).queue();
							} else {
								event.reply("Erreur : Le système " + name + " existe déjà").setEphemeral(true).queue();
							}
						} else if (interacting.equalsIgnoreCase("member")) {
							String name = event.getOption("name").getAsString();
							if (pp.getSelected() == null) {
								event.reply("Erreur : Aucun système n'est sélectionné").setEphemeral(true).queue();
								return;
							}
							PluralSystem system = pp.getSelected();
							if (pp.getSelected().getMemberByName(name) == null) {
								PluralMember member = new PluralMember(name, new ArrayList<>(),
										event.getUser().getAvatarUrl());
								system.getMembers().add(member);
								pp.updateSystem(system);
								PluralManager.saveProfile(event.getUser().getId(), pp);
								event.reply(
										"Le membre " + name + " a été créé sur le système " + system.getSystemName())
										.setEphemeral(true).queue();
							} else {
								event.reply("Erreur : Le membre " + name + " existe déjà").setEphemeral(true).queue();
							}
						} else {
							String prefix = event.getOption("valeur").getAsString();
							if (pp.getSelected() == null) {
								event.reply("Erreur : Aucun système n'est sélectionné").setEphemeral(true).queue();
								return;
							}
							PluralSystem system = pp.getSelected();
							
							if(event.getOption("valeur") == null) {
								event.reply("Erreur : La valeur n'est pas défini, elle doit correspondre au format à créer").setEphemeral(true).queue();
								return;
							}
							
							PluralMember member = system.getMemberByName(event.getOption("name").getAsString());
							if(member == null) {
								event.reply("Erreur : Le membre est invalide !").setEphemeral(true).queue();
								return;
							}
							if (!member.getPrefix().contains(prefix)) {
								if (system.getMemberByPrefix(prefix) == null) {
									if(prefix.contains("text")) {
										member.addPrefix(prefix);
										pp.updateSystem(system);
									} else {
										event.reply("Erreur : Le format doit obligatoirement contenir le mot \"text\", example : [text] prendra en compte les messages au format [message]").setEphemeral(true).queue();
									}
								} else {
									event.reply("Erreur : Le format est déjà utilisé sur un autre membre du système")
											.setEphemeral(true).queue();
								}
							} else {
								event.reply("Erreur : Le format est déjà dans la liste.").setEphemeral(true).queue();
							}

						}
						break;
					case "name":
						if (interacting.equalsIgnoreCase("system")) {
							if (pp.getSelected() == null) {
								event.reply("Erreur : Aucun système n'est sélectionné").setEphemeral(true).queue();
								return;
							}
							PluralSystem system = pp.getSelected();
							if (event.getOption("name") == null) {
								event.reply("Erreur : Le nom doit être défini").setEphemeral(true).queue();
								return;
							}
							String name = event.getOption("name").getAsString();
							if (pp.getSystemByName(name) != null) {
								event.reply("Erreur : Le nom de système est déjà pris").setEphemeral(true).queue();
								return;
							}
							system.setSystemName(name);
							pp.updateSystem(pp.getSelected(), system);
						} else {
							if (pp.getSelected() == null) {
								event.reply("Erreur : Aucun système n'est sélectionné").setEphemeral(true).queue();
								return;
							}
							PluralSystem system = pp.getSelected();
							if (event.getOption("name") == null) {
								event.reply("Erreur : Le nom doit être défini").setEphemeral(true).queue();
								return;
							}
							String name = event.getOption("name").getAsString();
							if (system.getMemberByName(name) != null) {
								event.reply("Erreur : Le nom est déjà pris dans le système").setEphemeral(true).queue();
								return;
							}
							if (event.getOption("valeur") == null) {
								event.reply("Erreur : Vous devez spécifier l'ancien nom du membre du système")
										.setEphemeral(true).queue();
								return;
							}
							if (system.getMemberByName(event.getOption("valeur").getAsString()) == null) {
								event.reply("Erreur : Le nom du membre est invalide").setEphemeral(true).queue();
								return;
							}
							system.getMemberByName(event.getOption("valeur").getAsString()).setName(name);
							pp.updateSystem(system);
						}

						break;
					case "delete":

						break;
					case "select":

						break;
					case "list":
						if (interacting.equalsIgnoreCase("system")) {

							String response = "Voici la liste des systèmes :\n";

							for (PluralSystem system : pp.getSystems()) {
								response += "- " + system.getSystemName() + "\n";
							}

							event.reply(response).setEphemeral(true).queue();
						} else {

							String response = "Voici la liste des membres du système sélectionné :\n";

							for (PluralMember member : pp.getSelected().getMembers()) {
								response += "- " + member.getName() + "\n";
							}

							event.reply(response).setEphemeral(true).queue();
						}
						break;
					default:
						event.reply("Erreur, commande introuvable ! /plural help pour plus d'informations")
								.setEphemeral(true).queue();
						break;
					}

				} else if (interacting.equalsIgnoreCase("help")) {

				} else {
					event.reply("Erreur, commande introuvable ! /plural help pour plus d'informations")
							.setEphemeral(true).queue();
				}
			}
		};
	}

}
