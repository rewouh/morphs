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

public class WitchMorph extends Morph {

    private Color armorColor1 = Color.fromRGB(48, 20, 79);
    private Color armorColor2 = Color.fromRGB(87, 87, 87);

    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.LEATHER_BOOTS).setName("&5Witch Boots").setColor(armorColor2),
            new EItemStack(Material.LEATHER_LEGGINGS).setName("&5Witch Leggings").setColor(armorColor1),
            new EItemStack(Material.LEATHER_CHESTPLATE).setName("&5Witch Chestplate").setColor(armorColor1),
            new EItemStack(Material.PLAYER_HEAD).setName("65Witch Head").setHead("MHF_Witch")
    };

    private ItemStack mainWeapon = new EItemStack(Material.STICK).setName("&5Witch Cane").addEnchant(Enchantment.KNOCKBACK, 2);

    private ItemStack secondaryWeapon = null;

    private ItemStack[] primaryItems = {};

    private ItemStack[] secondaryItems = {};

    private PassiveAbility[] passives = new PassiveAbility[] {
            Abilities.SURPRISE_DRINK
    };

    private ActiveAbility[] actives = new ActiveAbility[] {
            Abilities.SURPRISE_SPLASH
    };

    private Ability[] abilities = ObjectArrays.concat(passives, actives, Ability.class);

    //region Getters
    @Override
    public MorphType getType() {
        return MorphType.WITCH;
    }

    @Override
    public int getRank() {
        return 2;
    }

    @Override
    public float getSpeed() {
        return 0.2f;
    }

    @Override
    public float getHealth() {
        return 20f;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_PURPLE;
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
