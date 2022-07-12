package fr.fyz.lpb.task;

import java.util.TimerTask;

import fr.fyz.lpb.Main;
import net.dv8tion.jda.api.entities.VoiceChannel;


public class StatsTask extends TimerTask {

	private int result = 0;
	private static String VC_COUNT = "991493385187766273";
	private static String VC_COUNT_NAME = "🍎┃En vocal : ";
	
	private static String MEMBERS_COUNT = "991493350433763368";
	private static String MEMBERS_COUNT_NAME = "🍉┃Membres : ";
	
	public void run() {
		System.out.println("Started Stats Tasks");
		VoiceChannel vc_count = Main.getJDA().getVoiceChannelById(VC_COUNT);
		vc_count.getManager().setName(VC_COUNT_NAME+getInVC(vc_count)).queue();
		
		VoiceChannel members_count = Main.getJDA().getVoiceChannelById(MEMBERS_COUNT);
		members_count.getManager().setName(MEMBERS_COUNT_NAME+members_count.getGuild().getMemberCount()).queue();
	}
	
	private int getInVC(VoiceChannel vc) {
		result = 0;
		vc.getGuild().getVoiceChannels().forEach(i -> result+=i.getMembers().size());
		return result;
	}

}