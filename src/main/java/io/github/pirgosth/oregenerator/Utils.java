package io.github.pirgosth.oregenerator;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Utils {
	public static int Sum(ArrayList<Double> l) {
		int result = 0;
		for(double x : l) {
			result += Math.ceil(x * 100000);
		}
		return result / 100000;
	}
	
	public static boolean IsBetween(double l, double a, double b) {
		return(l >= a && l <= b);
	}
	
	public static ArrayList<String> getWorldNames(){
		ArrayList<String> worlds = new ArrayList<>();
		for(World world: Bukkit.getWorlds()) {
			worlds.add(world.getName());
		}
		return worlds;
	}
}
