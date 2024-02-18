package me.rewu.morphs.Abilities.Passives;

import me.rewu.morphs.Abilities.PassiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.function.Consumer;

public class AngelGraceAbility extends PassiveAbility {

    private EItemStack item = new EItemStack(Material.LEATHER_BOOTS).setName("Angel's Grace").setColor(Color.WHITE).setLore(new String[]{"&7Immune to fall damage."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>() {{
        put(MorphEvent.DAMAGE, e -> onDamage((EntityDamageEvent) e));
    }};

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    public void onDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
            event.setCancelled(true);
    }
}
