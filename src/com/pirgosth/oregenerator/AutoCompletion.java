package com.pirgosth.oregenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class AutoCompletion implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if(args.length == 1) {
			final List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], Arrays.asList("disable", "enable", "list", "reload"), completions);
			return completions;
		}
		else if(args.length == 2) {
			final List<String> completions = new ArrayList<>();
			switch(args[0].toLowerCase()) {
				case "enable":
					ArrayList<String> worlds = Utility.getWorldNames();
					worlds.removeAll(Config.getActiveWorlds());
					StringUtil.copyPartialMatches(args[1], worlds, completions);
					break;
				case "disable":
					StringUtil.copyPartialMatches(args[1], Config.getActiveWorlds(), completions);
					break;
				default:
					break;
			}
			return completions;
		}
		return new ArrayList<String>();
	}

}
