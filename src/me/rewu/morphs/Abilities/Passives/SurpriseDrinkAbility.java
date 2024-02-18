package me.rewu.morphs.Abilities.Passives;

import me.rewu.morphs.Abilities.PassiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.function.Consumer;

public class SurpriseDrinkAbility extends PassiveAbility {

    private EItemStack potionBase = new EItemStack(Material.POTION).setName("&dSurprise Drink");

    private EItemStack item = ((EItemStack) potionBase.clone()).setLore(new String[]{"&7You receive a (good) random potion now and then."}).addEnchant(Enchantment.OXYGEN, 1).hideEnchants();

    private PotionEffectType[] possibleTypes = new PotionEffectType[] {
        PotionEffectType.JUMP,
        PotionEffectType.SPEED,
        PotionEffectType.INCREASE_DAMAGE,
        PotionEffectType.FAST_DIGGING,
        PotionEffectType.FIRE_RESISTANCE,
        PotionEffectType.REGENERATION,
        PotionEffectType.DAMAGE_RESISTANCE,
        PotionEffectType.HEAL,
        PotionEffectType.ABSORPTION
    };

    private HashMap<Player, Integer> playersReceiving = new HashMap<>();

    private HashMap<MorphEvent, Consumer<Event>> callbacks = new HashMap<>() {{
        put(MorphEvent.ITEM_CONSUME, e -> onItemConsume((PlayerItemConsumeEvent) e));
    }};

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

        playersReceiving.put(player, scheduler.scheduleSyncRepeatingTask(main, () -> GiveRandomPotion(player), 20*10, 20*10));
    }

    @Override
    public void Cancel(Player player) {
        super.Cancel(player);

        scheduler.cancelTask(playersReceiving.get(player));
        playersReceiving.put(player, -1);
    }

    @Override
    public void Remove(Player player) {
        super.Remove(player);

        playersReceiving.remove(player);
    }

    private void GiveRandomPotion(Player player){
        PlayerInventory inv = player.getInventory();

        boolean hotbarFull = true;

        for (int i = 0; i < 9 && hotbarFull; i++)
            if (inv.getItem(i) == null)
                hotbarFull = false;

        if (hotbarFull)
            return;

        PotionEffectType randomType = possibleTypes[rand.nextInt(possibleTypes.length)];
        EItemStack potion = ((EItemStack) potionBase.clone()).addPotionEffect(new PotionEffect(randomType, rand.nextInt(5, 20)*20, rand.nextInt(0,5)));

        inv.addItem(potion);
    }

    private void onItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.POTION)
            return;

        Player player = event.getPlayer();

        scheduler.scheduleSyncDelayedTask(main, () -> {
            player.getInventory().setItem(event.getHand(), null);
        }, 1L);
    }
}
