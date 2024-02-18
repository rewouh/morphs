package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SplashPotion;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;

public class SurpriseSplash extends ActiveAbility {

    private EItemStack item = new EItemStack(Material.SPLASH_POTION).setName("&dSurprise Splash").setLore(new String[]{"&7Throws a surprise (bad) splash potion."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private PotionEffectType[] possibleTypes = new PotionEffectType[] {
            PotionEffectType.SLOW,
            PotionEffectType.HARM,
            PotionEffectType.BLINDNESS,
            PotionEffectType.DARKNESS,
            PotionEffectType.POISON,
            PotionEffectType.WITHER,
            PotionEffectType.WEAKNESS,
            PotionEffectType.LEVITATION,
            PotionEffectType.CONFUSION,
            PotionEffectType.SLOW_DIGGING
    };

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
        ThrownPotion thrownPotion = player.launchProjectile(ThrownPotion.class);

        PotionEffectType randomType = possibleTypes[new Random().nextInt(possibleTypes.length)];

        EItemStack item = new EItemStack(Material.SPLASH_POTION).addPotionEffect(new PotionEffect(randomType, rand.nextInt(5, 20)*20, rand.nextInt(0,5)));

        thrownPotion.setItem(item);
    }
}
