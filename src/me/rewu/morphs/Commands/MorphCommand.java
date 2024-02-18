package me.rewu.morphs.Commands;

import me.rewu.morphs.Data.DataType;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Main;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.MorphsAPI;
import me.rewu.morphs.Utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MorphCommand extends Command {

    String usage = "/m <optional:targets> <entity>";

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("morphs.morph")) {
            sender.sendMessage("You don't have permission to use this command.");

            return;
        }

        if (args.length > 2) {
            sender.sendMessage("Usage: " + usage);

            return;
        }

        ArrayList<Player> targets = args.length == 1 ? CommandUtils.getPlayers(sender, "me") : CommandUtils.getPlayers(sender, args[0]);

        if (targets.size() == 0) {
            sender.sendMessage("You need to specify at least one target.");

            return;
        }

        MorphType type;

        try {
            type = MorphType.valueOf(args[args.length - 1].toUpperCase());
        } catch (Exception exception) {
            sender.sendMessage("This entity doesn't exist.");

            return;
        }

        Morph morph = MorphsAPI.getMorphFromType(type);

        if (morph == null) {
            sender.sendMessage("No morph exists for this entity (yet).");

            return;
        }

        String morphName = TextUtils.Nameify(morph);;

        String affected = "";

        for (Player target : targets) {
            Morph previous = (Morph) PlayerData.getPlayerData(DataType.CURRENT_MORPH, target.getUniqueId());

            if (previous != null)
                previous.Clear(target);

            morph.Morph(target);

            affected += target.getName() + " ";

            if (sender != target)
                target.sendMessage("You've been been morphed as a " + morphName + " by " + sender.getName() + "!");
        }

        sender.sendMessage("The following players have been morphed to " + morphName + " :\n" + affected);
    }
}
