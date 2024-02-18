package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import me.rewu.morphs.Utils.WorldUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;

public class StalkAbility extends ActiveAbility {
    private EItemStack item = new EItemStack(Material.ENDER_EYE).setName("&0Stalk").setLore(new String[]{"&7If the targeted player is looking at the user", "&7when this ability is used, then engages stalk mode."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>() {{
        put(MorphEvent.ENTITY_DEATH, e -> onEntityDeath((EntityDeathEvent) e));
    }};

    private HashMap<Player, Object[]> playerStalking = new HashMap<>();
    private HashMap<LivingEntity, Player> entityStalked = new HashMap<>();

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
        playerStalking.put(player, new Object[]{ -1, null});
    }

    @Override
    public void Cancel(Player player) {
        int id = (int) playerStalking.get(player)[0];
        LivingEntity stalked = (LivingEntity) playerStalking.get(player)[1];

        if (id != -1) {
            scheduler.cancelTask(id);
            entityStalked.remove(stalked);

            playerStalking.put(player, new Object[]{ -1, null});
        }
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);

        playerStalking.remove(player);
    }

    @Override
    public void Use(Player player) {

        LivingEntity target = (LivingEntity) WorldUtils.getTargetEntity(player, e -> e instanceof LivingEntity);

        if (target == null)
            return;

        if (!WorldUtils.isInVision(player, target, 60))
            return;

        if (target instanceof Player targetPlayer)
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 1, 1);

        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            player.teleport(findRandomLocationAround(target));
        }, 0, 20L);

        playerStalking.put(player, new Object[]{id, target});
        entityStalked.put(target, player);

        scheduler.scheduleSyncDelayedTask(main, () -> Cancel(player), 20*10L);
    }

    private Location findRandomLocationAround(LivingEntity target) {
        Random rand = new Random();

        Location location = target.getLocation();

        Location loc = location.clone();
        loc.add(rand.nextDouble(-2, 2), 0, rand.nextDouble(-2, 2)); // Adding random offset

        Vector vec = location.toVector().subtract(loc.toVector()).normalize();
        loc.setDirection(vec);

        return loc;
    }

    private void onEntityDeath(EntityDeathEvent event) {
        Entity stalked = event.getEntity();

        if (!entityStalked.containsKey(stalked))
            return;

        Player stalker = entityStalked.get(stalked);

        Cancel(stalker);
    }
}
