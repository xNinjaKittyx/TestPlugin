package io.github.firefwing24.TestPlugin.events;

import io.github.firefwing24.TestPlugin.TestPlugin;

import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatEvent implements Listener {
	protected static Pattern chatColorPattern = Pattern
			.compile("(?i)&([0-9A-F])");
	protected static Pattern chatMagicPattern = Pattern.compile("(?i)&([K])");
	protected static Pattern chatBoldPattern = Pattern.compile("(?i)&([L])");
	protected static Pattern chatStrikethroughPattern = Pattern
			.compile("(?i)&([M])");
	protected static Pattern chatUnderlinePattern = Pattern
			.compile("(?i)&([N])");
	protected static Pattern chatItalicPattern = Pattern.compile("(?i)&([O])");
	protected static Pattern chatResetPattern = Pattern.compile("(?i)&([R])");

	public ChatEvent(TestPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMessage(AsyncPlayerChatEvent e) {

		if (e.isCancelled()) {
			return;
		}

		Player player = e.getPlayer();

		PermissionUser user = PermissionsEx.getPermissionManager().getUser(
				player);

		if (user == null)
			return;

		String message = e.getMessage();
		String format = "%prefix%name%suffix" + ChatColor.WHITE + ": %message";

		String newFormat = this.replaceFormat(player, format);

		String newMessage = this.translateColorCodes(message);
		String newFormat2 = this.translateColorCodes(newFormat);

		e.setFormat(newFormat2);
		e.setMessage(newMessage);

	}

	public String replaceFormat(Player player, String format) {
		String prefix = PermissionsEx.getUser(player).getGroups()[0]
				.getPrefix();
		String suffix = PermissionsEx.getUser(player).getGroups()[0]
				.getSuffix();
		return format.replace("%prefix", prefix).replace("%suffix", suffix)
				.replace("%name", "%1$s").replace("%message", "%2$s");
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
