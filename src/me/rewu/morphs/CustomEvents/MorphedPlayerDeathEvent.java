package me.rewu.morphs.CustomEvents;

import me.rewu.morphs.Morphs.Morph;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;

public class MorphedPlayerDeathEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player PLAYER;

    private Morph MORPH;

    private PlayerDeathEvent DEATH_EVENT;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public MorphedPlayerDeathEvent(Player player, Morph morph, PlayerDeathEvent event) {
        this.PLAYER = player;
        this.MORPH = morph;
        this.DEATH_EVENT = event;
    }

    public Player getPlayer() { return PLAYER; }

    public Morph getMorph() { return MORPH; }

    public PlayerDeathEvent getDeathEvent() { return DEATH_EVENT; }
}
