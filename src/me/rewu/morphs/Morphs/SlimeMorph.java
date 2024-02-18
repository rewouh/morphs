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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SlimeMorph extends Morph {

    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.LEATHER_BOOTS).setName("&aSlime Boots").setColor(Color.LIME),
            new EItemStack(Material.LEATHER_LEGGINGS).setName("&aSlime Leggings").setColor(Color.LIME),
            new EItemStack(Material.LEATHER_CHESTPLATE).setName("&aSlime Chestplate").setColor(Color.LIME),
            new EItemStack(Material.PLAYER_HEAD).setHead("gamingguidesde")
    };

    private ItemStack mainWeapon = new EItemStack(Material.WOODEN_SWORD).setName("&aSlime Hand");

    private ItemStack secondaryWeapon = null;

    private ItemStack[] primaryItems = {
    };

    private ItemStack[] secondaryItems = {
    };

    private PassiveAbility[] passives = new PassiveAbility[] {
            Abilities.ANGEL_GRACE,
            Abilities.MITOSIS
    };

    private ActiveAbility[] actives = new ActiveAbility[] {
    };

    private Ability[] abilities = ObjectArrays.concat(passives, actives, Ability.class);

    //region Getters
    @Override
    public MorphType getType() {
        return MorphType.SLIME;
    }

    @Override
    public int getRank() {
        return 1;
    }

    @Override
    public float getSpeed() {
        return 0.17f;
    }

    @Override
    public float getHealth() {
        return 16f;
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
        return ChatColor.GREEN;
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

        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 4, false, false));
    }
}
