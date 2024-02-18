package me.rewu.morphs.Commands;

import me.rewu.morphs.Data.DataType;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.Utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class StatsCommand extends Command {

    String usage = "/m stats";

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (args.length != 1) {
            sender.sendMessage("Usage: " + usage);

            return;
        }

        if (!sender.hasPermission("morphs.stats")) {
            sender.sendMessage("You don't have permission to use this command.");

            return;
        }

        String stats = "§aStatistics:§f\n";

        for (Player target : Bukkit.getOnlinePlayers()) {
            UUID uuid = target.getUniqueId();

            stats += "\n§6[" + target.getName() + "]§f\n";

            stats += "- Current morph: " + TextUtils.Nameify((Morph) PlayerData.getPlayerData(DataType.CURRENT_MORPH, uuid)) + "\n";

            ArrayList<MorphType> morphs = (ArrayList<MorphType>) PlayerData.getPlayerData(DataType.OWNED_MORPHS, uuid);

            stats += "- Owned morphs (" + morphs.size() + "): §7";

            for (MorphType type : morphs)
                stats += " " + TextUtils.Nameify(type);
        }

        sender.sendMessage(stats);
    }
}
