package io.github.firefwing24.TestPlugin.events;

import io.github.firefwing24.TestPlugin.TestPlugin;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class MOTDEvent implements Listener {
	protected static Pattern chatColorPattern = Pattern.compile("(?i)&([0-9A-F])");
	protected static Pattern chatMagicPattern = Pattern.compile("(?i)&([K])");
	protected static Pattern chatBoldPattern = Pattern.compile("(?i)&([L])");
	protected static Pattern chatStrikethroughPattern = Pattern.compile("(?i)&([M])");
	protected static Pattern chatUnderlinePattern = Pattern.compile("(?i)&([N])");
	protected static Pattern chatItalicPattern = Pattern.compile("(?i)&([O])");
	protected static Pattern chatResetPattern = Pattern.compile("(?i)&([R])");

	public MOTDEvent(TestPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(PlayerJoinEvent e) {
		Player player = e.getPlayer();

		String prefix = PermissionsEx.getUser(player).getGroups()[0].getPrefix();
		String suffix = PermissionsEx.getUser(player).getGroups()[0].getSuffix();
		
		String message = ChatColor.GREEN + "Welcome to the Server " + prefix + player.getDisplayName() + suffix + ChatColor.GREEN + "!";
		String newMessage = this.translateColorCodes(message);
		e.setJoinMessage(null);
		Bukkit.broadcastMessage(newMessage);
		// TestPlugin.plugin.getConfig().getString("MOTD")
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onExit(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		String prefix = PermissionsEx.getUser(player).getGroups()[0]
				.getPrefix();
		String suffix = PermissionsEx.getUser(player).getGroups()[0]
				.getSuffix();
		String message = prefix + player.getDisplayName()
				+ suffix + ChatColor.GRAY + " has left the server.";
		String newMessage = this.translateColorCodes(message);
		e.setQuitMessage(null);
		Bukkit.broadcastMessage(newMessage);
	}

	protected String translateColorCodes(String string) {
		if (string == null) {
			return "";
		}

		String newstring = string;
		newstring = chatColorPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatMagicPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatBoldPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatStrikethroughPattern.matcher(newstring).replaceAll(
				"\u00A7$1");
		newstring = chatUnderlinePattern.matcher(newstring).replaceAll(
				"\u00A7$1");
		newstring = chatItalicPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatResetPattern.matcher(newstring).replaceAll("\u00A7$1");
		return newstring;
	}

}
