package com.pirgosth.oregenerator;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Utility {
	public static float Sum(ArrayList<Float> l) {
		float result = 0;
		for(float x : l) {
			result += x;
		}
		return result;
	}
	
	public static boolean IsBetween(ArrayList<Float> l, float a, float b) {
		for(float x : l) {
			if(x < a || x > b) {
				return false;
			}
		}
		return true;
	}
	
	public static ArrayList<String> getWorldNames(){
		ArrayList<String> worlds = new ArrayList<String>();
		for(World world: Bukkit.getWorlds()) {
			worlds.add(world.getName());
		}
		return worlds;
	}
}
