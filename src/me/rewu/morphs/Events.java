package me.rewu.morphs;
import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.CustomEvents.MorphedPlayerDeathEvent;
import me.rewu.morphs.Data.DataType;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.PlayerInventory;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class Events implements Listener {

    private static HashMap<MorphEvent, HashMap<Player, ArrayList<Consumer<Event>>>> eventsCallbacks = new HashMap<>() {{
        put(MorphEvent.BLOCK_PLACE, new HashMap<>());
        put(MorphEvent.DISMOUNT, new HashMap<>());
        put(MorphEvent.DAMAGE, new HashMap<>());
        put(MorphEvent.PROJECTILE_LAUNCH, new HashMap<>());
        put(MorphEvent.DEATH, new HashMap<>());
        put(MorphEvent.ENTITY_DEATH, new HashMap<>());
        put(MorphEvent.ITEM_CONSUME, new HashMap<>());
        put(MorphEvent.ENTITY_TARGET, new HashMap<>());
    }};

    public static void RegisterCallback(MorphEvent event, Player player, Consumer<Event> callback) {
        HashMap<Player, ArrayList<Consumer<Event>>> playersCallbacks = eventsCallbacks.get(event);

        if (!playersCallbacks.containsKey(player))
            playersCallbacks.put(player, new ArrayList<>());

        ArrayList<Consumer<Event>> callbacks = playersCallbacks.get(player);

        callbacks.add(callback);
    }

    public static void UnregisterCallback(MorphEvent event, Player player, Consumer<Event> callback) {
        HashMap<Player, ArrayList<Consumer<Event>>> playersCallbacks = eventsCallbacks.get(event);

        ArrayList<Consumer<Event>> callbacks = playersCallbacks.get(player);

        callbacks.remove(callback);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData.Load(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Morph morph = (Morph) PlayerData.getPlayerData(DataType.CURRENT_MORPH, player.getUniqueId());

        if (morph == null)
            return;

        morph.Clear(player);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();

        if (PlayerData.getPlayerData(DataType.CURRENT_MORPH, player.getUniqueId()) != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (PlayerData.getPlayerData(DataType.CURRENT_MORPH, player.getUniqueId()) != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (PlayerData.getPlayerData(DataType.CURRENT_MORPH, player.getUniqueId()) != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        Player player = event.getPlayer();

        if (PlayerData.getPlayerData(DataType.CURRENT_MORPH, player.getUniqueId()) != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID playerId = player.getUniqueId();

        Morph morph = (Morph) PlayerData.getPlayerData(DataType.CURRENT_MORPH, playerId);

        if (morph == null)
            return;

        // Clearing drops & exp
        event.getDrops().clear();
        event.setDroppedExp(0);

        morph.Clear(player);

        Bukkit.getPluginManager().callEvent(new MorphedPlayerDeathEvent(player, morph, event));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        Morph morph = (Morph) PlayerData.getPlayerData(DataType.CURRENT_MORPH, uuid);

        if (morph == null)
            return;

        PlayerInventory inventory = player.getInventory();

        int slot = inventory.getHeldItemSlot();

        ActiveAbility[] abilities = morph.getActiveAbilities();

        if (slot == 0 || slot > abilities.length)
            return;

        event.setCancelled(true);

        Material type = inventory.getItem(slot).getType();

        if (player.getCooldown(type) > 0) // Ability on cooldown
            return;

        ActiveAbility ability = abilities[slot-1];

        ability.Use(player);

        player.setCooldown(type, ability.getCooldown() * 20);
    }

    private void abilityListener(Object obj, MorphEvent eventType, Event event) {
        if (!(obj instanceof Player player))
            return;

        Morph morph = (Morph) PlayerData.getPlayerData(DataType.CURRENT_MORPH, player.getUniqueId());

        if (morph == null)
            return;

        HashMap<Player, ArrayList<Consumer<Event>>> playersCallbacks = eventsCallbacks.get(eventType);

        if (!playersCallbacks.containsKey(player))
            return;

        ArrayList<Consumer<Event>> callbacks = playersCallbacks.get(player);

        callbacks.forEach(callback -> callback.accept(event));
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        abilityListener(event.getPlayer(), MorphEvent.BLOCK_PLACE, event);
    }

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        abilityListener(event.getEntity(), MorphEvent.DISMOUNT, event);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        abilityListener(event.getEntity(), MorphEvent.DAMAGE, event);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        abilityListener(event.getEntity().getShooter(), MorphEvent.PROJECTILE_LAUNCH, event);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Bukkit.getOnlinePlayers().forEach(player -> abilityListener(player, MorphEvent.ENTITY_DEATH, event));
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event){
        abilityListener(event.getPlayer(), MorphEvent.ITEM_CONSUME, event);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        Bukkit.getOnlinePlayers().forEach(player -> abilityListener(player, MorphEvent.ENTITY_TARGET, event));
    }

}
