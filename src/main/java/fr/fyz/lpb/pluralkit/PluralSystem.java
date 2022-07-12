package fr.fyz.lpb.pluralkit;

import java.util.ArrayList;

public class PluralSystem {

	private String system_name;
	private ArrayList<PluralMember> members;

	public PluralSystem(String system_name, ArrayList<PluralMember> members) {
		this.system_name = system_name;
		this.members = members;
	}

	public String getSystemName() {
		return system_name;
	}

	public PluralMember getMemberByPrefix(String prefix) {
		for (PluralMember member : members) {
			for (String pr : member.getPrefix()) {
				if (pr.equalsIgnoreCase(prefix)) {
					return member;
				}
			}
		}
		return null;
	}

	public PluralMember getMemberByName(String name) {
		return members.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findAny().orElse(null);
	}

	public ArrayList<PluralMember> getMembers() {
		return members;
	}

	public void setSystemName(String name) { 
		this.system_name = name;
	}

}
