package me.rewu.morphs.Abilities.Passives;

import me.rewu.morphs.Abilities.PassiveAbility;
import me.rewu.morphs.Abilities.Utils.SlimeSize;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import me.rewu.morphs.Utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.function.Consumer;

public class MitosisAbility extends PassiveAbility {

    private EItemStack item = new EItemStack(Material.SLIME_BALL).setName("&aMitosis").setLore(new String[]{"&7Revives with altered stats (2 times max)."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>() {{
        put(MorphEvent.DAMAGE, e -> onDamage((EntityDamageEvent) e));
    }};

    private HashMap<Player, SlimeSize> playersSize = new HashMap<>();

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    @Override
    public void Add(Player player) {
        super.Add(player);
        playersSize.put(player, SlimeSize.BIG);
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);
        playersSize.remove(player);
    }

    public void onDamage(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();

        if (player.getHealth() - event.getFinalDamage() > 0)
            return;

        SlimeSize size = playersSize.get(player);

        if (size == SlimeSize.SMALL)
            return;

        event.setCancelled(true);

        double currHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        PlayerUtils.changeHealth(player, (float) currHealth / 4);

        int currJump = player.getPotionEffect(PotionEffectType.JUMP).getAmplifier();

        player.removePotionEffect(PotionEffectType.JUMP);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 9999999, currJump / 2, false, false));

        Location loc = player.getLocation();
        World w = loc.getWorld();

        w.spawnParticle(Particle.SLIME, loc, 20, 0.5, 0.5, 0.5, 0.1);
        w.playSound(loc, Sound.ENTITY_SLIME_DEATH, 1, 1);

        playersSize.put(player, size == SlimeSize.BIG ? SlimeSize.MEDIUM : SlimeSize.SMALL);
    }
}
