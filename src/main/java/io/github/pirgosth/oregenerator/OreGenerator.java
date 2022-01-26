package io.github.pirgosth.oregenerator;

import io.github.pirgosth.liberty.core.LibertyCore;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

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
		LibertyCore.getInstance().getCommandRegister().register(this, new OreCommands());
		getServer().getPluginManager().registerEvents(new EventListener(), this);
	}
}
