package net.voic3.Permissions;


import java.util.ArrayList;
import java.util.Arrays;

import net.voic3.Permissions.cmds.GroupCmd;
import net.voic3.Permissions.cmds.PermsCommand;
import net.voic3.Permissions.cmds.TestCmd;
import net.voic3.Permissions.cmds.UserCmd;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Nathan Rhodes
 * All rights reserved. © 2015.
 */

public class CommandManager implements CommandExecutor {

    private ArrayList<PermsCommand> cmds = new ArrayList<PermsCommand>();

    public CommandManager() {
        cmds.add(new GroupCmd());
        cmds.add(new UserCmd());
        cmds.add(new TestCmd());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("perms")) {

            if (args.length == 0) {
                for (PermsCommand c : cmds) {
                    sender.sendMessage(ChatColor.YELLOW + "/perms " + c.getName() + " " + c.getArgs());
                }

                return true;
            }

            ArrayList<String> a = new ArrayList<String>(Arrays.asList(args));
            a.remove(0);

            for (PermsCommand c : cmds) {
                if (c.getName().equalsIgnoreCase(args[0])) {
                    try { c.run(sender, a.toArray(new String[a.size()])); }
                    catch (Exception e) { sender.sendMessage(ChatColor.RED + "An error has occurred."); e.printStackTrace(); }
                    return true;
                }
            }

            sender.sendMessage(ChatColor.RED + "Invalid command!");
        }

        return true;
    }
}