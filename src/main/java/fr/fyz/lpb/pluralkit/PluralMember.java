package fr.fyz.lpb.pluralkit;

import java.util.ArrayList;

public class PluralMember {

	private String name,avatar_url;
	private ArrayList<String> prefix;
	
	public PluralMember(String name, ArrayList<String> prefix, String avatar_url) {
		this.name = name;
		this.prefix = prefix;
		this.avatar_url = avatar_url;
	}
	
	public void addPrefix(String prefix) {
		this.prefix.add(prefix);
	}
	
	public void removePrefix(String prefix) {
		if(this.prefix.contains(prefix)) {
			this.prefix.remove(prefix);
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<String> getPrefix() {
		return prefix;
	}
	
	public String getAvatarURL() {
		return avatar_url;
	}
	
}
