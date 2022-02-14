package io.github.pirgosth.oregenerator;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockFormEvent;

public class Random {
    public Material RandomOre(BlockFormEvent event, boolean deep) {
        String worldName = event.getBlock().getWorld().getName();
        ArrayList<Material> materials = OreGenerator.getMainConfig().getMaterials(worldName);
        ArrayList<Double> probabilities = OreGenerator.getMainConfig().getProbabilities(worldName);
        materials.set(0, deep ? Material.DEEPSLATE : event.getNewState().getType());
        double r = Math.random();
        double c = 0;
        for (int i = 0; i < materials.size(); i++) {
            if (r >= c && r < c + probabilities.get(i)) {
                if (OreGenerator.getMainConfig().isDebug()) {
                    Bukkit.getConsoleSender().sendMessage("Random value: " + r + " material: " + materials.get(i).toString());
                }
                return materials.get(i);
            }
            c += probabilities.get(i);
        }
        if (OreGenerator.getMainConfig().isDebug()) {
            Bukkit.getConsoleSender().sendMessage("Random value: " + r + " invalid material");
        }
        return Material.COBBLESTONE;
    }

    public Material RandomOre(BlockFormEvent event) {
        return this.RandomOre(event, false);
    }
}
