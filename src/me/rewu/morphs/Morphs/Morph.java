package me.rewu.morphs.Morphs;

import me.rewu.morphs.Abilities.Ability;
import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Abilities.PassiveAbility;
import me.rewu.morphs.Data.DataType;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class Morph {

    protected World world = Bukkit.getWorld("world");

    public abstract MorphType getType();

    public abstract int getRank();

    public abstract float getSpeed();

    public abstract float getHealth();

    public abstract boolean canJump();

    public abstract boolean canRun();

    public abstract boolean canRegen();

    public abstract ChatColor getColor();

    public abstract ItemStack[] getArmor();

    public abstract ItemStack getMainWeapon();

    public abstract ItemStack getSecondaryWeapon();

    public abstract ItemStack[] getPrimaryItems();

    public abstract ItemStack[] getSecondaryItems();

    public abstract PassiveAbility[] getPassiveAbilities();

    public abstract ActiveAbility[] getActiveAbilities();

    public abstract Ability[] getEveryAbilities();

    public void Init() {
    }

    public void Morph(Player player) {
        PlayerInventory inventory = player.getInventory();

        // Providing items & abilities
        inventory.clear();

        inventory.setArmorContents(getArmor());

        if (getMainWeapon() != null)
            inventory.setItem(0, getMainWeapon());

        inventory.setItemInOffHand(getSecondaryWeapon());

        int activeSlot = 1;
        int passiveSlot = 17;

        for (PassiveAbility ability : getPassiveAbilities()) {
            ability.Add(player);

            inventory.setItem(passiveSlot, ability.getItem());
            passiveSlot--;
        }

        for (ActiveAbility ability : getActiveAbilities()) {
            ability.Add(player);

            inventory.setItem(activeSlot, ability.getItem());
            activeSlot++;
        }

        ItemStack[] primary = getPrimaryItems();

        for (int i = 0; i < primary.length; i++)
            inventory.setItem(i+5, primary[i]);

        ItemStack[] secondary = getSecondaryItems();

        for (int i = 0; i < secondary.length; i++)
            inventory.setItem(i+9, secondary[i]);

        // Setting stats
        player.setWalkSpeed(getSpeed());
        PlayerUtils.changeHealth(player, getHealth());

        if (!canJump())
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 100000, false, false));

        player.setFoodLevel(canRun() ? (canRegen() ? 20 : 18) : 2);

        PlayerData.setPlayerData(DataType.CURRENT_MORPH, player.getUniqueId(), this);
    }

    public void Clear(Player player) {
        PlayerInventory inventory = player.getInventory();

        // Cancelling every ability
        for (Ability ability : getPassiveAbilities())
            ability.Remove(player);

        for (Ability ability : getActiveAbilities()) {
            ability.Remove(player);
            player.setCooldown(ability.getItem().getType(), 0);
        }

        // Clearing inventory
        inventory.clear();

        // Clearing potion effects
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        // Resetting player stats
        player.setWalkSpeed(0.2f);
        PlayerUtils.changeHealth(player, 20);
        player.setFoodLevel(20);

        PlayerData.setPlayerData(DataType.CURRENT_MORPH, player.getUniqueId(), null);
    }
}

