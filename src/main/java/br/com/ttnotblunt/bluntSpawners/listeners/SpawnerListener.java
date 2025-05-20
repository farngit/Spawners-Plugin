package br.com.ttnotblunt.bluntSpawners.listeners;

import br.com.ttnotblunt.bluntSpawners.data.SpawnerDataAPI;
import br.com.ttnotblunt.bluntSpawners.utils.SpawnerGUI;
import br.com.ttnotblunt.bluntSpawners.utils.SpawnerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;

import java.util.Objects;

public class SpawnerListener implements Listener {

    @EventHandler
    public void aoClicarSpawner(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = e.getClickedBlock();
        if (block == null || block.getType() != Material.SPAWNER) return;

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.SPAWNER || !item.hasItemMeta()) return;

        Location loc = block.getLocation();
        CreatureSpawner existente = (CreatureSpawner) block.getState();
        EntityType tipoExistente = existente.getSpawnedType();

        // Verifica o tipo de mob no item da mão
        String nomeItem = Objects.requireNonNull(item.getItemMeta()).getDisplayName();
        EntityType tipoNovo = SpawnerManager.getTipoPorNome(nomeItem);

        if (tipoNovo == null || tipoNovo != tipoExistente) {
            player.sendMessage(ChatColor.RED + "Este spawner é de outro tipo de mob.");
            return;
        }

        // Verifica dono
        String dono = SpawnerDataAPI.getDono(loc);
        if (!dono.equals(player.getName())) {
            player.sendMessage(ChatColor.RED + "Você não é o dono deste spawner.");
            return;
        }

        // Empilha
        int atual = SpawnerDataAPI.getQuantidade(loc);
        SpawnerDataAPI.setQuantidade(loc, atual + 1);

        // Remove item da mão
        item = player.getInventory().getItemInMainHand();
        player.getInventory().setItemInMainHand(item);

        player.sendMessage(ChatColor.GREEN + "Spawner colocado com sucesso, stack atual: " + (atual + 1));
        e.setCancelled(true);
    }

    @EventHandler
    public void aoInteragir(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block bloco = e.getClickedBlock();
        if (bloco == null || bloco.getType() != Material.SPAWNER) return;

        Player p = e.getPlayer();

        if (!p.hasPermission("bluntspawners.admin")) {
            p.sendMessage("§cVocê não tem permissão para interagir com este spawner.");
            e.setCancelled(true);
            return;
        }

        CreatureSpawner spawner = (CreatureSpawner) bloco.getState();
        SpawnerGUI.abrir(p, spawner.getLocation());
        e.setCancelled(true);
    }
}
