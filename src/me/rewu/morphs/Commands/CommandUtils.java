package me.rewu.morphs.Commands;

import me.rewu.morphs.Main;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.MorphsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandUtils {

    public static ArrayList<Player> getPlayers(CommandSender sender, String argument) {

        ArrayList<Player> targets = new ArrayList<Player>();

        for (String name : argument.split(",")) {

            switch (name) {
                case "me":
                    if (sender instanceof Player player)
                        targets.add(player);
                    continue;
                case "*":
                case "all":
                    targets.addAll(Bukkit.getOnlinePlayers());
                    continue;
                default:
                    Player target = Bukkit.getPlayer(name);

                    if (target != null)
                        targets.add(target);
            }
        }

        return targets;
    }

    public static ArrayList<MorphType> getMorphs(String argument) {

        ArrayList<MorphType> targets = new ArrayList<MorphType>();

        for (String name : argument.split(",")) {

            switch (name) {
                case "*":
                case "all":
                    targets.addAll(MorphsAPI.getEveryMorphType());
                    continue;
                default:
                    try { targets.add(MorphType.valueOf(name.toUpperCase())); } catch (Exception exception) { continue; }
            }
        }

        return targets;
    }
}
