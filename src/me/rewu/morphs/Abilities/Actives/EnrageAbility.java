package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

public class EnrageAbility extends ActiveAbility {
    private EItemStack item = new EItemStack(Material.BONE).setName("&7Enrage").setLore(new String[]{"&7Engages enraged mode for 15s."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>();

    private HashMap<Player, Integer> playersEnraged = new HashMap<>();

    private PotionEffect[] potionEffects = new PotionEffect[] {
            new PotionEffect(PotionEffectType.FAST_DIGGING, 15*20, 0, false, false),
            new PotionEffect(PotionEffectType.SPEED, 15*20, 0, false, false)
    };

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public Integer getCooldown() {
        return 30;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    @Override
    public void Add(Player player) {
        super.Add(player);
        playersEnraged.put(player, -1);
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);
        playersEnraged.remove(player);
    }

    @Override
    public void Use(Player player) {

        for (PotionEffect potionEffect : potionEffects)
            player.addPotionEffect(potionEffect);

        World world = player.getLocation().getWorld();

        playersEnraged.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Location eyeLocation = player.getEyeLocation();
            eyeLocation.add(eyeLocation.getDirection().multiply(0.5));
            eyeLocation.add(0, 0.1, 0);

            Location leftEyeLocation = eyeLocation.clone().add(eyeLocation.getDirection().rotateAroundY(Math.PI / 2).multiply(0.2));
            Location rightEyeLocation = eyeLocation.clone().add(eyeLocation.getDirection().rotateAroundY(-Math.PI / 2).multiply(0.2));

            world.spawnParticle(Particle.REDSTONE, leftEyeLocation, 5, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
            world.spawnParticle(Particle.REDSTONE, rightEyeLocation, 5, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
        }, 0, 5));

        scheduler.scheduleSyncDelayedTask(main, () -> Cancel(player), 20 * 15);
    }

    @Override
    public void Cancel(Player player) {
        for (PotionEffect potionEffect : potionEffects)
            player.removePotionEffect(potionEffect.getType());

        scheduler.cancelTask(playersEnraged.get(player));
        playersEnraged.put(player, -1);
    }
}
