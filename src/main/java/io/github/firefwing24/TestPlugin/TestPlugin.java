package io.github.firefwing24.TestPlugin;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.github.firefwing24.TestPlugin.events.ChatEvent;
import io.github.firefwing24.TestPlugin.events.GodEvent;
import io.github.firefwing24.TestPlugin.events.MOTDEvent;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public final class TestPlugin extends JavaPlugin {

	public static TestPlugin plugin;
	public static Economy econ = null;
	public static Permission perms = null;
	public static Chat chat = null;
	public static boolean pex;
	File configFile;
	
	FileConfiguration config;

	// Values of Config

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info(
				pdfFile.getName() + " by firefwing24 has been Disabled!");
		saveYamls();
	}

	@Override
	public void onEnable() {
		
		configFile = new File(getDataFolder(), "config.yml");
		
		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		config = new YamlConfiguration();
		
		loadYamls(); 
		

		/*
		 * if (!setupEconomy() ) { getLogger().severe(String.format(
		 * "[%s] - Disabled due to no Vault dependency found!",
		 * getDescription().getName()));
		 * getServer().getPluginManager().disablePlugin(this); return; }
		 */
		setupPermissions();
		setupChat();

		pex = isPexOn();

		new MOTDEvent(this);
		new ChatEvent(this);
		new GodEvent(this);
		
		this.getCommand("broadcast").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("fly").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("god").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("heal").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("home").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("kick").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("kill").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("repair").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("sethome").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("spawn").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("setspawn").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("testplugin").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("tp").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("tpa").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("tpaccept").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("tpdeny").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("tphere").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("who").setExecutor(new TestPluginCommandExecutor(this));
		this.getCommand("w").setExecutor(new TestPluginCommandExecutor(this));

		PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info(
				pdfFile.getName() + " version " + pdfFile.getVersion()
						+ " is enabled!");
		getLogger().info("Created by firefwing24 (xNinjaKittyx)");
	}

	
	private void firstRun() throws Exception {
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), configFile);
		}
	}
	
	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while((len=in.read(buf))> 0) {
				out.write(buf,0,len);
			}
			out.close();
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadYamls() {
		try {
			config.load(configFile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveYamls() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

/*	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	} */

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager()
				.getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer()
				.getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	private boolean isPexOn() {
		if (getServer().getPluginManager().getPlugin("PermissionsEx") != null)
			return true;
		else
			return false;

	}
}
