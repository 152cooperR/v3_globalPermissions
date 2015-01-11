package net.voic3.Permissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
/**
 * Created by Nathan Rhodes
 * All rights reserved. © 2015.
 */
public class SettingsManager {

    private SettingsManager() { }

    private static SettingsManager instance = new SettingsManager();

    public static SettingsManager getInstance() {
        return instance;
    }

    private Plugin p;
    private FileConfiguration config;
    private File cfile;

    private HashMap<String, PermissionAttachment> attachments = new HashMap<String, PermissionAttachment>();
    private ArrayList<Group> groups = new ArrayList<Group>();

    public void setup(Plugin p) {
        this.p = p;

        if (!p.getDataFolder().exists()) p.getDataFolder().mkdir();

        cfile = new File(p.getDataFolder(), "perms.yml");

        if (!cfile.exists()) {
            try { cfile.createNewFile(); }
            catch (Exception e) { e.printStackTrace(); }
        }

        config = YamlConfiguration.loadConfiguration(cfile);

        if (!config.contains("groups")) config.createSection("groups");

        setupGroups();
    }

	/*
	 * BUGS:
	 * - addPerms doesn't do a List<String> or something O.o
	 *
	 * A bunch of group stuff:
	 * user:
  pogostick29:
    perms: buyxp.buy
    groups: []
  test:
    groups: []
  buyxp:
    buy:
      groups:
      - buyxp.buy
	 */

    /*
     * user:
     *   pogostick29:
     *     perms:
     *       - your.mom
     *       - l.ol
     *     groups:
     *       - sample
     *
     * group:
     *   sample:
     *     perms:
     *       - your.mom
     *       - l.ol
     */
    public void addPerm(String player, String perm) {
        player = player.toLowerCase();

        Player p = Bukkit.getServer().getPlayer(player);

        if (p != null) {
            injectPlayer(p);
            attachments.get(p.getName()).setPermission(perm, true);
        }

        List<String> perms = getPerms(player);
        perms.add(perm);
        config.set("user." + player + ".perms", perm);

        save();
    }

    public void removePerm(String player, String perm) {
        player = player.toLowerCase();

        Player p = Bukkit.getServer().getPlayer(player);

        if (p != null) {
            injectPlayer(p);
            attachments.get(p.getName()).setPermission(perm, false);
        }

        List<String> perms = getPerms(player);
        perms.remove(perm);
        config.set("user." + player + ".perms", perm);

        save();
    }

    public List<String> getPerms(String player) {
        player = player.toLowerCase();

        if (!config.contains("user." + player + ".perms")) config.set("user." + player + ".perms", new ArrayList<String>());

        return config.getStringList("user." + player + ".perms");
    }

    public void addGroup(String player, String g) {
        player = player.toLowerCase();

        List<String> groups = getGroups(player);
        groups.add(g);
        config.set("user." + player + ".groups", groups);

        save();

        Player p = Bukkit.getServer().getPlayer(player);

        if (p != null) {
            injectPlayer(p);
        }
    }

    public void removeGroup(String player, String g) {
        player = player.toLowerCase();

        List<String> groups = getGroups(player);
        groups.remove(g);
        config.set("user." + player + ".groups", groups);

        save();

        Player p = Bukkit.getServer().getPlayer(player);

        if (p != null) {
            injectPlayer(p);
        }
    }

    public List<String> getGroups(String player) {
        player = player.toLowerCase();

        if (!config.contains("user." + player + ".groups")) config.set("user." + player + ".groups", new ArrayList<String>());

        return config.getStringList("user." + player + ".groups");
    }

    public void createGroup(String group) {
        config.getConfigurationSection("groups").set(group + ".perms", new ArrayList<String>());
        save();
        setupGroups();
    }

    private void setupGroups() {
        groups.clear();

        for (String groupName : config.getConfigurationSection("groups").getKeys(false)) {
            groups.add(new Group(groupName));
        }
    }

    public void injectPlayer(Player... pl) {
        for (Player p : pl) {
            if (attachments.get(p.getName()) == null) attachments.put(p.getName(), p.addAttachment(getPlugin()));

            for (Entry<String, Boolean> perm : attachments.get(p.getName()).getPermissions().entrySet()) {
                if (getPerms(p.getName()).contains(perm.getKey())) attachments.get(p.getName()).setPermission(perm.getKey(), true);
                else attachments.get(p.getName()).setPermission(perm.getKey(), false);
            }

            if (!config.contains("user." + p.getName().toLowerCase() + ".groups")) config.set("user." + p.getName().toLowerCase() + ".groups", new ArrayList<String>());

            for (String gName : config.getStringList("user." + p.getName().toLowerCase() + ".groups")) {
                for (Group g : groups) {
                    if (g.getName().equals(gName)) {
                        for (String perm : g.getPerms()) {
                            System.out.println("Injecting " + perm + " from " + gName);
                            attachments.get(p.getName()).setPermission(perm, true);
                        }
                    }
                }
            }
        }
    }

    public void uninjectPlayer(Player pl) {
        if (attachments.get(pl.getName()) == null) return;
        pl.removeAttachment(attachments.get(pl.getName()));
        attachments.remove(pl.getName());
    }

    public Group getGroup(String name) {
        for (Group g : groups) {
            if (g.getName().equals(name)) return g;
        }

        return null;
    }

    public ConfigurationSection getGroupSection(String name) {
        return config.getConfigurationSection("groups." + name);
    }

    public void save() {
        try { config.save(cfile); }
        catch (Exception e) { e.printStackTrace(); }
    }

    public Plugin getPlugin() {
        return p;
    }

    /*
    * v3_globalPermissions.iml
    * */
}
