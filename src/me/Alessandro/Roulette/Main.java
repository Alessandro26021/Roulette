package me.Alessandro.Roulette;

import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener{
	
	public static Economy econ = null;
	
	@Override
	public void onEnable() {
		System.out.println("[Roulette] Plugin aktiviert!");
		
		this.getServer().getPluginManager().registerEvents(new Countdown(this), this);
		this.getServer().getPluginManager().registerEvents(new PlaceBet(), this);
		
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            //getServer().getPluginManager().disablePlugin(this);
            return;
        }
	}
	
	@Override
	public void onDisable() {
		System.out.println("[Plugin] Plugin deaktiviert!");
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	
	public Main getPlugin() {
		return this;
	}
	
}
