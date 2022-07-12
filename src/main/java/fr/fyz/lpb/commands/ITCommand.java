package fr.fyz.lpb.commands;

import fr.fyz.lpb.commands.list.CommandRunnable;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public interface ITCommand {

	String getName();
	
	String getDescription();
	
	OptionData[] getOptions();
	
	CommandRunnable getRunnable();
	
}
