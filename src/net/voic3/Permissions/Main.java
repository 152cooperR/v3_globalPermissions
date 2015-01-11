package net.voic3.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Nathan Rhodes
 * All rights reserved. © 2015.
 */
public class Main extends JavaPlugin implements Listener{


    public void onEnable()  {
        SettingsManager.getInstance().setup(this);

        getCommand("perms").setExecutor(new CommandManager());

        Bukkit.getServer().getPluginManager().registerEvents(new InjectEvents(), this);
    }
}
