package com.pirgosth.oregenerator;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Load extends JavaPlugin{
	public static Config config;
	
	public Load() {
		config = new Config(this);
	}
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Plugin is loading ...");
		config.load();
		getCommand("og").setExecutor(new Commands());
		getCommand("og").setTabCompleter(new AutoCompletion());
		getServer().getPluginManager().registerEvents(new EventListener(), this);
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Plugin is shutting down ...");
		config.save();
	}
}
