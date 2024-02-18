package me.rewu.morphs.Morphs;

import com.google.common.collect.ObjectArrays;
import me.rewu.morphs.Abilities.Ability;
import me.rewu.morphs.Abilities.ActiveAbility;
import me.rewu.morphs.Abilities.PassiveAbility;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RavagerMorph extends Morph {

    private Color armorColor1 = Color.fromRGB(44, 38, 35);
    private Color armorColor2 = Color.fromRGB(102, 121, 120);

    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.LEATHER_BOOTS).setName("&8Ravager Boots").setColor(armorColor1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            new EItemStack(Material.LEATHER_LEGGINGS).setName("&8Ravager Leggings").setColor(armorColor2).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            new EItemStack(Material.LEATHER_CHESTPLATE).setName("&8Ravager Chestplate").setColor(armorColor1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            new EItemStack(Material.PLAYER_HEAD).setName("&8Ravager Head").setHead("ValveInc")
    };

    private ItemStack mainWeapon = new EItemStack(Material.NETHERITE_AXE).setName("&8Brutal Horn").addEnchant(Enchantment.DAMAGE_ALL, 3);

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
        return MorphType.RAVAGER;
    }

    @Override
    public int getRank() {
        return 1;
    }

    @Override
    public float getSpeed() {
        return 0.3f;
    }

    @Override
    public float getHealth() {
        return 100f;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_GRAY;
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
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1, false, false));
    }

    @Override
    public void Clear(Player player) {
        super.Clear(player);

        PlayerUtils.changeKnockbackResistance(player, 0f);
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
    }
}
