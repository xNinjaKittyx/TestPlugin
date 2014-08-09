package io.github.firefwing24.TestPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class MOTDHandler implements Listener {
	
	public MOTDHandler(TestPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onConnect(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		String prefix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getPrefix();
		player.setDisplayName(prefix + player.getDisplayName());
	}
	
	@EventHandler
	public void onLogin (PlayerJoinEvent e) {
		Player player = e.getPlayer();
		e.setJoinMessage(ChatColor.GREEN + "Welcome to the Server " + player.getDisplayName() + ChatColor.GREEN + "!");
		//TestPlugin.plugin.getConfig().getString("MOTD")
	}
	public void onExit (PlayerQuitEvent e) {
		Player player = e.getPlayer();
		e.setQuitMessage(ChatColor.GRAY + player.getDisplayName() + " has left the server.");
	}
}
