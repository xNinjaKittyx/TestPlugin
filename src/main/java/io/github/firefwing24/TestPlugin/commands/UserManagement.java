package io.github.firefwing24.TestPlugin.commands;

import io.github.firefwing24.TestPlugin.PEXRankCheck;
import io.github.firefwing24.TestPlugin.TestPlugin;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserManagement {

	private PEXRankCheck rc = new PEXRankCheck();


	ArrayList<String> onlinePlayers = new ArrayList<String>();
	//Soon i will make this automatic so that whenever a player joins, it should automatically be entered into the list.
	//When they leave, they should be removed from the list.

	public boolean kick(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!sender.hasPermission("TestPlugin.kick")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Invalid Arguments");
			return false;
		}

		else {

			Player playerToKick = Bukkit.getServer().getPlayer(args[0]);
			if (playerToKick == null) {
				sender.sendMessage(ChatColor.RED + args[0] + "is not Online");
				return false;
			}
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// If you try to change the location of a higher ranking.
				if (rc.isLess(player, playerToKick) && TestPlugin.pex) {
					sender.sendMessage(ChatColor.RED
							+ "You can't kick a player higher rank than you!");
					return true;
				}
			}

			playerToKick.kickPlayer("You have been kicked.");
			Command.broadcastCommandMessage(sender, ChatColor.GRAY
					+ playerToKick.getName() + " has been kicked!");

		}
		return true;
	}

	public boolean ban(CommandSender sender, Command cmd, String label, String[] args) {
		return true;
	}

	public boolean unban(CommandSender sender, Command cmd, String label, String[] args) {
		return true;
	}

	public boolean banlist(CommandSender sender, Command cmd, String label, String[] args) {
		return true;
	}
	
	public boolean who(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("TestPlugin.who")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					onlinePlayers.add(p.getName());
				}
				String players = StringUtils.join(onlinePlayers.toArray(), ' ', 1, args.length);
				player.sendMessage(ChatColor.YELLOW + "Online Players: "
						+ ChatColor.WHITE + players);
				onlinePlayers.clear();
			}
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				onlinePlayers.add(p.getName());
			}
			sender.sendMessage(ChatColor.YELLOW + "Online Players: "
					+ ChatColor.WHITE + onlinePlayers.toString());
			onlinePlayers.clear();
		}
		return true;
	}

}
