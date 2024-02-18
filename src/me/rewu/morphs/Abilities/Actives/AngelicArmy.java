package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Events;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import me.rewu.morphs.Utils.WorldUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.function.Consumer;

public class AngelicArmy extends ActiveAbility {
    private EItemStack item = new EItemStack(Material.VEX_SPAWN_EGG).setName("&7Angelic Army").setLore(new String[]{"&7Summons three vexes aiming for", "&7the targetted player."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>() {{
        put(MorphEvent.ENTITY_TARGET, e -> onEntityTarget((EntityTargetEvent) e));
    }};

    private HashMap<Player, Object[]> playersData = new HashMap<>();

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public Integer getCooldown() {
        return 20;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    @Override
    public void Add(Player player) {
        super.Add(player);
        playersData.put(player, new Object[] { null, null, null});
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);
        playersData.remove(player);
    }

    @Override
    public void Use(Player player) {

        LivingEntity target = (LivingEntity) WorldUtils.getTargetEntity(player, e -> e instanceof LivingEntity);

        if (target == null)
            return;

        World world = player.getLocation().getWorld();

        Object[] data = new Object[3];

        for (int i=0; i<3; i++){
            Vex vex = (Vex) world.spawnEntity(player.getLocation(), EntityType.VEX);
            vex.setTarget(target);

            vex.setMetadata("morphs_summon", new FixedMetadataValue(main, player));

            data[i] = vex;
        }

        playersData.put(player, data);

        scheduler.scheduleSyncDelayedTask(main, () -> Cancel(player), 20 * 10);
    }

    @Override
    public void Cancel(Player player) {
        Object[] data = playersData.get(player);

        if (data[0] == null)
            return;

        for (int i=0; i<3; i++) {
            Vex vex = (Vex) data[i];

            vex.remove();

            data[i] = null;
        }
    }

    public void onEntityTarget(EntityTargetEvent event) {

        if (!(event.getEntity() instanceof Vex vex) || !vex.hasMetadata("morphs_summon"))
            return;

        Player player = (Player) vex.getMetadata("morphs_summon").get(0).value();

        if (playersData.containsKey(player))
            Cancel(player);
    }
}
