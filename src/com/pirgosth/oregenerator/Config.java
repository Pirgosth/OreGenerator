package com.pirgosth.oregenerator;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	private JavaPlugin plugin;
	private FileConfiguration config;
	
	public Config(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public boolean debug() {
		return config.getBoolean("debug", false);
	}
	
	public void load() {
		plugin.saveDefaultConfig();
		config = plugin.getConfig();
		cleanConfig();
		save();
	}
	
	public void reload(){
		plugin.saveDefaultConfig();
		plugin.reloadConfig();
		config = plugin.getConfig();
		cleanConfig();
		save();
	}
	
	public void save() {
		plugin.saveConfig();
	}

	public void cleanConfig() {
		ArrayList<String> worlds = getActiveWorlds();
		ArrayList<String> common = new ArrayList<String>(worlds);
		common.retainAll(Utility.getWorldNames());
		config.set("enabled-worlds", common);
		if(common.size() != worlds.size()) {
			worlds.removeAll(common);
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Old worlds: " + worlds.toString() + " have been removed from configuration.");
		}
	}
	
	public static boolean doesWorldExist(String world) {
		for(World w : Bukkit.getWorlds()) {
			if(w.getName().equalsIgnoreCase(world)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getActiveWorlds(){
		List<String> worlds = config.getStringList("enabled-worlds");
		if(worlds == null) {
			return new ArrayList<String>();
		}
		return new ArrayList<String>(worlds);
	}
	
	public boolean addActiveWorld(String world) {
		ArrayList<String> worlds = getActiveWorlds();
		if(!worlds.contains(world)) {
			worlds.add(world);
			config.set("enabled-worlds", worlds);
			
			return true;
		}
		return false;
	}
	
	public boolean delActiveWorld(String world) {
		ArrayList<String> worlds = getActiveWorlds();
		if(worlds.contains(world)) {
			worlds.remove(world);
			config.set("enabled-worlds", worlds);
			return true;
		}
		return false;
	}
	
	public ArrayList<Material> getMaterials() throws Exception{
		ConfigurationSection section = config.getConfigurationSection("blocks");
		if(section == null) {
			throw new Exception("Missing blocks section !");
		}
		ArrayList<Material> materials = new ArrayList<Material>();
		for(String block: section.getKeys(false)) {
			String key = config.getString("blocks." + block + ".material");
			if(key.isEmpty()) {
				throw new Exception("Missing material for: " + block);
			}
			Material material = Material.getMaterial(key);
			if(material == null) {
				throw new Exception("Invalid Material: " + key);
			}
			materials.add(material);
		}
		return materials;
	}
	
	public ArrayList<Double> getProbabilities() throws Exception{
		ConfigurationSection section = config.getConfigurationSection("blocks");
		if(section == null) {
			throw new Exception("Missing blocks section !");
		}
		ArrayList<Double> probabilities = new ArrayList<Double>();
		for(String block: section.getKeys(false)) {
			double probability = config.getDouble("blocks." + block + ".probability", Double.POSITIVE_INFINITY);
			if(Double.isInfinite(probability)) {
				throw new Exception("Missing probability for: " + block);
			}
			if(!Utility.IsBetween(probability, 0, 1)) {
				throw new Exception("Invalid probability for: " + block + ". Must be between 0 and 1 !");
			}
			probabilities.add(probability);
		}
		double pSum = Utility.Sum(probabilities);
		if(pSum != 1.0f) {
			throw new Exception("The sum of probabilities does not equal 1 (" + pSum + ")");
		}
		return probabilities;
	}
}
