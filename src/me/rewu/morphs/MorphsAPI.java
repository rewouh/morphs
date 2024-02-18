package me.rewu.morphs;

import me.rewu.morphs.Data.DataType;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.Utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MorphsAPI {

    private static HashMap<MorphType, Morph> registeredMorphs = new HashMap<MorphType, Morph>();

    private static HashMap<Integer, ArrayList<MorphType>> rankedMorphs = new HashMap<>();

    public static Morph getMorphFromType(MorphType type) { return registeredMorphs.get(type); }

    public static Set<MorphType> getEveryMorphType() { return registeredMorphs.keySet(); }

    public static ArrayList<MorphType> getMorphTypesByRank(int rank) { return rankedMorphs.get(rank); }

    public static void registerMorphs() {
        for (int i=1; i<=3; i++)
            rankedMorphs.put(i, new ArrayList<>());

        for (MorphType type : MorphType.values()) {
            String mName = TextUtils.Nameify(type, true);

            try {
                Morph morph = (Morph) Class.forName("me.rewu.morphs.Morphs." + mName + "Morph").newInstance();

                registeredMorphs.put(type, morph);
                rankedMorphs.get(morph.getRank()).add(type);
            } catch (Exception e) {

            }
        }
    }

    public static boolean isPlayerMorphed(Player player) {
        return PlayerData.getPlayerData(DataType.CURRENT_MORPH, player.getUniqueId()) != null;
    }

    public static Morph getPlayerCurrentMorph(Player player) {
        return (Morph) PlayerData.getPlayerData(DataType.CURRENT_MORPH, player.getUniqueId());
    }

    public static int getPlayerNumberOfRolls(Player player) {
        return (int) PlayerData.getPlayerData(DataType.ROLLS, player.getUniqueId());
    }

    public static void setPlayerNumberOfRolls(Player player, int rolls) {
        PlayerData.setPlayerData(DataType.ROLLS, player.getUniqueId(), rolls);
    }

    public static float getPlayerLuck(Player player) {
        return (float) PlayerData.getPlayerData(DataType.LUCK, player.getUniqueId());
    }

    public static void setPlayerLuck(Player player, float luck) {
        PlayerData.setPlayerData(DataType.LUCK, player.getUniqueId(), luck);
    }

    public static ArrayList<MorphType> getPlayerOwnedMorphs(Player player) {
        return (ArrayList<MorphType>) PlayerData.getPlayerData(DataType.OWNED_MORPHS, player.getUniqueId());
    }

    public static void setPlayerOwnedMorphs(Player player, ArrayList<MorphType> ownedMorphs) {
        PlayerData.setPlayerData(DataType.OWNED_MORPHS, player.getUniqueId(), ownedMorphs);
    }

    public static void Morph(Player player, MorphType type) {
        getMorphFromType(type).Morph(player);
    }

    public static void Unmorph(Player player) {
        if (!isPlayerMorphed(player)) return;

        getPlayerCurrentMorph(player).Clear(player);
    }

}
