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
import org.bukkit.inventory.ItemStack;

public class CreeperMorph extends Morph {

    private Color armorColor1 = Color.fromRGB(118, 194, 104);
    private Color armorColor2 = Color.fromRGB(78, 151, 74);

    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.LEATHER_BOOTS).setName("&aCreeper Boots").setColor(armorColor1),
            new EItemStack(Material.LEATHER_LEGGINGS).setName("&aCreeper Leggings").setColor(armorColor2),
            new EItemStack(Material.LEATHER_CHESTPLATE).setName("&aCreeper Chestplate").setColor(armorColor1),
            new EItemStack(Material.CREEPER_HEAD).setName("Creeper Head")
    };

    private ItemStack mainWeapon = null;

    private ItemStack secondaryWeapon = null;

    private ItemStack[] primaryItems = {};

    private ItemStack[] secondaryItems = {};

    private PassiveAbility[] passives = new PassiveAbility[] {};

    private ActiveAbility[] actives = new ActiveAbility[] {
            Abilities.SELF_DESTRUCT,
            Abilities.BACKSTAB
    };

    private Ability[] abilities = ObjectArrays.concat(passives, actives, Ability.class);

    //region Getters
    @Override
    public MorphType getType() {
        return MorphType.CREEPER;
    }

    @Override
    public int getRank() {
        return 1;
    }

    @Override
    public float getSpeed() {
        return 0.12f;
    }

    @Override
    public float getHealth() {
        return 20f;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public boolean canRun() {
        return false;
    }

    @Override
    public boolean canRegen() {
        return false;
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
