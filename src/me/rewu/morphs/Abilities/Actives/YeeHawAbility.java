package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.HashMap;
import java.util.function.Consumer;

public class YeeHawAbility extends ActiveAbility {

    private EItemStack item = new EItemStack(Material.SADDLE).setName("&eYeeee Haw!!").setLore(new String[]{"&7Mount your horse for 5 seconds."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>() {{
        put(MorphEvent.DISMOUNT, e -> onDismount((EntityDismountEvent) e));
    }};

    private HashMap<Player, Horse> playersHorses = new HashMap<>();

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
        playersHorses.put(player, null);
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);
        playersHorses.remove(player);
    }

    @Override
    public void Use(Player player) {
        Location location = player.getLocation();

        Horse horse = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);

        horse.setTamed(true);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

        horse.addPassenger(player);

        playersHorses.put(player, horse);

        scheduler.scheduleSyncDelayedTask(main, () -> Dismount(player), 20 * 5);
    }

    private void Dismount(Player player) {
        Horse horse = playersHorses.get(player);

        if (horse != null)
            horse.remove();

        playersHorses.remove(player);
    }

    public void onDismount(EntityDismountEvent event) {
        if (event.getDismounted() instanceof Horse horse)
            Dismount((Player) event.getEntity());
    }

    @Override
    public void Cancel(Player player) {
        Dismount(player);
    }
}
