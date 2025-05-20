package br.com.ttnotblunt.bluntSpawners.commands;

import br.com.ttnotblunt.bluntSpawners.utils.SpawnerItemBuilder;
import br.com.ttnotblunt.bluntSpawners.utils.SpawnerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

        String mob = args[1].toUpperCase();

        if (!SpawnerManager.ehMobValido(mob)) {
            sender.sendMessage("§cMob inválido. Verifique a confg.yml.");
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
