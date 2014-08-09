package io.github.firefwing24.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MOTDHandler implements Listener {
	
	public MOTDHandler(TestPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onLogin (PlayerJoinEvent e) {
		Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Welcome to the Server " + e.getPlayer() + "!");
		//TestPlugin.plugin.getConfig().getString("MOTD")
	}
}
