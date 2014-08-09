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
	private Teleportation tp = new Teleportation();

	public static List<String> godToggleList = new ArrayList<String>();
	
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
				if (player.hasPermission("TestPlugin.test")) {
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
				if ((args.length < 1) || (args.length > 3)) {
					sender.sendMessage(ChatColor.RED + "Invalid Arguments!");
					return false;
				}
				if (args.length == 1) 
					return tp.tpToPlayer(sender, cmd, label, args);
				
				
				else if (args.length == 2)
					return tp.tpP2P(sender, cmd, label, args);
				
				else if (args.length == 3) {
					return tp.tpToCoord(sender, cmd, label, args);
				}
				else
					return false;
			
			}
			
			else {
				if (args.length == 2)
					return tp.tpP2P(sender, cmd, label, args);
				
				else 
					//Not usuable by console.
					sender.sendMessage("You can only use Player to Player teleportation!");
			}
			return false;
		}
		
		//TPHERE COMMAND
		if (cmd.getName().equalsIgnoreCase("tphere"))
			return tp.tpHere(sender, cmd, label, args);
		
		
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
			if (target == null) {
				player.sendMessage(ChatColor.RED + args[0] + "is not online or doesn't exist!");
				return false;
			} else {
				
				String message = StringUtils.join(args, ' ',1,args.length);
				
				target.sendMessage(ChatColor.GRAY + "[Whisper: " +sender.getName() + "]: " + message);
				return true;
			}
			
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
					player.sendMessage(ChatColor.RED + args[0] + "is not online or doesn't exist!");
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
					sender.sendMessage(ChatColor.RED + args[0] + "is not online or doesn't exist!");
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
					player.setFoodLevel(20);
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
						player.sendMessage(ChatColor.RED + args[0] + "is not online or doesn't exist!");
					}
						
					return false;
				}
				
				double health = target.getMaxHealth();
				target.setFoodLevel(20);
				target.setHealth(health);
				target.sendMessage(ChatColor.YELLOW + "Healed!");
				return true;
			} else 
				return false;
			
		}
		
		
		//BROADCAST COMMAND
		if (cmd.getName().equalsIgnoreCase("broadcast")) {
			if (!sender.hasPermission("TestPlugin.broadcast")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			if (args.length == 0)
				return false;
			String message = StringUtils.join(args, " ", 1, args.length);
			Bukkit.broadcastMessage(message);
			return true;
		}
		
		//GOD COMMAND
		if (cmd.getName().equalsIgnoreCase("god")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage("Cannot be used by non-player");
				return true;
			}
			
			Player player = (Player) sender;
			if (args.length == 0) {
				if (!sender.hasPermission("TestPlugin.god.me")) {
					sender.sendMessage(ChatColor.RED + "You don't have permission!");
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
					sender.sendMessage(ChatColor.RED + "You don't have permission!");
					return true;
				}
				Player target = player.getServer().getPlayer(args[0]);
				if (target == null) {
					player.sendMessage(ChatColor.RED + args[0] + "is not online or doesn't exist!");
					return false;
				} else {

					if (!(godToggleList.contains(target.getName()))) {
						godToggleList.add(target.getName());
						player.sendMessage(ChatColor.GREEN + "Godmode Enabled!");
					} else {
						godToggleList.remove(target.getName());
						player.sendMessage(ChatColor.GREEN + "Godmode Disabled!");
					}
					return true;
				}
				
			}
			
			else {
				player.sendMessage(ChatColor.RED + "Invalid Parameters");
				return false;
			}
			
		}
		
		
		//
		//args.length is number of arguments. args is an array :)
		/*
		 * if (arg.length > 4) would mean they gave more than 3 syntax stuff which autofails the command
		 * 
		 * 
		 */
		
		sender.sendMessage("Something went wrong with the command. This message means that there's a boolean leak in the command.");
		return false;
	}
}
