package io.github.firefwing24.TestPlugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
				}
				else {
					player.sendMessage(ChatColor.RED + "You don't have permission!");
				}
			} else {
				sender.sendMessage("TestPlugin is working! Sent from non-player");
			}
			return true;
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
						return false;
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
					player.sendMessage(ChatColor.YELLOW + "Online Players: " + ChatColor.WHITE + onlinePlayers.toString());
					onlinePlayers.clear();
				}
			}
			else {
				for (Player p : Bukkit.getOnlinePlayers()) {
					onlinePlayers.add(p.getName());
				}
				sender.sendMessage(ChatColor.YELLOW + "Online Players: " + ChatColor.WHITE + onlinePlayers.toString());
				onlinePlayers.clear();
			}
			return true;
		}
		
		//Whisper Command
		
		if (cmd.getName().equalsIgnoreCase("w")) {
			if (!sender.hasPermission("TestPlugin.whisper")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
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
				return false;
			} else if (target.isOnline()) {
				
				String message = StringUtils.join(args, ' ',1,args.length);
				
				target.sendMessage(ChatColor.GRAY + "[Whisper: " +sender.getName() + "]: " + message);
				return true;
			}
			else
				return false;
			
		}
		
		//Kill Command
		if (cmd.getName().equalsIgnoreCase("kill")) {
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
					sender.sendMessage(ChatColor.RED + args[0] + "is not online");
					return false;
				} else {
					target.setHealth(0);
				}
				return true;
			} else {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (args.length < 1)
					return false;
				if (target == null) {
					sender.sendMessage(ChatColor.RED + args[0] + "is not online");
					return false;
				} else {
						
					target.setHealth(0);
				}
				return true;
			}
			
			
			
		}
		
		//SetHome Command
		if (cmd.getName().equalsIgnoreCase("sethome")) {
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
		
		
		//HOME COMMAND
		if (cmd.getName().equalsIgnoreCase("home")) {
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
		
		//FLY COMMAND
		if (cmd.getName().equalsIgnoreCase("fly")) {
			if (!sender.hasPermission("TestPlugin.fly")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("This can only be used by in-game Players");
				return true;	
			}
			
			if (args.length > 1)
				return false;
			
			Player player = (Player) sender;
			//Set Player to allow flight
			if (args.length == 0) {
				if (!sender.hasPermission("TestPlugin.fly.me")) {
					sender.sendMessage(ChatColor.RED + "You don't have permission!");
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
			
			//Allow different player to fly
			else if (args.length == 1) {
				if (!sender.hasPermission("TestPlugin.fly.others")) {
					sender.sendMessage(ChatColor.RED + "You don't have permission!");
					return true;
				}
				
				Player target = player.getServer().getPlayer(args[0]);
				if (target == null) {
					sender.sendMessage(ChatColor.RED + args[0] + "is not online");
					return false;
				}
				if (target.getAllowFlight()) {
					target.setAllowFlight(false);
					player.sendMessage(ChatColor.AQUA + "Flying Disabled!");
				} else {
					player.setAllowFlight(true);
					player.sendMessage(ChatColor.AQUA + "Flying Enabled!");
				}
				return true;
			}
			
			return false;
		}
		
		//HEAL COMMAND
		if (cmd.getName().equalsIgnoreCase("heal")) {
			if (args.length > 1) {
				sender.sendMessage("Invalid Parameters!");
				return false;
			}
			if (!(sender instanceof Player)) {
				if (args.length == 0) {
					sender.sendMessage("Can't Heal Non-Player!");
					return false;
				}
				
			} else {
				if (args.length == 0) {
					Player player = (Player) sender;
					double health = player.getMaxHealth();
					player.setHealth(health);
					player.sendMessage(ChatColor.YELLOW + "Healed!");
					return true;
				}
			}
			
			if (args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target == null) {
					if (!(sender instanceof Player))
						sender.sendMessage(args[0] + "is not Online!");
					else {
						Player player = (Player) sender;
						player.sendMessage(ChatColor.RED + args[0] + "is not Online!");
					}
						
					return false;
				}
				
				double health = target.getMaxHealth();
				target.setHealth(health);
				target.sendMessage(ChatColor.YELLOW + "Healed!");
				return true;
			} else 
				return false;
			
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
