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
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class ZombieMorph extends Morph {

    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.LEATHER_BOOTS).setName("&2Zombie Boots").setColor(Color.GREEN),
            new EItemStack(Material.LEATHER_LEGGINGS).setName("&2Zombie Leggings").setColor(Color.GREEN),
            new EItemStack(Material.LEATHER_CHESTPLATE).setName("&2Zombie Chestplate").setColor(Color.GREEN),
            new EItemStack(Material.ZOMBIE_HEAD).setName("&2Zombie Head")
    };

    private ItemStack mainWeapon = new EItemStack(Material.WOODEN_SWORD).setName("&2Zombie Hand");

    private ItemStack secondaryWeapon = null;

    private ItemStack[] primaryItems = {
    };

    private ItemStack[] secondaryItems = {
    };

    private PassiveAbility[] passives = new PassiveAbility[] {};

    private ActiveAbility[] actives = new ActiveAbility[] {
            Abilities.FRESH_BLOOD
    };

    private Ability[] abilities = ObjectArrays.concat(passives, actives, Ability.class);

    //region Getters
    @Override
    public MorphType getType() {
        return MorphType.ZOMBIE;
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
        return ChatColor.DARK_GREEN;
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
