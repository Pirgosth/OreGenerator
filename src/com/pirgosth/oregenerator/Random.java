package com.pirgosth.oregenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockFormEvent;

public class Random {
	private ArrayList<Material> materials;
	private ArrayList<Double> probabilities;
	
	public Random(){
		try {
			reload();
		}
		catch(Exception e) {
			Bukkit.getLogger().log(Level.WARNING, e.toString());
		}
	}
	
	private void loadDefault() {
		materials = new ArrayList<Material>(Arrays.asList(Material.COBBLESTONE, Material.IRON_ORE,
				Material.GOLD_ORE, Material.REDSTONE_ORE, Material.DIAMOND_ORE, 
				Material.EMERALD_ORE, Material.EMERALD_BLOCK));
		probabilities = new ArrayList<Double>(Arrays.asList(0.8, 0.1435, 0.015, 0.03, 0.01, 0.001, 0.0005));
	}
	
	public void reload() throws Exception{
		try {
			materials = Load.config.getMaterials();
			probabilities = Load.config.getProbabilities();
		}
		catch(Exception e) {
			loadDefault();
			Bukkit.getLogger().log(Level.WARNING, "Loading default materials ...");
			throw e;
		}
	}
	
	public Material RandomOre(BlockFormEvent event){
		materials.set(0, event.getNewState().getType());
		double r = Math.random();
		double c = 0;
		for(int i = 0; i<materials.size(); i++) {
			if(r >= c && r < c+probabilities.get(i)) {
				if(Load.config.debug()) {
					Bukkit.getConsoleSender().sendMessage("Random value: " + r + " material: " + materials.get(i).toString());
				}
				return materials.get(i);
			}
			c += probabilities.get(i);
		}
		if(Load.config.debug()) {
			Bukkit.getConsoleSender().sendMessage("Random value: " + r + " invalid material");
		}
		return Material.COBBLESTONE;
	}
}
