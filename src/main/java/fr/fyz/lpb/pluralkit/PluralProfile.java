package fr.fyz.lpb.pluralkit;

import java.util.ArrayList;
import java.util.Iterator;

public class PluralProfile {

	private ArrayList<PluralSystem> systems;
	private PluralSystem selected;
	
	public PluralProfile(ArrayList<PluralSystem> systems, PluralSystem selected) {
		this.systems = systems;
		this.selected = selected;
	}
	
	public PluralSystem getSelected() {
		return selected;
	}
	
	public void setSelected(PluralSystem system) {
		selected = system;
	}
	
	public ArrayList<PluralSystem> getSystems() {
		return systems;
	}
	
	public PluralSystem getSystemByName(String name) {
		return systems.stream().filter(s -> s.getSystemName().equalsIgnoreCase(name)).findAny().orElse(null);
	}

	public void updateSystem(PluralSystem system) {
		Iterator<PluralSystem> ite = systems.iterator();
		while(ite.hasNext()) {
			PluralSystem s = ite.next();
			if(s.getSystemName().equals(system.getSystemName())) {
				ite.remove();
				return;
			}
		}
		systems.add(system);
		setSelected(system);
	}
	
	public void updateSystem(PluralSystem old, PluralSystem newSystem) {
		systems.remove(old);
		systems.add(newSystem);
		setSelected(newSystem);
	}
}
