package io.github.firefwing24.TestPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public final class TestPlugin extends JavaPlugin {

	public static TestPlugin plugin;
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;
    public static boolean pex;
	
	public TestPlugin() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info(pdfFile.getName() + " by firefwing24 has been Disabled!");
	}
	@Override
	public void onEnable(){
		
		new MOTDHandler(this);
		new ChatEvent(this);
		new GodEvent(this);
		
		/*if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        } */
        setupPermissions();
        setupChat();
		
        pex = isPexOn();
        
		this.getCommand("broadcast").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("fly").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("god").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("heal").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("home").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("kill").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("sethome").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("test").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("tp").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("tphere").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("who").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("w").setExecutor(new TestPluginCommandExecutor(this));
		
		PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
		getLogger().info("Created by firefwing24 (xNinjaKittyx)");
	}
	
	public void LoadConfig() {
		
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

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
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
