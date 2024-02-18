package me.rewu.morphs.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerOnlyCommand extends Command {
    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");

            return;
        }

        Execute(player, args);
    }

    public abstract void Execute(Player player, String[] args);
}
