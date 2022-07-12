package fr.fyz.lpb.task;

import java.util.Random;
import java.util.TimerTask;

import fr.fyz.lpb.Main;
import net.dv8tion.jda.api.entities.Activity;

public class MOTDTask extends TimerTask {

	private static String[] presences = {"vec FyzBot", "Hmmm Gazpacho.", "02/07/22 03:59:15!", "BLEP!", "Mange des cartes graphiques", "Essaye la commande /song dans le salon musique", "Essaye le /secret dans mes DM!", "Tourne en rond, tourne en rond, tourne en rond", "4C6120637572696F736974E92065737420756E65207175616C6974E9", "🔮🔮🔮", "It's dangerous to go alone, take this!", "Do a barrel roll!", "We... Are the Crystal Gems!", "Wap wap wap wap", "Réviser les lois de la robotique"};
	
	@Override
	public void run() {
		String motd = presences[new Random().nextInt(presences.length)];
		Main.log("MOTD Updated : "+motd);
		Main.getJDA().getPresence().setActivity(Activity.playing(motd));
	}

}
