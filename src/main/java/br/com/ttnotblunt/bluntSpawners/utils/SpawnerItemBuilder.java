package br.com.ttnotblunt.bluntSpawners.utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpawnerItemBuilder {

    public static ItemStack criarSpawner(String tipo, int quantidade) {
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("Tipo do spawner não pode ser vazio.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }

        ItemStack item = new ItemStack(Material.SPAWNER, quantidade);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            throw new IllegalStateException("Não foi possível obter o ItemMeta do spawner.");
        }

        meta.setDisplayName("§aSpawner de " + tipo);
        meta.setLore(Collections.singletonList("§7Mob: " + tipo));
        item.setItemMeta(meta);

        return item;
    }
}
