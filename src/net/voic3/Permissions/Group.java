package net.voic3.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Nathan Rhodes
 * All rights reserved. © 2015.
 */
public class Group {

    private String name;
    private ArrayList<String> perms;

    public Group(String name) {
        this.name = name;

        ConfigurationSection s = SettingsManager.getInstance().getGroupSection(name);

        if (!s.contains("perms")) s.set("perms", new ArrayList<String>());

        this.perms = new ArrayList<String>(s.getStringList("perms"));
    }

    public String getName() {
        return name;
    }

    public boolean hasPerm(String perm) {
        return perms.contains(perm);
    }

    public void addPerm(String perm) {
        perms.add(perm);
        SettingsManager.getInstance().getGroupSection(name).set("perms", perms);
        SettingsManager.getInstance().save();
        for(Player all : Bukkit.getOnlinePlayers()){
            SettingsManager.getInstance().injectPlayer(all);
        }
    }

    public void removePerm(String perm) {
        perms.remove(perm);
        SettingsManager.getInstance().getGroupSection(name).set("perms", perms);
        SettingsManager.getInstance().save();
        for(Player all : Bukkit.getOnlinePlayers()){
            SettingsManager.getInstance().injectPlayer(all);
        }

    }

    public ArrayList<String> getPerms() {
        return perms;
    }
}
