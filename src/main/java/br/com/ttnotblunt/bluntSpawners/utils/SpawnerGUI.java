package br.com.ttnotblunt.bluntSpawners.utils;

import br.com.ttnotblunt.bluntSpawners.data.SpawnerDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;


import java.util.*;

public class SpawnerGUI implements Listener {

    private static final Map<UUID, Location> spawnerAberto = new HashMap<>();
    private static final String GUI_TITLE = "§aGerenciar Spawner";
    private static final String REMOVER_NAME = ChatColor.RED + "Remover Spawner";
    private static final String DETALHES_NAME = "§bDetalhes do Spawner";

    public static void abrir(@NonNull Player player, @NonNull Location loc) {
        Inventory gui = Bukkit.createInventory(null, 27, GUI_TITLE);

        CreatureSpawner spawner = (CreatureSpawner) loc.getBlock().getState();
        String tipo = Objects.requireNonNull(spawner.getSpawnedType()).name();

        // Cabeça com info geral
        ItemStack info = criarCabecaInfo(player, loc, tipo);
        // Botão de remover
        ItemStack remover = criarItemRemover();
        // Informações avançadas
        ItemStack avancado = criarItemDetalhes(loc, tipo);

        gui.setItem(3, info);
        gui.setItem(5, remover);
        gui.setItem(20, avancado);

        player.openInventory(gui);
        spawnerAberto.put(player.getUniqueId(), loc.clone()); // Usar clone() para evitar modificações externas
    }

    private static ItemStack criarCabecaInfo(Player player, Location loc, String tipo) {
        ItemStack info = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) info.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(player);
            meta.setDisplayName("§eInformações do Spawner");
            List<String> lore = new ArrayList<>();
            lore.add("§7Mob: §f" + SpawnerManager.traduzirMob(tipo));
            lore.add("§7Local: §f" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
            meta.setLore(lore);
            info.setItemMeta(meta);
        }
        return info;
    }

    private static ItemStack criarItemRemover() {
        ItemStack remover = new ItemStack(Material.BARRIER);
        ItemMeta meta = remover.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(REMOVER_NAME);
            remover.setItemMeta(meta);
        }
        return remover;
    }

    private static ItemStack criarItemDetalhes(Location loc, String tipo) {
        ItemStack avancado = new ItemStack(Material.BOOK);
        ItemMeta meta = avancado.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(DETALHES_NAME);
            List<String> lore = new ArrayList<>();
            lore.add("§7Dono: §f" + SpawnerDataAPI.getDono(loc));
            lore.add("§7Tipo: §f" + SpawnerManager.traduzirMob(tipo));
            lore.add("§7Stack: §f" + SpawnerDataAPI.getQuantidade(loc));
            meta.setLore(lore);
            avancado.setItemMeta(meta);
        }
        return avancado;
    }

    @EventHandler
    public void aoClicar(@NonNull InventoryClickEvent e) {
        if (!GUI_TITLE.equals(e.getView().getTitle())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player player)) return;

        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return;

        String nome = meta.getDisplayName();
        Location loc = spawnerAberto.get(player.getUniqueId());

        if (loc == null) {
            player.sendMessage("§cNão foi possível encontrar o spawner.");
            return;
        }

        if (REMOVER_NAME.equals(nome)) {
            handleRemoverSpawner(player, loc);
        } else if (DETALHES_NAME.equals(nome)) {
            player.sendMessage("§aVocê está visualizando os detalhes do spawner.");
        }
    }

    private void handleRemoverSpawner(Player player, Location loc) {
        loc.getBlock().setType(Material.AIR);
        SpawnerDataAPI.remover(loc);
        player.sendMessage("§aSpawner removido com sucesso.");
        player.closeInventory();
        spawnerAberto.remove(player.getUniqueId());
    }
}