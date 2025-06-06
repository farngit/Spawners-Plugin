package br.com.ttnotblunt.bluntSpawners.data;

import br.com.ttnotblunt.bluntSpawners.BluntSpawners;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.Objects;

public class SpawnerDataAPI {

    private static String getPath(Location loc) {
        return Objects.requireNonNull(loc.getWorld()).getName() + "." + loc.getBlockX() + "_" + loc.getBlockY() + "_" + loc.getBlockZ();
    }

 //   // public static void setDono(Location loc, String nome) {
 //        FileConfiguration config = BluntSpawners.getInstance().getSpawnerConfig();
 //        config.set(getPath(loc) + ".dono", nome);
 //        BluntSpawners.getInstance().saveSpawnerConfig();
 //    }

    public static String getDono(Location loc) {
        FileConfiguration config = BluntSpawners.getInstance().getSpawnerConfig();
        return config.getString(getPath(loc) + ".dono", "Desconhecido");
    }

    public static void setQuantidade(Location loc, int quantidade) {
        FileConfiguration config = BluntSpawners.getInstance().getSpawnerConfig();
        config.set(getPath(loc) + ".quantidade", quantidade);
        BluntSpawners.getInstance().saveSpawnerConfig();
    }

    public static int getQuantidade(Location loc) {
        FileConfiguration config = BluntSpawners.getInstance().getSpawnerConfig();
        return config.getInt(getPath(loc) + ".quantidade", 1);
    }

    public static void remover(Location loc) {
        FileConfiguration config = BluntSpawners.getInstance().getSpawnerConfig();
        config.set(getPath(loc), null);
        BluntSpawners.getInstance().saveSpawnerConfig();
    }
}