package me.rewu.morphs.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class HeadCommand implements CommandExecutor {

    private HashMap<String, Command> subCommands = new HashMap<>();

    String usage = "Usage: /m <player;clear;stats>";

    private boolean wrongUsage(CommandSender sender) {
        sender.sendMessage(usage);

        return true;
    }

    public void registerSubCommands() {
        subCommands.put("morph", new MorphCommand());
        subCommands.put("clear", new ClearCommand());
        subCommands.put("stats", new StatsCommand());

        GiveWithdrawCommand giveWithdrawCommand = new GiveWithdrawCommand();
        subCommands.put("give", giveWithdrawCommand);
        subCommands.put("withdraw", giveWithdrawCommand);

        subCommands.put("roll", new RollCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String name, String[] args) {

        if (args.length == 0)
            return wrongUsage(sender);

        String subCommand = args[0];

        String foundCommand = "";

        for (String cmdName : subCommands.keySet()) {
            if (!cmdName.startsWith(subCommand))
                continue;

            foundCommand = cmdName;
            break;
        }

        if (foundCommand.isEmpty())
            foundCommand = "morph";

        subCommands.get(foundCommand).Execute(sender, args);

        return true;
    }
}
