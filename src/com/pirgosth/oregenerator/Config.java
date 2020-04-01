package com.pirgosth.oregenerator;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	public static void initDefaultConfig() {
		Load.config.options().copyDefaults(true);
		Load.config.addDefault("enabled-worlds", "");
		Load.config.addDefault("debug", false);
	}
	
	public static boolean debug() {
		return Load.config.getBoolean("debug", false);
	}
	
	public static void load(JavaPlugin plugin) {
		Load.config = plugin.getConfig();
		initDefaultConfig();
		plugin.saveConfig();
	}
	
	public static void reload(JavaPlugin plugin) {
		plugin.reloadConfig();
		load(plugin);
	}
	
	public static void save(JavaPlugin plugin) {
		plugin.saveConfig();
	}
	
	public static boolean doesWorldExist(String world) {
		for(World w : Bukkit.getWorlds()) {
			if(w.getName().equalsIgnoreCase(world)) {
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<String> getActiveWorlds(){
		List<String> worlds = Load.config.getStringList("enabled-worlds");
		if(worlds == null) {
			return new ArrayList<String>();
		}
		return new ArrayList<String>(worlds);
	}
	
	public static boolean addActiveWorld(String world) {
		ArrayList<String> worlds = getActiveWorlds();
		if(!worlds.contains(world)) {
			worlds.add(world);
			Load.config.set("enabled-worlds", worlds);
			return true;
		}
		return false;
	}
	
	public static boolean delActiveWorld(String world) {
		ArrayList<String> worlds = getActiveWorlds();
		if(worlds.contains(world)) {
			worlds.remove(world);
			Load.config.set("enabled-worlds", worlds);
			return true;
		}
		return false;
	}
}
