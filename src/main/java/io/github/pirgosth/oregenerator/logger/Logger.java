package io.github.pirgosth.oregenerator.logger;

import org.bukkit.Bukkit;

public class Logger {
	public static void sendMessage(String message) {
		Bukkit.getConsoleSender().sendMessage("[OreGenerator] " + message);
	}
}
