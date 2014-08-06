package io.github.firefwing24.TestPlugin;

import org.bukkit.plugin.java.JavaPlugin;


public final class TestPlugin extends JavaPlugin {

	public TestPlugin() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onEnable(){
		getLogger().info("Disabled!");
		this.getCommand("test").setExecutor(new TestPluginCommandExecutor (this));
		this.getCommand("tp").setExecutor(new TestPluginCommandExecutor (this));
	}
	@Override
	public void onDisable(){
		getLogger().info("Enabled!");
	}

}
