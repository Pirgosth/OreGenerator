package com.pirgosth.oregenerator;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Load extends JavaPlugin{
	public static FileConfiguration config;
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Plugin is loading ...");
		Config.load(this);
		getCommand("og").setExecutor(new Commands(this));
		getCommand("og").setTabCompleter(new AutoCompletion());
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Plugin is shutting down ...");
		Config.save(this);
	}
}
