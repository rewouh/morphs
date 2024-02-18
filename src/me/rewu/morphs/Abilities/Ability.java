package me.rewu.morphs.Abilities;

import me.rewu.morphs.Events;
import me.rewu.morphs.Main;
import me.rewu.morphs.Utils.EItemStack;
import me.rewu.morphs.Utils.MorphEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitScheduler;

import javax.security.auth.callback.Callback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public abstract class Ability {

    protected BukkitScheduler scheduler = Bukkit.getScheduler();

    protected Main main = Main.getInstance();

    protected Random rand = new Random();

    private NamespacedKey summonsKey = new NamespacedKey(main, "MORPH_OWNER");

    public abstract EItemStack getItem();

    public abstract HashMap<MorphEvent, Consumer<Event>> getCallbacks();

    public void Add(Player player) {
        HashMap<MorphEvent, Consumer<Event>> callbacks = getCallbacks();

        for (MorphEvent event : getCallbacks().keySet())
            Events.RegisterCallback(event, player, callbacks.get(event));
    }

    public void Cancel(Player player) {}

    public void Remove(Player player) {
        Cancel(player);

        HashMap<MorphEvent, Consumer<Event>> callbacks = getCallbacks();

        for (MorphEvent event : getCallbacks().keySet())
            Events.UnregisterCallback(event, player, callbacks.get(event));
    }
}
