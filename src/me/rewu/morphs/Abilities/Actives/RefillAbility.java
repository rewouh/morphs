package me.rewu.morphs.Abilities.Actives;

import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.security.auth.callback.Callback;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class RefillAbility extends ActiveAbility {
    private EItemStack item = new EItemStack(Material.GOAT_HORN).setName("&eRefill").setLore(new String[]{"&7Refills arrows and blocks."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>() {{
        put(MorphEvent.BLOCK_PLACE, e -> onBlockPlace((BlockPlaceEvent) e));
    }};

    private HashMap<Player, HashMap<Block, Material>> playersTemporaryBlocks = new HashMap<>();

    @Override
    public EItemStack getItem() {
        return item;
    }

    @Override
    public Integer getCooldown() {
        return 60;
    }

    @Override
    public HashMap<MorphEvent, Consumer<Event>> getCallbacks() {
        return callbacks;
    }

    @Override
    public void Add(Player player) {
        super.Add(player);

        playersTemporaryBlocks.put(player, new HashMap<>());
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);
        playersTemporaryBlocks.remove(player);
    }

    @Override
    public void Use(Player player) {
        PlayerInventory inventory = player.getInventory();

        inventory.setItem(9, new ItemStack(Material.ARROW, 16));
        inventory.setItem(6, new ItemStack(Material.OAK_PLANKS, 12));
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        Block b = event.getBlock();
        Material type = event.getBlockReplacedState().getType();

        HashMap<Block, Material> playerTempBlocks =  playersTemporaryBlocks.get(player);

        playerTempBlocks.put(b, type);

        scheduler.scheduleSyncDelayedTask(main, () -> {
            if (b.getType() == type)
                return;

            b.setType(type);
            playerTempBlocks.remove(b);
        }, 20 * 15);

        event.setCancelled(false);
    }

    @Override
    public void Cancel(Player player) {
        HashMap<Block, Material> playerTempBlocks = playersTemporaryBlocks.get(player);

        if (playerTempBlocks == null)
            return;

        playerTempBlocks.entrySet().forEach(entry -> {
            Block b = entry.getKey();
            Material type = entry.getValue();

            b.setType(type);
        });
    }
}
