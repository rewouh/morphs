package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.function.Consumer;

public class FreshBloodAbility extends ActiveAbility {

    private EItemStack item = new EItemStack(Material.PHANTOM_MEMBRANE).setName("&cFresh Blood").setLore(new String[]{"&7Reveals other players in a radius of 40 blocs for 5 seconds."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>();;

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public Integer getCooldown() {
        return 45;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    @Override
    public void Use(Player player) {

        Location loc = player.getLocation();

        PotionEffect glowingEffect = new PotionEffect(PotionEffectType.GLOWING, 5*20, 0, true, false);

        for (Player target : Bukkit.getOnlinePlayers()) {
            //if (p == player)
              //  continue;

            if (target.getLocation().distance(loc) > 40)
                continue;

            target.addPotionEffect(glowingEffect);
        }

        loc.getWorld().playSound(loc, Sound.ENTITY_ZOMBIE_INFECT, 1, 1);
    }
}
