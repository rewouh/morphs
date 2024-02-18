package me.rewu.morphs.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Predicate;

public class WorldUtils {

    public static Entity getTargetEntity(Entity source, @Nullable Predicate<Entity> filter) {

        Location loc = (source instanceof LivingEntity livingEntity) ? livingEntity.getEyeLocation() : source.getLocation();
        Vector direction = loc.getDirection();

        loc.add(direction.multiply(2));

        RayTraceResult result = source.getWorld().rayTraceEntities(loc, direction, 50, filter);

        if (result == null)
            return null;

        return result.getHitEntity();
    }

    public static Collection<Entity> getEntitiesInFrontOf(Entity source) {

        Location location = source.getLocation();

        location.add(location.getDirection().multiply(10));

        return source.getWorld().getNearbyEntities(location, 10, 10, 10);
    }

    public static boolean isInVision(LivingEntity source, LivingEntity target, int angle) {
        Vector dir = source.getEyeLocation().toVector().subtract(target.getEyeLocation().toVector()).normalize();
        double dot = dir.dot(target.getLocation().getDirection());

        return dot >= Math.cos(Math.toRadians(angle));
    }
}
