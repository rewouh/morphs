package me.rewu.morphs.Commands;

import me.rewu.morphs.Data.DataType;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.Utils.PlayerUtils;
import me.rewu.morphs.Utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GiveWithdrawCommand extends Command {

    String usage = "/m <give|withdraw> <optional:targets> <morph>";

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (args.length < 2 || args.length > 3) {
            sender.sendMessage("Usage: " + usage);

            return;
        }

        if (!sender.hasPermission("morphs.give")) {
            sender.sendMessage("You don't have permission to use this command.");

            return;
        }

        ArrayList<Player> targets = args.length == 2 ? CommandUtils.getPlayers(sender, "me") : CommandUtils.getPlayers(sender, args[1]);

        if (targets.size() == 0) {
            sender.sendMessage("You need to specify at least one valid target.");

            return;
        }

        ArrayList<MorphType> types = CommandUtils.getMorphs(args[args.length - 1]);

        if (types.size() == 0) {
            sender.sendMessage("You need to specify at least one valid type.");

            return;
        }

        boolean giving = args[0].startsWith("g");
        String changeChar = giving ? "§a+§f" : "§c-§f";

        HashMap<Player, String> changes = new HashMap<>();

        for (Player target : targets) {
            UUID uuid = target.getUniqueId();
            ArrayList<MorphType> morphs = (ArrayList<MorphType>) PlayerData.getPlayerData(DataType.OWNED_MORPHS, uuid);

            String targetChanges = "";

            for (MorphType type : types)
                if (giving ? PlayerUtils.giveMorph(target, type) : PlayerUtils.withdrawMorph(target, type))
                    targetChanges += changeChar + TextUtils.Nameify(type) + " ";

            if (targetChanges.length() > 0)
                changes.put(target, targetChanges);
        }

        if (changes.size() == 0) {
            sender.sendMessage("No changes.");

            return;
        }

        sender.sendMessage("Changes:");

        for (Player target : targets)
            sender.sendMessage( "\n" + target.getName() + " -> " + changes.get(target));

        targets.remove(sender);

        for (Player target : targets)
            target.sendMessage("Changes have been made to your morphs collection by " + sender.getName() + " : " + changes.get(target));
    }
}
