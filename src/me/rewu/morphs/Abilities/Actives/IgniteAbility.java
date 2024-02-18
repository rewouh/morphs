package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.function.Consumer;

public class IgniteAbility extends ActiveAbility {

    private EItemStack item = new EItemStack(Material.BLAZE_POWDER).setName("&6Ignite").setLore(new String[]{"&7You ignite for 3 seconds", "&7and launch 3 fireballs afterwards."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>();

    private HashMap<Player, Integer> playersIgniting = new HashMap<>();

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public Integer getCooldown() {
        return 8;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    @Override
    public void Add(Player player) {
        super.Add(player);
        playersIgniting.put(player, -1);
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);
        playersIgniting.remove(player);
    }

    @Override
    public void Use(Player player) {

        player.setVisualFire(true);

        World world = player.getWorld();

        world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_BURN, 1, 1);

        playersIgniting.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            player.setVisualFire(false);

            playersIgniting.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
                player.launchProjectile(Fireball.class, player.getLocation().getDirection().multiply(2));
                world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
            }, 0, 6));

            scheduler.scheduleSyncDelayedTask(main, () -> Cancel(player), 15);
        }, 20*3));
    }

    @Override
    public void Cancel(Player player) {
        scheduler.cancelTask(playersIgniting.get(player));
        playersIgniting.put(player, -1);
    }
}
