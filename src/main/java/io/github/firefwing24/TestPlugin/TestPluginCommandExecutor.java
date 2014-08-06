package io.github.firefwing24.TestPlugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPluginCommandExecutor extends JavaPlugin implements CommandExecutor {
	private final TestPlugin plugin;
 
	public TestPluginCommandExecutor(TestPlugin plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("test")){
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("TestPlugin.test") || player.isOp()) {
					player.sendMessage("TestPlugin is working! Sent from Player");
					return true;
				}
				else {
					player.sendMessage("You don't have permission!");
					return true;
				}
			} else {
				sender.sendMessage("TestPlugin is working! Sent from non-player");
				return true;
			}
			
		}
		
		if (cmd.getName().equalsIgnoreCase("tp")) { //example tp command
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if ((args.length < 1) || (args.length > 3)) {
					sender.sendMessage("Invalid Arguments!");
					return false;
				}
				if (args.length == 1) {
					if (player.hasPermission("TestPlugin.tp.me")) {
						Player targetPlayer = player.getServer().getPlayer(args[0]);
						Location location = targetPlayer.getLocation();
						player.teleport(location);
					}
					else
						sender.sendMessage("You don't have permission!");
					//TP directly to argument player.
					return true;
				}
				else if (args.length == 2) {
					if (player.hasPermission("TestPlugin.tp.others")) {
						Player target1 = getServer().getPlayer(args[0]);
						Player target2 = getServer().getPlayer(args[1]);
						
						Location location = target2.getLocation();
						
						target1.teleport(location);
					}
					else
						sender.sendMessage("You don't have permission!");
					//TP from arg1 to arg2
					return true;
				}
				else if (args.length == 3) {
					if (player.hasPermission("TestPlugin.tp.coord"))
					{
						final double x = Double.parseDouble(args[0]);
						final double y = Double.parseDouble(args[1]);
						final double z = Double.parseDouble(args[2]);
						Location location = new Location(player.getWorld(),x,y,z);
						player.teleport(location);
						//TP to coordinates (arg1,arg2,arg3)
					}
					else
						sender.sendMessage("You don't have permission!");
					return true;
				}
				else
					return false;
			
			}
			
			else {
				if (args.length == 2) {
					Player target1 = getServer().getPlayer(args[0]);
					Player target2 = getServer().getPlayer(args[1]);
					
					Location location = target2.getLocation();
					
					target1.teleport(location);
					//TP arg1 to arg2
				}
				else {
					//Not usuable by console.
					sender.sendMessage("You can only use Player to Player teleportation!");
				}
			}
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
