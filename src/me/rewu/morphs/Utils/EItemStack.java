package me.rewu.morphs.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.UUID;

public class EItemStack extends ItemStack {

    public EItemStack(Material material) {
        super(material);
    }

    public EItemStack(Material material, int amount) {
        super(material, amount);
    }

    public EItemStack setName(String name) {
        ItemMeta meta = getItemMeta();

        name = name.replaceAll("&", "§");

        meta.setDisplayName(name);

        setItemMeta(meta);

        return this;
    }

    public EItemStack setLore(String[] lore) {
        ItemMeta meta = getItemMeta();

        for (int i = 0; i < lore.length; i++)
            lore[i] = lore[i].replaceAll("&", "§");

        meta.setLore(Arrays.asList(lore));

        setItemMeta(meta);

        return this;
    }

    public EItemStack setColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) getItemMeta();

        meta.setColor(color);

        setItemMeta(meta);

        return this;
    }

    public EItemStack addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = getItemMeta();

        meta.addEnchant(enchantment, level, true);

        setItemMeta(meta);

        return this;
    }

    public EItemStack hideEnchants() {
        ItemMeta meta = getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        setItemMeta(meta);

        return this;
    }

    public EItemStack setHead(String playerName) {
        SkullMeta meta = (SkullMeta) getItemMeta();

        meta.setOwner(playerName);

        setItemMeta(meta);

        return this;
    }

    public EItemStack addPotionEffect(PotionEffect potionEffect) {
        PotionMeta meta = (PotionMeta) getItemMeta();

        meta.addCustomEffect(potionEffect, true);

        setItemMeta(meta);

        return this;
    }
}
