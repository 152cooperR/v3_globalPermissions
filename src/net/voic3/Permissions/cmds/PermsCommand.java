package net.voic3.Permissions.cmds;

import org.bukkit.command.CommandSender;

/**
 * Created by Nathan Rhodes
 * All rights reserved. © 2015.
 */
public abstract class PermsCommand {

    private String name, args;

    public PermsCommand(String name, String args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public String getArgs() {
        return args;
    }

    public abstract void run(CommandSender sender, String[] args);
}