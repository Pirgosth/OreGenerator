package io.github.pirgosth.oregenerator.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.pirgosth.oregenerator.OreGenerator;

public class CustomConfig {
	protected FileConfiguration config = null;
	protected File configFile = null;
	protected final String path;
	protected boolean copyDefaults = false;
	
	public CustomConfig(String path, boolean copyDefaults) {
		this.path = path;
		this.copyDefaults = copyDefaults;
		reload();
	}
	
	public CustomConfig(String path) {
		this(path, false);
	}
	
	public void setDefaults() {
		
	}
	
	public void reload() {
		configFile = new File(OreGenerator.getInstance().getDataFolder(), path);
		config = YamlConfiguration.loadConfiguration(configFile);
		
		setDefaults();
		
		if(OreGenerator.getInstance().getResource(path) == null) {
			return;
		}
		
		//Look for defaults in jar
		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(OreGenerator.getInstance().getResource(path), "UTF8");
			if(defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				config.options().copyDefaults(copyDefaults);
				config.setDefaults(defConfig);
			}
		}
		catch(UnsupportedEncodingException e) {
			OreGenerator.getInstance().getLogger().log(Level.SEVERE, "Could not load default config for " + path, e);
		}
	}
	
	public FileConfiguration config() {
		return config;
	}
	
	public void save() {
		try {
			config.save(configFile);
		}
		catch(IOException e) {
			OreGenerator.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
		}
	}
	
	public void saveDefaultConfig() {
		if(!configFile.exists()) {
			OreGenerator.getInstance().saveResource(path, false);
		}
	}
}
