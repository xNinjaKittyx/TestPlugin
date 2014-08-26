package io.github.firefwing24.TestPlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHeal {


	public boolean heal(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 1) {
			sender.sendMessage("Invalid Parameters!");
			return false;
		}
		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
				return true;
			}

		} else {
			if (args.length == 0) {
				if (!sender.hasPermission("TestPlugin.heal.me")) {
					sender.sendMessage(ChatColor.RED
							+ "You don't have permission!");
					return true;
				}
				Player player = (Player) sender;
				double health = player.getMaxHealth();
				player.setHealth(health);
				player.setFoodLevel(20);
				player.sendMessage(ChatColor.YELLOW + "Healed!");
				return true;
			}
		}

		if (args.length == 1) {
			if (!sender.hasPermission("TestPlugin.heal.others")) {
				sender.sendMessage(ChatColor.RED
						+ "You don't have permission!");
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				if (!(sender instanceof Player))
					sender.sendMessage(ChatColor.RED + args[0] + "is not Online!");
				else {
					Player player = (Player) sender;
					player.sendMessage(ChatColor.RED + args[0] + "is not Online");
				}

				return false;
			}

			double health = target.getMaxHealth();
			target.setFoodLevel(20);
			target.setHealth(health);
			sender.sendMessage(ChatColor.YELLOW + "Healed " + target.getName());
			return true;
		} else
			return false;

	}
}
