package fr.fyz.lpb.commands.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.fyz.lpb.Main;
import fr.fyz.lpb.commands.ITCommand;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

public class CommandManager {

	public static String getMagicBallChannel() {
		return "991495371991818290";
	}
	
	public static String getIDSecretChannel() {
		return "602669722668105729";
	}
	
	private static ArrayList<ITCommand> commands = new ArrayList<ITCommand>(Arrays.asList(new CommandHelp(), new CommandQueue(), new CommandClearQueue(), new CommandSkip(), new CommandNowPlaying(), new CommandStop(), new CommandPause(), new CommandPlay(), /*new CommandPlural(),*/new CommandSetup(), new CommandSong(), new CommandSecret(), new Command8Ball()));

	public static List<ITCommand> getCommands() {
		return commands;
	}

	private static void delete(Command command) {
		//command.delete().complete();
		//System.out.println("Command "+command.getName()+" deleted");
	}
	
	public static void init() {
		Main.getJDA().retrieveCommands().complete().forEach(cm -> delete(cm));
		for (ITCommand cmd : getCommands()) {
			Main.log("Created command : " + cmd.getName());
			CommandCreateAction action = Main.getJDA().upsertCommand(cmd.getName(), cmd.getDescription());
			if (cmd.getOptions() != null) {
				action.addOptions(cmd.getOptions());
			}
			action.queue();
		}
	}

	public static ITCommand getCommandByName(String name) {
		return commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(name)).findAny().orElse(null);
	}


}
