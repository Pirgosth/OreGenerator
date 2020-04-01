package com.pirgosth.oregenerator;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListener implements Listener{
	private ArrayList<Material> materials;
	private ArrayList<Float> probabilities;

	public EventListener(JavaPlugin plugin) {
		materials = new ArrayList<>(Arrays.asList(Material.COBBLESTONE, Material.IRON_ORE,
				Material.GOLD_ORE, Material.REDSTONE_ORE, Material.DIAMOND_ORE, 
				Material.EMERALD_ORE, Material.EMERALD_BLOCK));
		probabilities = new ArrayList<>(Arrays.asList(0.8f, 0.1435f, 0.015f, 0.03f, 0.01f, 0.001f, 0.0005f));
	}
	
	@EventHandler
	public void onBlockFormEvent(BlockFormEvent event) {
		if(event.getNewState().getType() == Material.COBBLESTONE || event.getNewState().getType() == Material.STONE) {
			if(!Config.getActiveWorlds().contains(event.getBlock().getWorld().getName())) {
				return;
			}
			try {
				materials.set(0, event.getNewState().getType());
				event.setCancelled(true);
				event.getBlock().setType(Random.RandomOre(materials, probabilities));
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1.0f, 2.0f);
			}
			catch(Exception e) {
				Bukkit.getConsoleSender().sendMessage("[OreGenerator]: " + ChatColor.DARK_RED + "[Error]: " + e);
				if(event.isCancelled()) {
					event.setCancelled(false);
				}
			}
		}
	}
}
