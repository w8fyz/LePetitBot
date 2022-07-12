package fr.fyz.lpb.listeners;

import fr.fyz.lpb.Main;
import fr.fyz.lpb.enums.RAINBOW;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WelcomeListener extends ListenerAdapter {

	private static String WELCOME_CHANNEL_ID = "602536446544576534";
	private static String DEFAULT_ROLE = "602538125281067192";
	

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		
		event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(DEFAULT_ROLE)).queue();
		
		TextChannel bvn = Main.getJDA().getTextChannelById(WELCOME_CHANNEL_ID);

		RAINBOW color_set = RAINBOW.getNextEmote(RAINBOW.getLastEmote(bvn));

		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Bienvenue ! " + color_set.getEmoji());
		eb.setColor(color_set.getColor());
		eb.setDescription("Bienvenue à toi "+event.getMember().getAsMention()+" sur le serveur ! N'hésite pas à faire un tour dans le salon <#992855792174776480> et à te présenter dans <#602536716238323732> !");
		bvn.sendMessageEmbeds(eb.build()).queue();
	}

}
