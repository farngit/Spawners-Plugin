package br.com.ttnotblunt.bluntSpawners;

import br.com.ttnotblunt.bluntSpawners.commands.DarSpawnerCommand;
import br.com.ttnotblunt.bluntSpawners.listeners.MobBehaviorListener;
import br.com.ttnotblunt.bluntSpawners.utils.SpawnerGUI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.*;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class BluntSpawners extends JavaPlugin {

    private static BluntSpawners instance;

    @Override
    public void onEnable() {
        instance = this;
        Objects.requireNonNull(getCommand("darspawner")).setExecutor(new DarSpawnerCommand());
        getServer().getPluginManager().registerEvents(new br.com.ttnotblunt.bluntSpawners.listeners.SpawnerListener(), this);
        getServer().getPluginManager().registerEvents(new MobBehaviorListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerGUI(), this);
        saveMobsFile();
        saveSpawnerFile();


        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + " _ ___");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "| '_  \\  _                _       _ ___   _                _");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "| |_) ) | |              | |     | '__ \\ | |              (_)");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "|_ ___/ | | _   _  _ __  | |_    | |__) || | _   _   __ _  _  _ __");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "| '_  \\ | || | | || '_ \\ | __|   |  ___/ | || | | | / _' || || '_ \\");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "| |_) ) | || |_| || | | || |_    | |     | || |_| |(.(_| || || | | | _");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "|_____/ |_| \\__._||_| |_| \\__|   |_|     |_| \\__._| \\__. ||_||_| |_||_|");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "                                                    _  | |");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "  All rights reserved. ©       ENABLED              ( \\_/ |");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "  2024-2025                 Spawner Plugin          \\¨__/");
    }

    private FileConfiguration spawnerConfig;
    private File spawnerFile;

    private void saveSpawnerFile() {
        spawnerFile = new File(getDataFolder(), "spawners.yml");
        if (!spawnerFile.exists()) {
            saveResource("spawners.yml", false);
        }
        spawnerConfig = YamlConfiguration.loadConfiguration(spawnerFile);
    }

    public FileConfiguration getSpawnerConfig() {
        return spawnerConfig;
    }

    public void saveSpawnerConfig() {
        try {
            spawnerConfig.save(spawnerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BluntSpawners getInstance() {
        return instance;
    }

    private FileConfiguration mobsConfig;
    private File mobsFile;

    private void saveMobsFile() {
        mobsFile = new File(getDataFolder(), "mobs.yml");
        if (!mobsFile.exists()) {
            saveResource("mobs.yml", false);
        }
        mobsConfig = YamlConfiguration.loadConfiguration(mobsFile);
    }

    public FileConfiguration getMobsConfig() {
        return mobsConfig;
    }

    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + " _ ___");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + "| '_  \\  _                _       _ ___   _                _");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + "| |_) ) | |              | |     | '__ \\ | |              (_)");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + "|_ ___/ | | _   _  _ __  | |_    | |__) || | _   _   __ _  _  _ __");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + "| '_  \\ | || | | || '_ \\ | __|   |  ___/ | || | | | / _' || || '_ \\");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + "| |_) ) | || |_| || | | || |_    | |     | || |_| |(.(_| || || | | | _");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + "|_____/ |_| \\__._||_| |_| \\__|   |_|     |_| \\__._| \\__. ||_||_| |_||_|");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + "                                                    _  | |");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + "   All rights reserved. ©       DISABLED            ( \\_/ |");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED  + "   2024-2025                 Spawner Plugin         \\¨__/");

    }
}