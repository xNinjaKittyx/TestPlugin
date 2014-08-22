package io.github.firefwing24.TestPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHome {

	public CommandHome() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean home(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.home")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("This can only be used by in-game Players");
			return true;
		}

		Player player = (Player) sender;
		Location location = player.getBedSpawnLocation();
		if (location == null) {
			player.sendMessage("No Home Exists");
			return false;
		}
		player.teleport(location);
		player.sendMessage(ChatColor.GREEN + "Teleported Home");
		return true;
	}
	
	public boolean sethome(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.sethome")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("This can only be used by in-game Players");
			return true;
		}

		Player player = (Player) sender;
		Location location = player.getLocation();
		player.setBedSpawnLocation(location, true);
		player.sendMessage(ChatColor.GREEN + "Home Set!");
		return true;
	}

}
