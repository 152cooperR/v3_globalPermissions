package net.voic3.Permissions.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Nathan Rhodes
 * All rights reserved. © 2015.
 */
public class TestCmd extends PermsCommand {

    /*
     * perms test <player> <perm>
     */
    public void run(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "You didn't enter a permission.");
            return;
        }

        Player p = Bukkit.getServer().getPlayer(args[0]);

        if (p == null) {
            sender.sendMessage(ChatColor.RED + "Could not find player.");
            return;
        }

        if (p.hasPermission(args[1])) {
            sender.sendMessage(ChatColor.GREEN + "Player " + p.getName() + " has permission " + args[1]);
        }

        else {
            sender.sendMessage(ChatColor.RED + "Player " + p.getName() + " does not have permission " + args[1]);
        }
    }

    public TestCmd() {
        super("test", "<player> <perm>");
    }
}