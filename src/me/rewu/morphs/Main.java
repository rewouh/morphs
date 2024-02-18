package me.rewu.morphs;

import me.rewu.morphs.Commands.HeadCommand;
import me.rewu.morphs.Data.PlayerData;
import me.rewu.morphs.Morphs.*;
import me.rewu.morphs.Utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static Main instance;

    private Logger logger = getLogger();

    private Events events;

    public static Main getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        PluginManager pluginManager = Bukkit.getPluginManager();

        // Registering commands
        HeadCommand headCommand = new HeadCommand();
        headCommand.registerSubCommands();
        getCommand("m").setExecutor(headCommand);

        // Registering event
        events = new Events();
        pluginManager.registerEvents(events, this);

        // Registering kits
        MorphsAPI.registerMorphs();

        // Reload compatibility
        for (Player player : Bukkit.getOnlinePlayers())
            events.onJoin(new PlayerJoinEvent(player, null));
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers())
            events.onQuit(new PlayerQuitEvent(player, null));
    }
}
