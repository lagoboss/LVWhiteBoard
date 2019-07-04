package com.gmail.mchelplagoverse.LVWhiteBoard;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by lake.smith on 5/20/2018.
 */
public class BootLVWhiteBoard extends JavaPlugin implements Listener {

    public static Economy econ;

    private static BootLVWhiteBoard instance;

    public static BootLVWhiteBoard inst(){
        return instance;
    }

    @Override
    public void onEnable(){

        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        //load configs
        this.saveDefaultConfig();
        FileConfiguration dataPlayer = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "data.yml"));
        FileConfiguration dataConfig = this.getConfig();


        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("wb").setExecutor(new WBCommandProcessor());

        instance = this;
        }



    @Override
    public void onDisable(){
        this.saveConfig();
        instance = null;
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy getEcononomy() {
        return econ;
    }
}
