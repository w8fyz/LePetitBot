package fr.fyz.lpb.listeners;

import java.sql.Date;
import java.time.Instant;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import fr.fyz.lpb.Main;
import fr.fyz.lpb.commands.list.CommandManager;
import fr.fyz.lpb.music.lavaplayer.MusicCheckerTask;
import fr.fyz.lpb.task.MOTDTask;
import fr.fyz.lpb.task.StatsTask;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {

	@Override
	public void onReady(ReadyEvent event) {
		Main.log("LPB Initialisé.");
		CommandManager.init();
		new Timer().schedule(new StatsTask(), Date.from(Instant.now()), TimeUnit.MINUTES.toMillis(5));
		new Timer().schedule(new MOTDTask(), Date.from(Instant.now()), TimeUnit.MINUTES.toMillis(2));
		new Timer().schedule(new MusicCheckerTask(), Date.from(Instant.now()), TimeUnit.MINUTES.toMillis(2));
	}

}
