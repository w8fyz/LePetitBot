package fr.fyz.lpb.spotify.auth;

import java.net.URI;

import fr.fyz.lpb.Main;

public class AuthCodeURI {

	public URI get() {
		final URI uri = Main.getSpotify().authorizationCodeUri().scope("user-read-currently-playing")
				.redirect_uri(URI.create("http://" + Main.IP_ADRESS + ":9090/connect")).build().execute();
		return uri;
	}
}