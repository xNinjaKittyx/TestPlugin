package io.github.firefwing24.TestPlugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGod {

	public CommandGod() {
	}

	public static List<String> godToggleList = new ArrayList<String>();

	public boolean god(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
				return true;
			}
		}

		Player player = (Player) sender;
		if (args.length == 0) {
			if (!sender.hasPermission("TestPlugin.god.me")) {
				sender.sendMessage(ChatColor.RED
						+ "You don't have permission!");
				return true;
			}
			if (!(godToggleList.contains(player.getName()))) {
				godToggleList.add(player.getName());
				player.sendMessage(ChatColor.GREEN + "Godmode Enabled!");
			} else {
				godToggleList.remove(player.getName());
				player.sendMessage(ChatColor.GREEN + "Godmode Disabled!");
			}
			return true;
		}

		else if (args.length == 1) {
			if (!sender.hasPermission("TestPlugin.god.others")) {
				sender.sendMessage(ChatColor.RED
						+ "You don't have permission!");
				return true;
			}
			Player target = player.getServer().getPlayer(args[0]);
			if (target == null) {
				player.sendMessage(ChatColor.RED + args[0] + "is not Online");
				return false;
			} else {

				if (!(godToggleList.contains(target.getName()))) {
					godToggleList.add(target.getName());
					player.sendMessage(ChatColor.GREEN + "Godmode Enabled for " + target.getName() + ChatColor.GREEN + "!");
				} else {
					godToggleList.remove(target.getName());
					player.sendMessage(ChatColor.GREEN
							+ "Godmode Disabled for" + target.getName() + ChatColor.GREEN + "!");
				}
				return true;
			}

		}

		else {
			player.sendMessage(ChatColor.RED + "Invalid Parameters");
			return false;
		}

	}

}
