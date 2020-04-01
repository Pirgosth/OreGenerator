package com.pirgosth.oregenerator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands implements CommandExecutor{
	private JavaPlugin plugin;
	
	public Commands(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	private boolean reload(CommandSender sender, String[] args) {
		if(sender instanceof Player && !sender.hasPermission("og.reload")) {
			sender.sendMessage(ChatColor.RED + "Missing permission: og.reload");
			return true;
		}
		if(args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Too many arguments !");
			return true;
		}
		Config.reload(plugin);
		sender.sendMessage(ChatColor.DARK_GREEN + "[OreGenerator]: Reloaded successfully !");
		return true;
	}
	
	private boolean toggleWorld(CommandSender sender, String world, boolean enable) {
		if(enable) {
			if(!Config.doesWorldExist(world)) {
				sender.sendMessage(ChatColor.DARK_RED + "You must specify an existing world name !");
				return true;
			}
			if(Config.addActiveWorld(world)) {
				sender.sendMessage(ChatColor.DARK_GREEN + "World " + world + " enabled successfully");
			}
			else {
				sender.sendMessage(ChatColor.DARK_AQUA + "World " + world + " is already enabled");
			}
		}
		else {
			if(!Config.getActiveWorlds().contains((world))) {
				sender.sendMessage(ChatColor.DARK_RED + "You must specify an active world name !");
				return true;
			}
			if(Config.delActiveWorld(world)) {
				sender.sendMessage(ChatColor.DARK_GREEN + "World " + world + " disabled successfully");
			}
			else {
				sender.sendMessage(ChatColor.DARK_AQUA + "World " + world + " is already disabled");
			}
		}
		return true;
	}
	
	private boolean onToggleWorld(CommandSender sender, String[] args, boolean enable) {
		if(args.length > 2) {
			return false;
		}
		if(args.length == 1) { //Enable/Disable current player world
			if(!(sender instanceof Player)) {
				ConsoleCommandSender console = (ConsoleCommandSender) sender;
				console.sendMessage("You need to specify a world to " + (enable ? "enable" : "disable") + ".");
				return true;
			}
			Player player = (Player) sender;
			return toggleWorld(sender, player.getWorld().getName(), enable);
		}
		else { //Enable/Disable specified world as args[1]
			return toggleWorld(sender, args[1], enable);
		}
	}
	
	private boolean enableWorld(CommandSender sender, String[] args) {
		if(sender instanceof Player && !sender.hasPermission("og.enable")) {
			sender.sendMessage(ChatColor.RED + "Missing permission: og.enable");
			return true;
		}
		return onToggleWorld(sender, args, true);
	}
	
	private boolean disableWorld(CommandSender sender, String[] args) {
		if(sender instanceof Player && !sender.hasPermission("og.disable")) {
			sender.sendMessage(ChatColor.RED + "Missing permission: og.disable");
			return true;
		}
		return onToggleWorld(sender, args, false);
	}
	
	private boolean listWorlds(CommandSender sender) {
		if(sender instanceof Player && !sender.hasPermission("og.list")) {
			sender.sendMessage(ChatColor.RED + "Missing permission: og.list");
			return true;
		}
		sender.sendMessage(ChatColor.DARK_AQUA + "Active worlds: " + Config.getActiveWorlds().toString());
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length >= 1) {
			switch(args[0].toLowerCase()) {
				case "reload":
					return reload(sender, args);
				case "enable":
					return enableWorld(sender, args);
				case "disable":
					return disableWorld(sender, args);
				case "list":
					return listWorlds(sender);
				default:
					return false;
			}
		}
		return false;
	}

}
