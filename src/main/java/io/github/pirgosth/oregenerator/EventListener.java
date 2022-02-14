package io.github.pirgosth.oregenerator;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class EventListener implements Listener {
    public static Random random;

    public EventListener() {
        random = new Random();
    }

    @EventHandler
    public void onBlockFormEvent(BlockFormEvent event) {

        if (event.getNewState().getType() == Material.BASALT) {
            String worldName = event.getBlock().getWorld().getName();
            if (!OreGenerator.getMainConfig().getActiveWorlds().contains(worldName) || !OreGenerator.getMainConfig().isNetherGenerator(worldName)) {
                return;
            }
            event.getNewState().setType(random.RandomOre(event));
        }

        if (event.getNewState().getType() == Material.COBBLESTONE || event.getNewState().getType() == Material.STONE) {
            String worldName = event.getBlock().getWorld().getName();
            if (!OreGenerator.getMainConfig().getActiveWorlds().contains(worldName) || OreGenerator.getMainConfig().isNetherGenerator(worldName)) {
                return;
            }
            event.getNewState().setType(random.RandomOre(event, event.getBlock().getY() < 0));
        }
    }
}
