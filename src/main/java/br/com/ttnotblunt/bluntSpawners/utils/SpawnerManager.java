package br.com.ttnotblunt.bluntSpawners.utils;

import org.bukkit.configuration.file.FileConfiguration;
import br.com.ttnotblunt.bluntSpawners.BluntSpawners;
import org.bukkit.entity.EntityType;

import java.util.Objects;

public class SpawnerManager {

    public static String traduzirMob(String mob) {
        FileConfiguration config = BluntSpawners.getInstance().getConfig();
        return config.getString("mobs." + mob.toLowerCase(), mob);
    }

    public static boolean ehMobValido(String mob) {
        FileConfiguration config = BluntSpawners.getInstance().getConfig();
        return config.contains("mobs." + mob.toLowerCase());
    }

    public static EntityType getTipoPorNome(String nomeItem) {
        FileConfiguration config = BluntSpawners.getInstance().getMobsConfig();
        for (String key : Objects.requireNonNull(config.getConfigurationSection("mobs")).getKeys(false)) {
            String traduzido = config.getString("mobs." + key);
            if (traduzido != null && traduzido.equalsIgnoreCase(nomeItem.replace("Spawner de ", "").trim())) {
                try {
                    return EntityType.valueOf(key.toUpperCase());
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        return null;
    }
}
