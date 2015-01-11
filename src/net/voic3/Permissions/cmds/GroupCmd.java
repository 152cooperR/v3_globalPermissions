package net.voic3.Permissions.cmds;

import net.voic3.Permissions.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Nathan Rhodes
 * All rights reserved. © 2015.
 */
public class GroupCmd extends PermsCommand {

    /*
     * perm group <name>
         * perm group <name> addperm <perm>
         * perm group <name> removeperm <perm>
         * perm group <name> add <name>
         * perm group <name> remove <name>
     */
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You didn't enter a group.");
            return;
        }

        String g = args[0];

        if (args.length < 2) {
            if (SettingsManager.getInstance().getGroup(g) != null) {
                if (SettingsManager.getInstance().getGroup(g).getPerms().size() == 0) {
                    sender.sendMessage(ChatColor.YELLOW + "No permissions for " + g);
                    return;
                }

                for (String perm : SettingsManager.getInstance().getGroup(g).getPerms()) {
                    sender.sendMessage(ChatColor.YELLOW + perm);
                    return;
                }
            }
            else {
                SettingsManager.getInstance().createGroup(g);
                sender.sendMessage(ChatColor.GREEN + "Created group!");
                return;
            }
        }

        else {
            if (args[1].equalsIgnoreCase("add")) {
                SettingsManager.getInstance().addGroup(args[2], g);
                sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " to group " + g);
                return;
            }
            else if (args[1].equalsIgnoreCase("remove")) {
                SettingsManager.getInstance().removeGroup(args[2], g);
                sender.sendMessage(ChatColor.GREEN + "Removed " + args[2] + " from group " + g);
                return;
            }
            else if (args[1].equalsIgnoreCase("addperm")) {
                SettingsManager.getInstance().getGroup(g).addPerm(args[2]);
                sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " to group " + g);
                return;
            }
            else if (args[1].equalsIgnoreCase("removeperm")) {
                SettingsManager.getInstance().getGroup(g).removePerm(args[2]);
                sender.sendMessage(ChatColor.GREEN + "Removed " + args[2] + " from group " + g);
                return;
            }
        }
    }

    public GroupCmd() {
        super("group", "<name> [<add | remove | addperm | removeperm> <user | perm>]");
    }
}