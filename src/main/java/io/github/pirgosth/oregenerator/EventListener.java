package io.github.pirgosth.oregenerator;

import org.bukkit.Material;
import org.bukkit.Sound;
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
        if (event.getNewState().getType() == Material.COBBLESTONE || event.getNewState().getType() == Material.STONE) {
            if (!OreGenerator.getMainConfig().getActiveWorlds().contains(event.getBlock().getWorld().getName())) {
                return;
            }
            event.setCancelled(true);
            event.getBlock().setType(random.RandomOre(event));
            event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1.0f, 2.0f);
        }
    }
}
