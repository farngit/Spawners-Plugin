package br.com.ttnotblunt.bluntSpawners.commands;

import br.com.ttnotblunt.bluntSpawners.BluntSpawners;
import br.com.ttnotblunt.bluntSpawners.utils.SpawnerItemBuilder;
import br.com.ttnotblunt.bluntSpawners.utils.SpawnerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class DarSpawnerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("bluntplugin.spawner.give")) {
            sender.sendMessage("§cVocê não tem permissão para isso.");
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage("§cUso correto: /darspawner <jogador> <mob> <quantidade>");
            return true;
        }

        Player alvo = Bukkit.getPlayer(args[0]);
        if (alvo == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return true;
        }

        String mob = args[1].toLowerCase();

        if (!SpawnerManager.ehMobValido(mob)) {
            try {
                String mobsDisponiveis = String.join(", ",
                        Objects.requireNonNull(BluntSpawners.getInstance()
                                        .getMobsConfig()
                                        .getConfigurationSection("mobs"))
                                .getKeys(false));
                sender.sendMessage("§cMob inválido. Mobs disponíveis: " + mobsDisponiveis);
            } catch (NullPointerException e) {
                sender.sendMessage("§cMob inválido. (Erro ao carregar lista de mobs)");
            }
            return true;
        }

        String mobTraduzido = SpawnerManager.traduzirMob(mob);

        int quantidade;
        try {
            quantidade = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cQuantidade inválida.");
            return true;
        }

        ItemStack spawner = SpawnerItemBuilder.criarSpawner(mobTraduzido, quantidade);
        alvo.getInventory().addItem(spawner);
        sender.sendMessage("§aSpawner de " + mobTraduzido + " dado com sucesso para " + alvo.getName() + ".");

        return true;
    }
}