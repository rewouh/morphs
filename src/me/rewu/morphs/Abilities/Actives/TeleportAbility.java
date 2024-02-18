package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.function.Consumer;

public class TeleportAbility extends ActiveAbility {

    private EItemStack item = new EItemStack(Material.ENDER_PEARL).setName("&5Teleport").setLore(new String[]{"&7Teleports to the targeted location."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>();;

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public Integer getCooldown() {
        return 10;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    @Override
    public void Use(Player player) {
        Block b = player.getTargetBlockExact(50);

        if (b == null)
            return;

        Location playerLoc = player.getLocation();
        Location targetLoc = b.getLocation().add(0, 1, 0);

        targetLoc.setYaw(playerLoc.getYaw());
        targetLoc.setPitch(playerLoc.getPitch());

        player.teleport(targetLoc);
    }
}
