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

import java.util.*;

public class SpawnerGUI implements Listener {

    private static final Map<UUID, Location> spawnerAberto = new HashMap<>();

    public static void abrir(Player player, Location loc) {
        Inventory gui = Bukkit.createInventory(null, 27, "§aGerenciar Spawner");

        CreatureSpawner spawner = (CreatureSpawner) loc.getBlock().getState();
        String tipo = Objects.requireNonNull(spawner.getSpawnedType()).name();

        // Cabeça com info geral
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

        // Botão de remover
        ItemStack remover = new ItemStack(Material.BARRIER);
        ItemMeta metaRemover = remover.getItemMeta();
        if (metaRemover != null) {
            metaRemover.setDisplayName(ChatColor.RED + "Remover Spawner");
            remover.setItemMeta(metaRemover);
        }

        // Informações avançadas
        ItemStack avancado = new ItemStack(Material.BOOK);
        ItemMeta metaAvancado = avancado.getItemMeta();
        if (metaAvancado != null) {
            metaAvancado.setDisplayName("§bDetalhes do Spawner");
            List<String> lore = new ArrayList<>();
            lore.add("§7Dono: §f" + SpawnerDataAPI.getDono(loc));
            lore.add("§7Tipo: §f" + SpawnerManager.traduzirMob(tipo));
            lore.add("§7Stack: §f" + SpawnerDataAPI.getQuantidade(loc));
            metaAvancado.setLore(lore);
            avancado.setItemMeta(metaAvancado);
        }

        gui.setItem(3, info);
        gui.setItem(5, remover);
        gui.setItem(20, avancado);

        player.openInventory(gui);
        spawnerAberto.put(player.getUniqueId(), loc); // salvar local para clique futuro
    }

    @EventHandler
    public void aoClicar(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals("§aGerenciar Spawner")) return;

        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String nome = Objects.requireNonNull(item.getItemMeta()).getDisplayName();
        if (nome == null) return;

        Location loc = spawnerAberto.get(player.getUniqueId());
        if (loc == null) {
            player.sendMessage("§cNão foi possível encontrar o spawner.");
            return;
        }

        if (nome.equals(ChatColor.RED + "Remover Spawner")) {
            loc.getBlock().setType(Material.AIR); // remove o bloco
            SpawnerDataAPI.remover(loc); // remove dados do arquivo
            player.sendMessage("§aSpawner removido com sucesso.");
            player.closeInventory();
            spawnerAberto.remove(player.getUniqueId()); // limpa cache
        } else if (nome.equals("§bDetalhes do Spawner")) {
            player.sendMessage("§aVocê está visualizando os detalhes do spawner.");
        }
    }
}
