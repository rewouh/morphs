package me.rewu.morphs.Morphs;

import com.google.common.collect.ObjectArrays;
import me.rewu.morphs.Abilities.Abilities;
import me.rewu.morphs.Abilities.Ability;
import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Abilities.PassiveAbility;
import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Morphs.MorphType;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class IronGolemMorph extends Morph {

    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.LEATHER_BOOTS).setName("Iron Golem Boots").setColor(Color.WHITE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            new EItemStack(Material.LEATHER_LEGGINGS).setName("Iron Golem Leggings").setColor(Color.WHITE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            new EItemStack(Material.LEATHER_CHESTPLATE).setName("&Iron Golem Chestplate").setColor(Color.WHITE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            new EItemStack(Material.PLAYER_HEAD).setName("Iron Golem Head").setHead("NoHitCombo")
    };

    private ItemStack mainWeapon = new EItemStack(Material.WOODEN_AXE).setName("Iron Fist");

    private ItemStack secondaryWeapon = null;

    private ItemStack[] primaryItems = {
    };

    private ItemStack[] secondaryItems = {
    };

    private PassiveAbility[] passives = new PassiveAbility[] {};

    private ActiveAbility[] actives = new ActiveAbility[] {};

    private Ability[] abilities = ObjectArrays.concat(passives, actives, Ability.class);

    //region Getters
    @Override
    public MorphType getType() {
        return MorphType.IRON_GOLEM;
    }

    @Override
    public int getRank() {
        return 1;
    }

    @Override
    public float getSpeed() {
        return 0.22f;
    }

    @Override
    public float getHealth() {
        return 100f;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GRAY;
    }

    @Override
    public boolean canJump() {
        return false;
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


    @Override
    public void Morph(Player player) {
        super.Morph(player);

        PlayerUtils.changeKnockbackResistance(player, 1f);
    }

    @Override
    public void Clear(Player player) {
        super.Clear(player);

        PlayerUtils.changeKnockbackResistance(player, 0f);
    }
}
