package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

public class AimbotAbility extends ActiveAbility {
    private EItemStack item = new EItemStack(Material.ARROW).setName("&cAimbot").setLore(new String[]{"&7Your bow has an aimbot for 15s."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>() {{
        put(MorphEvent.PROJECTILE_LAUNCH, e -> onProjectileLaunch((ProjectileLaunchEvent) e));
    }};

    private HashMap<Player, Boolean> playersBotting = new HashMap<>();

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
    public void Use(Player player) {
        playersBotting.put(player, true);

        scheduler.scheduleSyncDelayedTask(main, () -> Cancel(player), 20 * 15);
    }

    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Player player = (Player) event.getEntity().getShooter();

        if (!playersBotting.get(player))
            return;

        Location loc = player.getLocation();
        loc.add(loc.getDirection().multiply(10)); // 10 blocks forward

        Projectile projectile = event.getEntity();

        player.getWorld().getNearbyEntities(loc, 10, 5, 10, e -> e instanceof LivingEntity).stream().sorted((e1, e2) -> (int) (e1.getLocation().distanceSquared(loc) - e2.getLocation().distanceSquared(loc))).findFirst().ifPresent(e -> {
            projectile.setVelocity(((LivingEntity) e).getEyeLocation().toVector().subtract(projectile.getLocation().toVector()).normalize().multiply(2));
        });
    }

    @Override
    public void Add(Player player) {
        super.Add(player);

        playersBotting.put(player, false);
    }

    @Override
    public void Cancel(Player player) {
        playersBotting.put(player, false);
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);
        playersBotting.remove(player);
    }
}
