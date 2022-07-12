package fr.fyz.lpb.spotify;

import java.util.TimerTask;

import fr.fyz.lpb.spotify.auth.AuthorizationCodeRefresh;

public class CheckTask extends TimerTask {

	private int i = 0;

	public void run() {
		if (i == 65) {
			AuthorizationCodeRefresh.authorizationCodeRefresh_Sync();
			i = 0;
		}
		i++;
	}

}
