package fr.fyz.lpb;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;

import javax.security.auth.login.LoginException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import fr.fyz.lpb.listeners.CommandsListener;
import fr.fyz.lpb.listeners.CounterListener;
import fr.fyz.lpb.listeners.CustomVoiceListener;
import fr.fyz.lpb.listeners.LogListener;
import fr.fyz.lpb.listeners.ReadyListener;
import fr.fyz.lpb.listeners.RolesListener;
import fr.fyz.lpb.listeners.WelcomeListener;
import fr.fyz.lpb.spotify.auth.AuthCodeURI;
import fr.fyz.lpb.spotify.auth.WebRequestHandler;
import fr.fyz.lpb.utils.Credentials;
import fr.fyz.lpb.utils.FileResourcesUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import se.michaelthelin.spotify.SpotifyApi;

public class Main {

	private static JDA jda;

	private static Credentials credentials;

	private static Server server = null;
	public static String IP_ADRESS = "localhost";

	public static JDA getJDA() {
		return jda;
	}

	private static SpotifyApi spotify;

	public static SpotifyApi getSpotify() {
		return spotify;
	}

	@SuppressWarnings("serial")
	public static void main(String[] args) throws LoginException, IOException, URISyntaxException {
		credentials = new Gson().fromJson(Files
				.asCharSource(new FileResourcesUtils().getFileFromResource("credentials.yml"), Charsets.UTF_8).read(),
				new TypeToken<Credentials>() {
				}.getType());

		new Thread(new Runnable() {
			@Override
			public void run() {
				startSpotifyWebApp(args);
			}
		}).start();

		jda = JDABuilder.createDefault(credentials.discordToken)
				.addEventListeners(/* new PluralListener(), */new CounterListener(), new RolesListener(),
						new ReadyListener(), new CommandsListener(), new CustomVoiceListener(), new WelcomeListener(),
						new LogListener())
				.disableCache(CacheFlag.ACTIVITY)
				.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
				.setMemberCachePolicy(MemberCachePolicy.ALL).enableCache(CacheFlag.ACTIVITY).build();

	}

	public static void log(String msg) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println("[" + timestamp.toString() + "] " + msg);
	}

	private static void startSpotifyWebApp(String[] args) {
		try {
			if (args.length == 1 && args[0].equals("nogui")) {
				log("Successfully started in server-only mode.");
				log("Getting ip adress... ");
				System.out.println("");
				URL whatismyip = new URL("http://checkip.amazonaws.com");
				BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
				IP_ADRESS = in.readLine();
				log(IP_ADRESS);
				spotify = new SpotifyApi.Builder().setClientId(credentials.spotify)
						.setRedirectUri(URI.create("http://" + Main.IP_ADRESS + ":9090/connect"))
						.setClientSecret(credentials.spotifySecret).build();
				AuthCodeURI uri = new AuthCodeURI();

				log("URL : " + uri.get());
				log("[WARNING] IF YOU WANT TO BE IN SERVER-ONLY MODE THE IP ADRESS OF THE SERVER NEED TO BE WHITELISTED ON THE SPOTIFY DEV DASHBOARD.");

			} else {
				spotify = new SpotifyApi.Builder().setClientId(credentials.spotify)
						.setRedirectUri(URI.create("http://" + Main.IP_ADRESS + ":9090/connect"))
						.setClientSecret(credentials.spotifySecret).build();
				AuthCodeURI uri = new AuthCodeURI();
				Desktop.getDesktop().browse(uri.get());
				System.out.println(
						"TIPS : You can start the program without this popup on the server side with the \"nogui\" argument");
			}
			initWebServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initWebServer() {
		server = new Server(9090);
		log("Listening on port 9090... (" + IP_ADRESS + ":9090)");
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(WebRequestHandler.class, "/connect");
		server.setHandler(handler);
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
