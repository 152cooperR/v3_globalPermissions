package net.voic3.Permissions.cmds;

import net.voic3.Permissions.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Nathan Rhodes
 * All rights reserved. © 2015.
 */
public class UserCmd extends PermsCommand {

    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You didn't enter a user.");
            return;
        }

        String p = args[0];

        if (args.length < 2) {
            if (SettingsManager.getInstance().getPerms(p).size() == 0) {
                sender.sendMessage(ChatColor.YELLOW + "No permissions for " + p);
            }

            for (String perm : SettingsManager.getInstance().getPerms(p)) {
                sender.sendMessage(ChatColor.YELLOW + perm);
                return;
            }
        }

        else {
            if (args[1].equalsIgnoreCase("addperm")) {
                SettingsManager.getInstance().addPerm(p, args[2]);
                sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " to " + p);
                return;
            }

            else if (args[1].equalsIgnoreCase("removeperm")) {
                SettingsManager.getInstance().removePerm(p, args[2]);
                sender.sendMessage(ChatColor.GREEN + "Removed " + args[2] + " from " + p);
                return;
            }
        }
    }

    public UserCmd() {
        super("user", "<name> [<addperm | removeper> <perm>]");
    }
}