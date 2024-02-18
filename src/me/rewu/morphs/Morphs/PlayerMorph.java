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
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerMorph extends Morph {
    private ItemStack[] armor = new ItemStack[] {
            new EItemStack(Material.IRON_BOOTS),
            new EItemStack(Material.IRON_LEGGINGS),
            new EItemStack(Material.IRON_CHESTPLATE)
    };

    private ItemStack mainWeapon = new ItemStack(Material.STONE_SWORD);

    private ItemStack secondaryWeapon = new ItemStack(Material.SHIELD);

    private ItemStack[] primaryItems = {
            new ItemStack(Material.BOW),
            new ItemStack(Material.OAK_PLANKS, 12),
    };

    private ItemStack[] secondaryItems = {
            new ItemStack(Material.ARROW, 16),
    };

    private PassiveAbility[] passives = new PassiveAbility[] {};

    private ActiveAbility[] actives = new ActiveAbility[] {
            Abilities.REFILL,
            Abilities.YEE_HAW
    };

    private Ability[] abilities = ObjectArrays.concat(passives, actives, Ability.class);

    //region Getters
    @Override
    public MorphType getType() {
        return MorphType.PLAYER;
    }

    @Override
    public int getRank() {
        return 1;
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

        player.getInventory().setHelmet(new EItemStack(Material.PLAYER_HEAD).setHead(player.getName()));
    }

    @Override
    public void Clear(Player player) {
        super.Clear(player);
    }
}
