package io.github.firefwing24.TestPlugin.commands;

import io.github.firefwing24.TestPlugin.PEXRankCheck;
import io.github.firefwing24.TestPlugin.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKill {

	private PEXRankCheck rc = new PEXRankCheck();

	public CommandKill() {
		// TODO Auto-generated constructor stub
	}

	public boolean kill(CommandSender sender, Command cmd, String label, String args[]) {

		if (!sender.hasPermission("TestPlugin.kill")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}

		if (sender instanceof Player) {
			Player player = (Player) sender;

			Player target = player.getServer().getPlayer(args[0]);
			if (args.length < 1)
				return false;

			if (target == null) {
				player.sendMessage(ChatColor.RED + args[0] + "is not Online");
				return false;
			} else {

				if (rc.isLess(player, target) && TestPlugin.pex) {
					sender.sendMessage(ChatColor.RED
							+ "You can't kill a player higher rank than you!");
					return true;
				}
				target.setHealth(0);
			}
			return true;
		} else {
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (args.length < 1)
				return false;
			if (target == null) {
				sender.sendMessage(ChatColor.RED + args[0] + "is not Online");
				return false;
			} else {

				target.setHealth(0);
			}
			return true;
		}
	}
}
