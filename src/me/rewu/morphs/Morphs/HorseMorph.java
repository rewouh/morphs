package me.rewu.morphs.Morphs;

import com.google.common.collect.ObjectArrays;
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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class HorseMorph extends Morph {

    private ItemStack[] kitArmor = new ItemStack[] {
            new EItemStack(Material.LEATHER_BOOTS).setName("&eHorse Boots").setColor(Color.YELLOW),
            new EItemStack(Material.LEATHER_LEGGINGS).setName("&eHorse Leggings").setColor(Color.YELLOW),
            new EItemStack(Material.LEATHER_CHESTPLATE).setName("&eHorse Chestplate").setColor(Color.YELLOW),
            new EItemStack(Material.PLAYER_HEAD).setName("&eHorse Head").setHead("gavertoso")
    };

    private ItemStack mainWeapon = new EItemStack(Material.WOODEN_SWORD).setName("&eHorse Leg").addEnchant(Enchantment.KNOCKBACK, 1);

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
        return MorphType.HORSE;
    }

    @Override
    public int getRank() {
        return 1;
    }

    @Override
    public float getSpeed() {
        return new Random().nextFloat(0.2125f, 0.4375f);
    }

    @Override
    public float getHealth() {
        return new Random().nextInt(15, 30);
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
        return ChatColor.YELLOW;
    }

    @Override
    public ItemStack[] getArmor() {
        return kitArmor;
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

        Random rand = new Random();
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, rand.nextInt(1, 3), false, false));
    }

    @Override
    public void Clear(Player player) {
        super.Clear(player);

        player.setWalkSpeed(0.2f);
    }
}
