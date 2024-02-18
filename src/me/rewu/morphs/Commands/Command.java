package me.rewu.morphs.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command {

    public abstract void Execute(CommandSender sender, String[] args);
}
