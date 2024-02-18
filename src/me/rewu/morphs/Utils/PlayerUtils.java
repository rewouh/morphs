package me.rewu.morphs.Utils;

import me.rewu.morphs.Data.DataType;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Main;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.MorphsAPI;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.Map.Entry;

public class PlayerUtils {

    public static void changeHealth(Player player, float newHealth) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newHealth);
        player.setHealthScale(newHealth);

        if (!player.isDead())
            player.setHealth(newHealth);
    }

    public static void changeKnockbackResistance(Player player, float newResistance) {
        player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(newResistance);
    }

    public static boolean giveMorph(Player player, MorphType type) {
        ArrayList<MorphType> morphs = (ArrayList<MorphType>) PlayerData.getPlayerData(DataType.OWNED_MORPHS, player.getUniqueId());

        if (morphs.contains(type))
            return false;

        morphs.add(type);

        return true;
    }

    public static boolean withdrawMorph(Player player, MorphType type) {
        ArrayList<MorphType> morphs = (ArrayList<MorphType>) PlayerData.getPlayerData(DataType.OWNED_MORPHS, player.getUniqueId());

        if (!morphs.contains(type))
            return false;

        morphs.remove(type);

        return true;
    }

    public static void rollMorph(Player player) {
        UUID uuid = player.getUniqueId();

        ArrayList<EntityType> morphs = (ArrayList<EntityType>) PlayerData.getPlayerData(DataType.OWNED_MORPHS, uuid);
        double luck = (double) PlayerData.getPlayerData(DataType.LUCK, uuid);

        Random rand = new Random();

        // 1/5 to get a rank 2, 1/20 to get a rank 3
        int rank = rand.nextDouble() < (0.025 + luck) ? (rand.nextDouble() < (0.1 + luck) ? 3 : 2) : 1;

        ArrayList<MorphType> rankMorphs;

        while ((rankMorphs = MorphsAPI.getMorphTypesByRank(rank)).size() == 0)
            rank--;

        MorphType rolledType = rankMorphs.get(rand.nextInt(rankMorphs.size()));

        String rankText = (rank == 1 ? "§e*" : rank == 2 ? "§6**" : "§5***") + "§f";

        if (giveMorph(player, rolledType)) {
            if (luck != 0)
                PlayerData.setPlayerData(DataType.LUCK, uuid, 0.0);

            for (Player p : Bukkit.getOnlinePlayers())
                p.sendMessage(p == player ? "You rolled " + TextUtils.Nameify(rolledType) + " (" + rankText + ") !" : player.getName() + " rolled " + TextUtils.Nameify(rolledType) + " (" + rankText + ") !");
        } else {
            PlayerData.setPlayerData(DataType.LUCK, uuid, luck == 0.5 ? luck : luck + 0.01);

            player.sendMessage("You rolled a duplicata (" + TextUtils.Nameify(rolledType) + "), your luck will be increased on next roll.");
        }
    }
}
