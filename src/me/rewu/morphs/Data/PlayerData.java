package me.rewu.morphs.Data;

import me.rewu.morphs.Main;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.Utils.PlayerUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PlayerData {

    private static HashMap<DataType, HashMap<UUID, Object>> playersData = new HashMap<>()  {{
        put(DataType.CURRENT_MORPH, new HashMap<>());
        put(DataType.OWNED_MORPHS, new HashMap<>());
        put(DataType.LUCK, new HashMap<>());
        put(DataType.ROLLS, new HashMap<>());
    }};

    public static void setPlayerData(DataType type, UUID uuid, Object obj) {
        playersData.get(type).put(uuid, obj);
    }

    public static Object getPlayerData(DataType type, UUID uuid) {
        return playersData.get(type).get(uuid);
    }

    public static void addToPlayerData(DataType type, UUID uuid, Object obj) {
        HashMap<UUID, Object> data = playersData.get(type);
        Object d = data.get(uuid);

        if (d instanceof Integer) {
            data.put(uuid, (int) d + (int) obj);
        } else if (d instanceof Double) {
            data.put(uuid, (double) d + (double) obj);
        } else if (d instanceof ArrayList) {
            ((ArrayList) d).add(obj);
        }
    }

    public static void removeFromPlayerData(DataType type, UUID uuid, Object obj) {
        HashMap<UUID, Object> data = playersData.get(type);
        Object d = data.get(uuid);

        if (d instanceof Integer) {
            data.put(uuid, (int) d - (int) obj);
        } else if (d instanceof Double) {
            data.put(uuid, (double) - + (double) obj);
        } else if (d instanceof ArrayList) {
            ((ArrayList) d).remove(obj);
        }
    }

    public static boolean isLoaded(Player player) {
        return playersData.get(DataType.CURRENT_MORPH).containsKey(player.getUniqueId());
    }

    public static void Load(Player player) {
        UUID uuid = player.getUniqueId();

        if (isLoaded(player))
            return;

        setPlayerData(DataType.CURRENT_MORPH, uuid, null);
        setPlayerData(DataType.OWNED_MORPHS, uuid, new ArrayList<MorphType>());
        setPlayerData(DataType.LUCK, uuid, 0.0);
        setPlayerData(DataType.ROLLS, uuid, 0);
    }
}
