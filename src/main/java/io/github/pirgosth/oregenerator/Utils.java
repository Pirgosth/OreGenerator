package io.github.pirgosth.oregenerator;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Utils {
	public static double Sum(ArrayList<Double> l) {
		double result = 0;
		for(double x : l) {
			result += x;
		}
		return result;
	}
	
	public static boolean IsBetween(double l, double a, double b) {
		return(l >= a && l <= b);
	}
	
	public static ArrayList<String> getWorldNames(){
		ArrayList<String> worlds = new ArrayList<String>();
		for(World world: Bukkit.getWorlds()) {
			worlds.add(world.getName());
		}
		return worlds;
	}
}
