package me.rewu.morphs.Utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class EventsUtils {

    public static Player retrieveDamager(Entity entity) {

        if (entity instanceof Player player)
            return player;

        if (entity instanceof Projectile projectile)
            if (projectile.getShooter() instanceof Player player)
                return player;

        return null;
    }
}
