package me.rewu.morphs.Abilities;

import org.bukkit.entity.Player;

public abstract class ActiveAbility extends Ability {

    public abstract Integer getCooldown();

    public abstract void Use(Player player);
}
