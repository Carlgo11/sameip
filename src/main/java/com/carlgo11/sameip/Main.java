package com.carlgo11.sameip;

import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;
import player.Join;

public class Main extends JavaPlugin {

    public void onEnable()
    {
        loadConfig();
        Mysql.updateStrings(getConfig().getString("mysql.url"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password"), getConfig().getString("mysql.database"), getConfig().getString("mysql.table"));
        Mysql.createTables();
        getServer().getPluginManager().registerEvents(new Join(), this);
    }

    public void onDisable()
    {

    }

    public void commands()
    {

    }

    public void loadConfig()
    {
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            saveDefaultConfig();
            getConfig().options().copyHeader(true);
            System.out.println("[" + getDataFolder().getName() + "] No config.yml detected, config.yml created");
        }
    }
}
