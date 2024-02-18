package me.rewu.morphs.Commands;

import me.rewu.morphs.Data.DataType;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Morphs.Morph;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ClearCommand extends Command {

    private String usage = "/m clear <optional:targets>";

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (args.length > 2) {
            sender.sendMessage("Usage: " + usage);

            return;
        }

        if (!sender.hasPermission("morphs.clear") || (args.length == 2 && !sender.hasPermission("morphs.clear.others"))) {
            sender.sendMessage("You don't have the permission to do this.");

            return;
        }

        ArrayList<Player> targets = args.length == 1 ? CommandUtils.getPlayers(sender, "me") : CommandUtils.getPlayers(sender, args[1]);

        if (targets.size() == 0) {
            sender.sendMessage("You need to specify at least one target.");

            return;
        }

        String affected = "";

        for (Player target : targets) {

            Morph morph = (Morph) PlayerData.getPlayerData(DataType.CURRENT_MORPH, target.getUniqueId());

            if (morph == null)
                continue;

            morph.Clear(target);

            affected += target.getName() + " ";

            if (target != sender)
                target.sendMessage("Your morph has been cleared by " + sender.getName() + ".");
        }

        sender.sendMessage(affected.isEmpty() ? "Those players are not morphed."  : "You cleared the morphs of:\n" + affected);
    }
}
