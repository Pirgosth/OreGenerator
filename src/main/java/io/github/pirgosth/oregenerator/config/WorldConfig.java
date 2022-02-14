package io.github.pirgosth.oregenerator.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

import io.github.pirgosth.oregenerator.OreGenerator;

public class WorldConfig extends CustomConfig{

	public WorldConfig(String path) {
		super(path, true);
	}
	
	@Override
	public void setDefaults() {
		config.addDefault("blocks.default.material", "COBBLESTONE");
		config.addDefault("blocks.default.probability", 1.0);
	}
	
	@Override
	public void saveDefaultConfig() {
		if(!configFile.exists()) {
			InputStream stream = OreGenerator.getInstance().getResource("world.yml");
			try{
				Files.copy(stream, new File(OreGenerator.getInstance().getDataFolder(), path).getAbsoluteFile().toPath());
			}
			catch(IOException e) {
				OreGenerator.getInstance().getLogger().log(Level.SEVERE, "Could not save default config: " + path, e);
			}
		}
	}

	public boolean isNetherGenerator() {
		return config.getBoolean("nether-mode", false);
	}
}
