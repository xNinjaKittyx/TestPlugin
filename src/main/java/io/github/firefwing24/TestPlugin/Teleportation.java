package io.github.firefwing24.TestPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleportation {

	private PEXRankCheck rc = new PEXRankCheck();
	
	public Teleportation() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean tpToPlayer(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.tp.me")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return false;
		}
		Player player = (Player) sender;
		Player target = player.getServer().getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatColor.RED + args[0] + " is not online or doesn't exist!");
			return false;
		}
		Location location = target.getLocation();
		player.teleport(location);
		return true;
	}
	
	public boolean tpP2P(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.tp.others")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return false;
		}
		Player player = (Player) sender;
		Player target1 = player.getServer().getPlayer(args[0]);
		Player target2 = player.getServer().getPlayer(args[1]);
		//If you try to change the location of a higher ranking.
		if (rc.isLess(player, target1) && TestPlugin.pex) {
			sender.sendMessage(ChatColor.RED + "You can't TP a player with a higher rank than you!");
			return true;
		}
		if (target1 != null && target2 != null) {
			Location location = target2.getLocation();
			target1.teleport(location);
			target1.sendMessage(ChatColor.YELLOW + "You have been tp'd to " + target2.getDisplayName() + ".");
			target2.sendMessage(ChatColor.YELLOW + target1.getDisplayName() + " has been tp'd to you.");
			return true;
		} else {
			if (target1 == null) {
				player.sendMessage(ChatColor.RED + args[0] + " is not online or doesn't exist!");
			}
			if (target2 == null) {
				player.sendMessage(ChatColor.RED + args[1] + " is not online or doesn't exist!");
			}
			return false;
		}
	}
	
	public boolean tpToCoord(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.tp.coord")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return false;
		}
		Player player = (Player) sender;
		try {
			final double x = Double.parseDouble(args[0]);
			final double y = Double.parseDouble(args[1]);
			final double z = Double.parseDouble(args[2]);
			Location location = new Location(player.getWorld(),x,y,z);
			player.teleport(location);
			//TP to coordinates (arg1,arg2,arg3)
		}	catch (NumberFormatException ex) {
			player.sendMessage(ChatColor.RED + "Invalid Location");
			return false;
		}
		return true;
	}
	
	public boolean tpHere(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Cannot be used by non-player");
			return true;
		}
		Player player = (Player) sender;
		if ( !player.hasPermission("TestPlugin.tphere")) {
			player.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		
		
		
		if (args.length == 1) {
			Player bringTarget = player.getServer().getPlayer(args[0]);
			if (bringTarget == null) {
				player.sendMessage(ChatColor.RED + args[0] + "is not Online!");
				return false;
			}
			
			//If you try to change the location of a higher ranking.
			if (rc.isLess(player, bringTarget) && TestPlugin.pex) {
				sender.sendMessage(ChatColor.RED + "You can't TP a player with a higher rank than you!");
				return true;
			}
				
			Location location = player.getLocation();
			bringTarget.teleport(location);
			return true;
			
			
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid Arguments!");
			return false;
		}
	}

}
