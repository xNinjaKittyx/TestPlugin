package io.github.firefwing24.TestPlugin;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public final class TestPlugin extends JavaPlugin {

	public TestPlugin() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onDisable(){
		getLogger().info("TestPlugin has been Disabled!");
	}
	@Override
	public void onEnable(){
		
		this.getCommand("test").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("tp").setExecutor(new TestPluginCommandExecutor (this));
		
		PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

}
