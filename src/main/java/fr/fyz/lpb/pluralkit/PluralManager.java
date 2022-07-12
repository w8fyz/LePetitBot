package fr.fyz.lpb.pluralkit;

import com.google.gson.Gson;

import fr.fyz.lpb.utils.data.Data;

public class PluralManager {

	public static PluralProfile getProfile(String discordID) {
		return (PluralProfile) new Data(PluralProfile.class).get(discordID);
	}
	
	public static void saveProfile(String discordID, PluralProfile profile) {
		new Data(PluralProfile.class).send(discordID, new Gson().toJson(profile));
	}
	
}
