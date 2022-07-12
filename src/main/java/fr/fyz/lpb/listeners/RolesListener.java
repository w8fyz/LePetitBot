package fr.fyz.lpb.listeners;

import java.awt.Color;
import java.util.List;

import fr.fyz.lpb.Main;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

public class RolesListener extends ListenerAdapter {

	public static List<Role> getPronounsRole() {
		return Main.getJDA().getGuildById("505739114600202250").getRoles().stream()
				.filter(r -> r.getColor() != null && r.getColor().equals(Color.decode("#71368A"))).toList();
	}

	public static List<Role> getInterestsRole() {
		return Main.getJDA().getGuildById("505739114600202250").getRoles().stream()
				.filter(r -> r.getColor() != null && r.getColor().equals(Color.decode("#2ECC71"))).toList();
	}

	@Override
	public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
		if (event.getSelectMenu().getId().equals("pronouns")) {
			getPronounsRole().forEach(r -> {
				if (event.getMember().getRoles().contains(r)) {
					event.getGuild().removeRoleFromMember(event.getMember(), r).complete();
				}
			});

			for (SelectOption option : event.getSelectedOptions()) {
				event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(option.getValue()))
						.queue();
			}
			event.reply("Tes pronoms ont été mis à jour ! ✨").setEphemeral(true).queue();
		} else if (event.getSelectMenu().getId().equals("interests")) {
			getInterestsRole().forEach(r -> {
				if (event.getMember().getRoles().contains(r)) {
					event.getGuild().removeRoleFromMember(event.getMember(), r).complete();
				}
			});

			for (SelectOption option : event.getSelectedOptions()) {
				event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(option.getValue()))
						.queue();
			}
			event.reply("Tes intérêts ont été mis à jour ! 👌").setEphemeral(true).queue();
		}
	}

}
