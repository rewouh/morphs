package me.rewu.morphs.Morphs;

import com.google.common.collect.ObjectArrays;
import me.rewu.morphs.Abilities.Abilities;
import me.rewu.morphs.Abilities.Ability;
import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Abilities.PassiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EndermanMorph extends Morph {

    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.LEATHER_BOOTS).setName("&0Enderman Boots").setColor(Color.BLACK),
            new EItemStack(Material.LEATHER_LEGGINGS).setName("&0Enderman Leggings").setColor(Color.BLACK),
            new EItemStack(Material.LEATHER_CHESTPLATE).setName("&0Enderman Chestplate").setColor(Color.BLACK),
            new EItemStack(Material.PLAYER_HEAD).setName("&0Enderman Head").setHead("TheOnlyRulers")
    };

    private ItemStack mainWeapon = new EItemStack(Material.IRON_SWORD).setName("&0Ender Hand").addEnchant(Enchantment.DAMAGE_ALL, 1);

    private ItemStack secondaryWeapon = null;

    private ItemStack[] primaryItems = {};

    private ItemStack[] secondaryItems = {};

    private PassiveAbility[] passives = new PassiveAbility[] {};

    private ActiveAbility[] actives = new ActiveAbility[] {
            Abilities.TELEPORT,
            Abilities.STALK
    };

    private Ability[] abilities = ObjectArrays.concat(passives, actives, Ability.class);

    //region Getters
    @Override
    public MorphType getType() {
        return MorphType.ENDERMAN;
    }

    @Override
    public int getRank() {
        return 2;
    }

    @Override
    public float getSpeed() {
        return 0.25f;
    }

    @Override
    public float getHealth() {
        return 40f;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.BLACK;
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
