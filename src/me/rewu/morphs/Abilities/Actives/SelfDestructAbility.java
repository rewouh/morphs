package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

public class SelfDestructAbility extends ActiveAbility {
    private EItemStack item = new EItemStack(Material.GUNPOWDER).setName("&cSelf Destruct").setLore(new String[]{"&7You explode after 2 seconds.", "&7Can be cancelled by re-activating."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>();

    private HashMap<Player, Integer> playersExploding = new HashMap<>();

    private PotionEffect[] potionEffects = new PotionEffect[] {
            new PotionEffect(PotionEffectType.SLOW, 2*20, 9, false, false),
            new PotionEffect(PotionEffectType.GLOWING, 2*20, 0, false, false)
    };

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public Integer getCooldown() {
        return 1;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    @Override
    public void Add(Player player) {
        super.Add(player);
        playersExploding.put(player, -1);
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);
        playersExploding.remove(player);
    }

    @Override
    public void Use(Player player) {

        if (playersExploding.get(player) != -1) {
            Cancel(player);

            return;
        }

        for (PotionEffect potionEffect : potionEffects)
            player.addPotionEffect(potionEffect);

        World world = player.getLocation().getWorld();

        world.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1, 1);

        playersExploding.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            world.createExplosion(player.getLocation(), 5, false, false, player);

            player.damage(1000, player);
        }, 20*2));
    }

    @Override
    public void Cancel(Player player) {
        for (PotionEffect potionEffect : potionEffects)
            player.removePotionEffect(potionEffect.getType());

        scheduler.cancelTask(playersExploding.get(player));
        playersExploding.put(player, -1);
    }
}
