package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import me.rewu.morphs.Utils.WorldUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.function.Consumer;

public class BackstabAbility extends ActiveAbility {
    private EItemStack item = new EItemStack(Material.DRIED_KELP).setName("&8Backstab").setLore(new String[]{"&7If aimed at the back of a player,", "&7teleports the user there."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>();;

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public Integer getCooldown() {
        return 15;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    @Override
    public void Use(Player player) {

        LivingEntity target = (LivingEntity) WorldUtils.getTargetEntity(player, e -> e instanceof LivingEntity);

        if (target == null)
            return;

        if (WorldUtils.isInVision(player, target, 90))
            return;

        Location targetLoc = target.getLocation();

        targetLoc.add(targetLoc.getDirection().multiply(-1.5));

        player.teleport(targetLoc);
    }
}
