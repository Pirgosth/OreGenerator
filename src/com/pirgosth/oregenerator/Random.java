package com.pirgosth.oregenerator;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class Random {
	public static Material RandomOre(ArrayList<Material> materials, ArrayList<Float> probabilities) throws Exception{
		if(Utility.Sum(probabilities) != 1.0f) {
			throw new ArithmeticException("The sum of probailities must be 1 !");
		}
		else if(!Utility.IsBetween(probabilities, 0.0f, 1.0f)) {
			throw new ArithmeticException("All probailities must be between 0 and 1 !");
		}
		else if(materials.size() != probabilities.size()) {
			throw new Exception("Sizes of materials and probabilities must be the same !");
		}
		double r = Math.random();
		double c = 0;
		for(int i = 0; i<materials.size(); i++) {
			if(r >= c && r < c+probabilities.get(i)) {
				if(Config.debug()) {
					Bukkit.getConsoleSender().sendMessage("Random value: " + r + " material: " + materials.get(i).toString());
				}
				return materials.get(i);
			}
			c += probabilities.get(i);
		}
		if(Config.debug()) {
			Bukkit.getConsoleSender().sendMessage("Random value: " + r + " invalid material");
		}
		return Material.COBBLESTONE;
	}
}
