package io.github.pirgosth.oregenerator;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class OreGenerator extends JavaPlugin{
	@Getter
	private static JavaPlugin instance = null;
	@Getter
	private static Config mainConfig;
	
	@Override
	public void onEnable() {
		instance = this;
		mainConfig = new Config();
		mainConfig.load();
		getCommand("og").setExecutor(new Commands());
		getCommand("og").setTabCompleter(new AutoCompletion());
		getServer().getPluginManager().registerEvents(new EventListener(), this);
	}
}
