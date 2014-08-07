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
		getLogger().info("TestPlugin has been Disabled!");
	}
	@Override
	public void onEnable(){

		this.getCommand("fly").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("home").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("kill").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("sethome").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("test").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("tp").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("who").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("w").setExecutor(new TestPluginCommandExecutor(this));
		
		PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

}
