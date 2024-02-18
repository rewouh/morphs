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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlazeMorph extends Morph {

    private Color armorColor1 = Color.fromRGB(184, 110, 0);
    private Color armorColor2 = Color.fromRGB(186, 156, 29);

    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.LEATHER_BOOTS).setName("&6Blaze Boots").setColor(armorColor1),
            new EItemStack(Material.LEATHER_LEGGINGS).setName("&6Blaze Leggings").setColor(armorColor2),
            new EItemStack(Material.LEATHER_CHESTPLATE).setName("&6Blaze Chestplate").setColor(armorColor1),
            new EItemStack(Material.PLAYER_HEAD).setName("6Blaze Head").setHead("MHF_Blaze")
    };

    private ItemStack mainWeapon = new EItemStack(Material.BLAZE_ROD).setName("&6Blaze Hand").addEnchant(Enchantment.KNOCKBACK, 2).addEnchant(Enchantment.DAMAGE_ALL, 7);

    private ItemStack secondaryWeapon = null;

    private ItemStack[] primaryItems = {};

    private ItemStack[] secondaryItems = {};

    private PassiveAbility[] passives = new PassiveAbility[] {};

    private ActiveAbility[] actives = new ActiveAbility[] {
            Abilities.IGNITE,
            Abilities.TAKE_OFF
    };

    private Ability[] abilities = ObjectArrays.concat(passives, actives, Ability.class);

    //region Getters
    @Override
    public MorphType getType() {
        return MorphType.BLAZE;
    }

    @Override
    public int getRank() {
        return 2;
    }

    @Override
    public float getSpeed() {
        return 0.17f;
    }

    @Override
    public float getHealth() {
        return 20f;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GOLD;
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


    @Override
    public void Morph(Player player) {
        super.Morph(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 253, false, false, false));
    }
}
