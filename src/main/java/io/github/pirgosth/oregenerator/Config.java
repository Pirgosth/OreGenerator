package io.github.pirgosth.oregenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.pirgosth.oregenerator.config.WorldConfig;
import lombok.Getter;

public class Config {
    private FileConfiguration config;
    @Getter
    private boolean debug;
    private Map<String, WorldConfig> activeWorlds = new HashMap<>();
    private Map<String, ArrayList<Material>> materials = new HashMap<>();
    private Map<String, ArrayList<Double>> probabilities = new HashMap<>();

    public void load() {
        OreGenerator.getInstance().saveDefaultConfig();
        this.config = OreGenerator.getInstance().getConfig();
        this.debug = this.config.getBoolean("debug", false);
        new File(OreGenerator.getInstance().getDataFolder(), "worlds").mkdirs();
        this.activeWorlds = new HashMap<>();
        this.materials = new HashMap<>();
        this.probabilities = new HashMap<>();
        for (String world : this.config.getStringList("enabled-worlds")) {
            this.addActiveWorld(world);
        }
        this.cleanConfig();
    }

    public void reload() {
        OreGenerator.getInstance().reloadConfig();
        this.load();
    }

    private void cleanConfig() {
        ArrayList<String> worlds = getActiveWorlds();
        ArrayList<String> common = new ArrayList<>(worlds);
        common.retainAll(Utils.getWorldNames());
        if (common.size() != worlds.size()) {
            worlds.removeAll(common);
            for (String world : worlds) {
                this.delActiveWorld(world);
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Old worlds: " + worlds + " have been removed from configuration.");
        }
    }

    public static boolean doesWorldExist(String world) {
        for (World w : Bukkit.getWorlds()) {
            if (w.getName().equalsIgnoreCase(world)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getActiveWorlds() {
        return new ArrayList<>(this.activeWorlds.keySet());
    }

    public boolean addActiveWorld(String world) {
        WorldConfig worldCfg = this.activeWorlds.get(world);
        if (worldCfg == null) {
            worldCfg = new WorldConfig("worlds/" + world + ".yml");
            worldCfg.saveDefaultConfig();
            worldCfg.reload();
            this.activeWorlds.put(world, worldCfg);
            this.refreshActiveWorlds();
            try {
                this.materials.put(world, loadMaterials(world));
            } catch (Exception e) {
                OreGenerator.getInstance().getLogger().log(Level.SEVERE, "Could not load materials for world " + world, e);
            }
            try {
                this.probabilities.put(world, loadProbabilities(world));
            } catch (Exception e) {
                OreGenerator.getInstance().getLogger().log(Level.SEVERE, "Could not load probabilities for world " + world, e);
            }
            return true;
        }
        return false;
    }

    public boolean delActiveWorld(String world) {
        WorldConfig worldCfg = this.activeWorlds.get(world);
        if (worldCfg != null) {
            this.activeWorlds.remove(world);
            this.refreshActiveWorlds();
            this.materials.remove(world);
            this.probabilities.remove(world);
            return true;
        }
        return false;
    }

    public void refreshActiveWorlds() {
        this.config.set("enabled-worlds", getActiveWorlds());
        OreGenerator.getInstance().saveConfig();
    }

    public ArrayList<Material> loadMaterials(String world) throws Exception {
        FileConfiguration worldConfig = activeWorlds.get(world).config();
        ConfigurationSection section = worldConfig.getConfigurationSection("blocks");
        if (section == null) {
            throw new Exception("Missing blocks section !");
        }
        ArrayList<Material> worldMaterials = new ArrayList<>();
        for (String block : section.getKeys(false)) {
            String key = worldConfig.getString("blocks." + block + ".material");
            if (key.isEmpty()) {
                throw new Exception("Missing material for: " + block);
            }
            Material material = Material.getMaterial(key);
            if (material == null) {
                throw new Exception("Invalid Material: " + key);
            }
            worldMaterials.add(material);
        }
        return worldMaterials;
    }

    public ArrayList<Double> loadProbabilities(String world) throws Exception {
        FileConfiguration worldConfig = activeWorlds.get(world).config();
        ConfigurationSection section = worldConfig.getConfigurationSection("blocks");
        if (section == null) {
            throw new Exception("Missing blocks section for world " + world + "!");
        }
        ArrayList<Double> worldProbabilities = new ArrayList<>();
        for (String block : section.getKeys(false)) {
            double probability = worldConfig.getDouble("blocks." + block + ".probability", Double.POSITIVE_INFINITY);
            if (Double.isInfinite(probability)) {
                throw new Exception("Missing probability for: " + block);
            }
            if (!Utils.IsBetween(probability, 0, 1)) {
                throw new Exception("Invalid probability for: " + block + ". Must be between 0 and 1 !");
            }
            worldProbabilities.add(probability);
        }
        int pSum = Utils.Sum(worldProbabilities);
        if (pSum != 1) {
            throw new Exception("The sum of probabilities does not equal 1 (" + pSum + ")");
        }
        return worldProbabilities;
    }

    public ArrayList<Material> getMaterials(String world) {
        return materials.get(world);
    }

    public ArrayList<Double> getProbabilities(String world) {
        return probabilities.get(world);
    }

    public boolean isNetherGenerator(String worldName) {
        return this.activeWorlds.get(worldName).isNetherGenerator();
    }
}
