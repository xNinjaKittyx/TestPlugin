package io.github.firefwing24.TestPlugin.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandBroadcast {

	public CommandBroadcast() {
	}

	public boolean broadcast(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.broadcast")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		if (args.length == 0)
			return false;
		String message = StringUtils.join(args, " ", 0, args.length);
		String newMessage = ChatColor.translateAlternateColorCodes('&', message);
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[CONSOLE]: " + ChatColor.RESET + newMessage);
		return true;
		
	}
}
