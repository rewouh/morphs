package me.rewu.morphs.Commands;

import me.rewu.morphs.Data.DataType;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.Utils.PlayerUtils;
import me.rewu.morphs.Utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class RollCommand extends Command {
    String usage = "/m roll <optional:targets>";

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (args.length > 2) {
            sender.sendMessage("Usage: " + usage);

            return;
        }

        if (!sender.hasPermission("morphs.roll") || (args.length == 2 && !sender.hasPermission("morphs.roll.others"))) {
            sender.sendMessage("You don't have the permission to do this.");

            return;
        }

        ArrayList<Player> targets = args.length == 1 ? CommandUtils.getPlayers(sender, "me") : CommandUtils.getPlayers(sender, args[1]);

        if (targets.size() == 0) {
            sender.sendMessage("You need to specify at least one valid target.");

            return;
        }

        if (!sender.hasPermission("morphs.roll.unlimited")) {
            Player player = (Player) sender;

            int rolls = (int) PlayerData.getPlayerData(DataType.ROLLS, player.getUniqueId());

            if (rolls < targets.size()) {
                sender.sendMessage("You don't have enough rolls to do this.");

                return;
            }

            PlayerData.removeFromPlayerData(DataType.ROLLS, player.getUniqueId(), targets.size());
        }

        for (Player target : targets) {
            if (target != sender)
                target.sendMessage(sender.getName() + " rolled for you!");

            PlayerUtils.rollMorph(target);
        }
    }
}
