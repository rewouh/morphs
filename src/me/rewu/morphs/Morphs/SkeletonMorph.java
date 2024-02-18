package me.rewu.morphs.Morphs;

import com.google.common.collect.ObjectArrays;
import me.rewu.morphs.Abilities.Abilities;
import me.rewu.morphs.Abilities.Ability;
import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Abilities.PassiveAbility;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.Utils.EItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class SkeletonMorph extends Morph {

    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.CHAINMAIL_BOOTS).setName("&7Skeleton Boots"),
            new EItemStack(Material.CHAINMAIL_LEGGINGS).setName("&7Skeleton Leggings"),
            new EItemStack(Material.CHAINMAIL_CHESTPLATE).setName("&7Skeleton Chestplate"),
            new EItemStack(Material.SKELETON_SKULL).setName("&7Skeleton Head")
    };

    private ItemStack mainWeapon = new EItemStack(Material.BOW).setName("&7Skeleton Bow").addEnchant(Enchantment.ARROW_INFINITE, 1);

    private ItemStack secondaryWeapon = null;

    private ItemStack[] primaryItems = {
    };

    private ItemStack[] secondaryItems = {
    };

    private PassiveAbility[] passives = new PassiveAbility[] {};

    private ActiveAbility[] actives = new ActiveAbility[] {
            Abilities.AIMBOT
    };

    private Ability[] abilities = ObjectArrays.concat(passives, actives, Ability.class);

    //region Getters
    @Override
    public MorphType getType() {
        return MorphType.SKELETON;
    }

    @Override
    public int getRank() {
        return 1;
    }

    @Override
    public float getSpeed() {
        return 0.23f;
    }

    @Override
    public float getHealth() {
        return 20f;
    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public boolean canRun() {
        return true;
    }

    @Override
    public boolean canRegen() {
        return true;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_GRAY;
    }

    @Override
    public ItemStack[] getArmor() {
        return armor;
    }

    @Override
    public ItemStack getMainWeapon() {
        return mainWeapon;
    }

    @Override
    public ItemStack getSecondaryWeapon() {
        return secondaryWeapon;
    }

    @Override
    public ItemStack[] getPrimaryItems() {
        return primaryItems;
    }

    @Override
    public ItemStack[] getSecondaryItems() {
        return secondaryItems;
    }

    @Override
    public PassiveAbility[] getPassiveAbilities() {
        return passives;
    }

    @Override
    public ActiveAbility[] getActiveAbilities() {
        return actives;
    }

    @Override
    public Ability[] getEveryAbilities() {
        return abilities;
    }
    //endregion
}
