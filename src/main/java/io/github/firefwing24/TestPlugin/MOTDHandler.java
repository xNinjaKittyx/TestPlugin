package io.github.firefwing24.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class MOTDHandler implements Listener {
	
	public MOTDHandler(TestPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onLogin (PlayerJoinEvent e) {
		Player player = e.getPlayer();
		/*String group = TestPlugin.chat.getPlayerGroups(e.getPlayer())[0];
		String prefix = TestPlugin.chat.getGroupPrefix(e.getPlayer().getWorld(),group);
		String suffix = TestPlugin.chat.getGroupSuffix(e.getPlayer().getWorld(),group);*/
		String prefix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getPrefix();
		String suffix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getSuffix();
		String message = ChatColor.GREEN + "Welcome to the Server " + prefix + player.getDisplayName() + suffix + ChatColor.GREEN + "!";
		e.setJoinMessage(null);
		Bukkit.broadcastMessage(message);
		//TestPlugin.plugin.getConfig().getString("MOTD")
	}
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onExit (PlayerQuitEvent e) {
		Player player = e.getPlayer();
		/*String group = TestPlugin.chat.getPlayerGroups(e.getPlayer())[0];
		String prefix = TestPlugin.chat.getGroupPrefix(e.getPlayer().getWorld(),group);
		String suffix = TestPlugin.chat.getGroupSuffix(e.getPlayer().getWorld(),group);*/
		String prefix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getPrefix();
		String suffix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getSuffix();
		String message = ChatColor.GRAY + prefix + player.getDisplayName() + suffix + " has left the server.";
		e.setQuitMessage(null);
		Bukkit.broadcastMessage(message);
	}
}
