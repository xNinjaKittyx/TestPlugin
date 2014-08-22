package io.github.firefwing24.TestPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly {

	public CommandFly() {
	}
	
	public boolean fly(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.fly")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
			return true;
		}

		if (args.length > 1)
			return false;

		Player player = (Player) sender;
		// Set Player to allow flight
		if (args.length == 0) {
			if (!sender.hasPermission("TestPlugin.fly.me")) {
				sender.sendMessage(ChatColor.RED
						+ "You don't have permission!");
				return true;
			}
			if (player.getAllowFlight()) {
				player.setAllowFlight(false);
				player.sendMessage(ChatColor.AQUA + "Flying Disabled!");
			} else {
				player.setAllowFlight(true);
				player.sendMessage(ChatColor.AQUA + "Flying Enabled!");
			}
			return true;
		}

		// Allow different player to fly
		else if (args.length == 1) {
			if (!sender.hasPermission("TestPlugin.fly.others")) {
				sender.sendMessage(ChatColor.RED
						+ "You don't have permission!");
				return true;
			}

			Player target = player.getServer().getPlayer(args[0]);
			if (target == null) {
				player.sendMessage(ChatColor.RED + args[0] + "is not Online");
				return false;
			}
			if (target.getAllowFlight()) {
				target.setAllowFlight(false);
				player.sendMessage(ChatColor.AQUA + "Flying Disabled for " + player.getName() + ChatColor.GREEN + "!");
			} else {
				player.setAllowFlight(true);
				player.sendMessage(ChatColor.AQUA + "Flying Enabled for " + player.getName() + ChatColor.GREEN + "!");
			}
			return true;
		}

		return false;
	}
}
