package io.github.firefwing24.TestPlugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestPluginCommandExecutor implements CommandExecutor {
	private final TestPlugin plugin;
	ArrayList <String> onlinePlayers = new ArrayList <String>();
 
	public TestPluginCommandExecutor(TestPlugin plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		//TEST COMMAND
		if (cmd.getName().equalsIgnoreCase("test")){
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("TestPlugin.test") || player.isOp()) {
					player.sendMessage(ChatColor.GREEN + "TestPlugin is working! Sent from Player");
					return true;
				}
				else {
					player.sendMessage(ChatColor.RED + "You don't have permission!");
					return true;
				}
			} else {
				sender.sendMessage("TestPlugin is working! Sent from non-player");
				return true;
			}
			
		}
		
		//TP COMMAND
		if (cmd.getName().equalsIgnoreCase("tp")) { //example tp command
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if ((args.length < 1) || (args.length > 3)) {
					sender.sendMessage(ChatColor.RED + "Invalid Arguments!");
					return false;
				}
				if (args.length == 1) {
					if (player.hasPermission("TestPlugin.tp.me")) {
						Player targetPlayer = player.getServer().getPlayer(args[0]);
						if (targetPlayer.isOnline()) {
							Location location = targetPlayer.getLocation();
							player.teleport(location);
						} else {
							player.sendMessage(ChatColor.RED + args[0] + " is not Online!");
						}
					}
					else
						sender.sendMessage(ChatColor.RED + "You don't have permission!");
					//TP directly to argument player.
					return true;
				}
				else if (args.length == 2) {
					if (player.hasPermission("TestPlugin.tp.others")) {
						Player target1 = player.getServer().getPlayer(args[0]);
						Player target2 = player.getServer().getPlayer(args[1]);
						if (target1.isOnline() && target2.isOnline()) {
							Location location = target2.getLocation();
							target1.teleport(location);
							target1.sendMessage(ChatColor.YELLOW + "You have been teleported to " + target2.getDisplayName());
						} else {
							if (!target1.isOnline()) {
								player.sendMessage(ChatColor.RED + args[0] + "is not Online!");
							}
							if (!target2.isOnline()) {
								player.sendMessage(ChatColor.RED + args[1] + "is not Online!");
							}
						}
					}
					else
						sender.sendMessage(ChatColor.RED + "You don't have permission!");
					//TP from arg1 to arg2
					return true;
				}
				else if (args.length == 3) {
					if (player.hasPermission("TestPlugin.tp.coord"))
					{
						try {
							final double x = Double.parseDouble(args[0]);
							final double y = Double.parseDouble(args[1]);
							final double z = Double.parseDouble(args[2]);
							Location location = new Location(player.getWorld(),x,y,z);
							player.teleport(location);
							//TP to coordinates (arg1,arg2,arg3)
						}	catch (NumberFormatException ex) {
							player.sendMessage(ChatColor.RED + "Invalid Location");
						}
						return true;
					}
					else
						sender.sendMessage(ChatColor.RED + "You don't have permission!");
					return true;
				}
				else
					return false;
			
			}
			
			else {
				if (args.length == 2) {
					
					Player target1 = sender.getServer().getPlayer(args[0]);
					Player target2 = sender.getServer().getPlayer(args[1]);
					if (target1.isOnline() && target2.isOnline()) {
						Location location = target2.getLocation();
						target1.teleport(location);
						target1.sendMessage(ChatColor.YELLOW + "You have been teleported to " + target2.getDisplayName());
					} else {
						if (!target1.isOnline()) {
							sender.sendMessage(ChatColor.RED + args[0] + "is not Online!");
						}
						if (!target2.isOnline()) {
							sender.sendMessage(ChatColor.RED + args[1] + "is not Online!");
						}
					}
					//TP arg1 to arg2
				}
				else {
					//Not usuable by console.
					sender.sendMessage("You can only use Player to Player teleportation!");
				}
			}
			return false;
		}
		
		//WHO COMMAND
		if (cmd.getName().equalsIgnoreCase("who")) {
			if (sender instanceof Player)
			{
				Player player = (Player) sender;
				if (player.hasPermission("TestPlugin.who"))
				{
					for (Player p : Bukkit.getOnlinePlayers()) {
						onlinePlayers.add(p.getName());
					}
					sender.sendMessage(ChatColor.YELLOW + "Online Players: " + ChatColor.WHITE + onlinePlayers.toString());
				
				}
			}
		}
		
		//Whisper Command
		
		if (cmd.getName().equalsIgnoreCase("w")) {
			if (!sender.hasPermission("TestPlugin.whisper")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
			}
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("This can only be used by in-game Players");
				return true;
			}
			

			Player player = (Player) sender;
			if (args.length < 2) {
				return false;
			}
			
			Player target = player.getServer().getPlayer(args[0]);
			if (!target.isOnline()) {
				player.sendMessage(ChatColor.RED + args[0] + " is not online!");
				return true;
			} else {
				
				target.sendMessage(player.getName() + ": ");
				ArrayList <String> message = new ArrayList <String>();
				for (int i=1; i<args.length;i++)
				{
					message.add(args[i]);
				}
				
				target.sendMessage(sender.getName() + ": " + message.toString());
				return true;
			}
			
		}
		
		if (cmd.getName().equalsIgnoreCase("kill")) {
			if (!sender.hasPermission("TestPlugin.kill")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
			}
			Player target = sender.getServer().getPlayer(args[0]);
			
			if (args.length < 1)
			
			if (target == null) {
				sender.sendMessage(ChatColor.RED + args[0] + "is not online");
			} else {
				
				target.setHealth(0);
			}
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("sethome")) {
			if (!sender.hasPermission("TestPlugin.sethome")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
			}
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("This can only be used by in-game Players");
				return true;	
			}
			
			Player player = (Player) sender;
			Location location = player.getLocation();
			player.setBedSpawnLocation(location);
			player.sendMessage(ChatColor.GREEN + "Home Set!");
		}
		
		if (cmd.getName().equalsIgnoreCase("home")) {
			if (!sender.hasPermission("TestPlugin.home")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
			}
			
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("This can only be used by in-game Players");
				return true;	
			}
			
			Player player = (Player) sender;
			Location location = player.getBedSpawnLocation();
			player.teleport(location);
			player.sendMessage(ChatColor.GREEN + "Teleported to Home");
		}
		
		
		
		//args.length is number of arguments. args is an array :)
		/*
		 * if (arg.length > 4) would mean they gave more than 3 syntax stuff which autofails the command
		 * 
		 * 
		 */
		
		
		return false;
	}
}
