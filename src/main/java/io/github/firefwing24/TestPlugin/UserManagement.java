package io.github.firefwing24.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserManagement {

	private PEXRankCheck rc = new PEXRankCheck();

	public UserManagement() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean kick(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Invalid Arguments");
			return false;
		}
		
		else {
			
			Player playerToKick = Bukkit.getServer().getPlayerExact(args[0]);
			if (playerToKick == null) {
				sender.sendMessage(ChatColor.RED + args[0] + " is not Online!");
				return false;
			}
			if (sender instanceof Player) {
				Player player = (Player) sender;
				//If you try to change the location of a higher ranking.
				if (rc.isLess(player, playerToKick) && TestPlugin.pex) {
					sender.sendMessage(ChatColor.RED + "You can't kick a player higher rank than you!");
					return true;
				}
			}
			
			playerToKick.kickPlayer("You have been kicked.");
			Command.broadcastCommandMessage(sender, ChatColor.GRAY + playerToKick.getName() + " has been kicked!");
			
			
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
	
}
