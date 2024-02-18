package me.rewu.morphs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Random;

public class Arena {

    private static World world = Bukkit.getWorld("world");

    private static Location[] spawns = {
        new Location(world, 0, 0, 0),
        new Location(world, -33, 0, -1),
        new Location(world, 33, 0, 1),
        new Location(world, -1, 0, 33),
        new Location(world, 1, 0, -33),
    };

    public static void sendToArena(Player player) {
        Random rand = new Random();

        player.teleport(spawns[rand.nextInt(spawns.length)]);
    }
}
