package io.github.firefwing24.TestPlugin;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public final class TestPlugin extends JavaPlugin {

	public static TestPlugin plugin;
	
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

}
