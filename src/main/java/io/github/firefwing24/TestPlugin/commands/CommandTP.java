package io.github.firefwing24.TestPlugin.commands;

import io.github.firefwing24.TestPlugin.PEXRankCheck;
import io.github.firefwing24.TestPlugin.TestPlugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.error.YAMLException;

public class CommandTP {

	private PEXRankCheck rc = new PEXRankCheck();
	private long keepAlive = 600;
	TestPlugin plugin = new TestPlugin();
	
	public Map<String,String> requests = new HashMap<String,String>();

	public CommandTP() {
	}

	public boolean tpToPlayer(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.tp.me")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
			return true;
		}
		Player player = (Player) sender;
		Player target = player.getServer().getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatColor.RED + args[0] + " is not Online");
			return false;
		}
		if (target == player) {
			player.sendMessage(ChatColor.RED + "You cannot teleport to yourself!");
			return false;
		}
		player.teleport(target);
		return true;
	}

	
	public boolean tpP2P(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.tp.others")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		Player player = (Player) sender;
		Player target1 = player.getServer().getPlayer(args[0]);
		Player target2 = player.getServer().getPlayer(args[1]);
		// If you try to change the location of a higher ranking.
		if (rc.isLess(player, target1) && TestPlugin.pex) {
			sender.sendMessage(ChatColor.RED
					+ "You can't TP a player with a higher rank than you!");
			return true;
		}
		if (target1 == target2) {
			sender.sendMessage(ChatColor.RED + "You can't tp a person to the same person!");
		}
		if (target1 != null && target2 != null) {
			target1.teleport(target2);
			target1.sendMessage(ChatColor.YELLOW + "You have been tp'd to "
					+ target2.getDisplayName() + ".");
			target2.sendMessage(ChatColor.YELLOW + target1.getDisplayName()
					+ " has been tp'd to you.");
			return true;
		} else {
			if (target1 == null) {
				sender.sendMessage(ChatColor.RED + args[0] + " is not Online");
			}
			else if (target2 == null) {
				sender.sendMessage(ChatColor.RED + args[0] + " is not Online");
			}
			return false;
		}
	}

	public boolean tpToCoord(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.tp.coord")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
			return true;
		}
		Player player = (Player) sender;
		try {
			final double x = Double.parseDouble(args[0]);
			final double y = Double.parseDouble(args[1]);
			final double z = Double.parseDouble(args[2]);
			Location location = new Location(player.getWorld(), x, y, z);
			player.teleport(location);
			// TP to coordinates (arg1,arg2,arg3)
		} catch (NumberFormatException ex) {
			player.sendMessage(ChatColor.RED + "Invalid Location");
			return false;
		}
		return true;
	}

	public boolean tpHere(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
			return true;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("TestPlugin.tphere")) {
			player.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}

		if (args.length == 1) {
			Player bringTarget = player.getServer().getPlayer(args[0]);
			if (bringTarget == null) {
				player.sendMessage(ChatColor.RED + args[0] + " is not Online");
				return false;
			}

			// If you try to change the location of a higher ranking.
			if (rc.isLess(player, bringTarget) && TestPlugin.pex) {
				sender.sendMessage(ChatColor.RED
						+ "You can't TP a player with a higher rank than you!");
				return true;
			}
			if (bringTarget == player) {
				player.sendMessage(ChatColor.RED + "You cannot teleport to yourself!");
				return false;
			}

			bringTarget.teleport(player);
			return true;

		} else {
			sender.sendMessage(ChatColor.RED + "Invalid Arguments!");
			return false;
		}
	}
	
	public boolean tpa(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
			return true;
		}
		if (!sender.hasPermission("TestPlugin.tpa")){
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		if (args.length != 1) {
			return false;
		}
		
		Player sent = (Player) sender;
		final Player receiver = Bukkit.getServer().getPlayer(args[0]);
		if (receiver == null) {
			sent.sendMessage(ChatColor.RED + args[0] + " is not Online");
			return false;
		}

		if (receiver == sent) {
			sent.sendMessage(ChatColor.RED + "You cannot teleport to yourself!");
			return false;
		}
		String sentName = sent.getName();
		final String receiverName = receiver.getName();
		requests.put(receiverName, sentName);
		sent.sendMessage(ChatColor.GREEN + "Request Sent to " + receiverName);
		receiver.sendMessage(ChatColor.YELLOW + "You receive a request to teleport from " + sentName);
		receiver.sendMessage(ChatColor.YELLOW + "To accept the teleport request, type " +  ChatColor.RED + "/tpaccept" + ChatColor.YELLOW + ".");
		receiver.sendMessage(ChatColor.YELLOW + "To deny the teleport request, type " +  ChatColor.RED + "/tpdeny" + ChatColor.YELLOW + ".");
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin , new Runnable() {
			@Override
			public void run() {
				killRequest(receiverName);
			}
		}, 600L);
		
		return true;
	}
	
	public boolean tpaccept(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GRAY + "Console can't accept tp requests");
			return true;
		}
		if (!sender.hasPermission("TestPlugin.tpa")){
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		Player receiver = (Player) sender;
		String receiverName = receiver.getName();
		if (requests.containsKey(receiverName)) {
			Player sent = Bukkit.getServer().getPlayer(requests.get(receiverName));
			requests.remove(receiverName);
			if (!(sent == null)) {
				sent.teleport(receiver);
				sent.sendMessage("Your request has been accepted");
				receiver.sendMessage("You have accepted the request");
				return true;
			} else {
				receiver.sendMessage(ChatColor.RED + "It seems the player that requested has logged out.");
				return true;
			}
		
		} else {
			sender.sendMessage(ChatColor.RED + "It doesn't appear that there are any current tp requests. Maybe it timed out?");
			return true;
		}
	}
	
	public boolean tpdeny(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
			return true;
		}
		if (!sender.hasPermission("TestPlugin.tpa")){
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}

		Player receiver = (Player) sender;
		String receiverName = receiver.getName();
		if (requests.containsValue(receiverName)) {
			if (requests.get(receiverName) != null) {
				Player sent = Bukkit.getServer().getPlayer(requests.get(receiverName));
				requests.remove(receiverName);
				if (sent == null) {
					receiver.sendMessage(ChatColor.RED + "It seems the player that requested has logged out.");
					return true;
				} else {
					receiver.sendMessage(ChatColor.RED + "You have denied the request");
					sent.sendMessage(ChatColor.RED + "Your Request has been denied");
					return true;
				}
			}
		} else {
			receiver.sendMessage(ChatColor.RED + "It doesn't appear that there are any current tp requests. Maybe it timed out?");
			return true;
		}
		return true;
		
	}
	public boolean killRequest(String key) {
		if (requests.containsKey(key)) {
			Player denied = Bukkit.getServer().getPlayer(requests.get(key));
			if (!(denied == null)) {
				denied.sendMessage(ChatColor.RED + "Your teleport request timed out.");
			}
			
			requests.remove(key);
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean spawn(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GRAY + "Console can't spawn.");
			return true;
		}
		if (!sender.hasPermission("TestPlugin.spawn")){
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		Player player = (Player) sender;
		double x, y, z;
		String w = null;
		float yaw, pitch;
		try {
			w = plugin.getConfig().getString("TPManagement.Spawn.world");
			World world = Bukkit.getServer().getWorld(w);
			if (world == null) {
				player.sendMessage(ChatColor.RED + "A spawn hasn't been set yet!");
				return true;
			}
			x = plugin.getConfig().getDouble("TPManagement.Spawn.x");
			y = plugin.getConfig().getDouble("TPManagement.Spawn.y");
			z = plugin.getConfig().getDouble("TPManagement.Spawn.z");
			yaw = (float) plugin.getConfig().getDouble("TPManagement.Spawn.yaw");
			pitch = (float) plugin.getConfig().getDouble("TPManagement.Spawn.pitch");
			
			
			Location location = new Location(world, x, y, z, yaw, pitch);
			player.teleport(location);
			
		} catch (YAMLException ex) {
			Bukkit.getConsoleSender().sendMessage("YAML ERROR");
		}
		
		return true;
	}
	
	public boolean setSpawn(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GRAY + "Console can't set a spawn.");
			return true;
		}
		if (!sender.hasPermission("TestPlugin.setspawn")){
			sender.sendMessage(ChatColor.RED + "You don't have permissions!");
			return true;
		}
		Player player = (Player) sender;
		Location location = player.getLocation();
		double x, y, z, yaw, pitch;
		String world = location.getWorld().getName();
		x = location.getX();
		y = location.getY();
		z = location.getZ();
		yaw = (double) location.getYaw();
		pitch = (double) location.getPitch();
		
		plugin.getConfig().set("TPManagement.Spawn.world", world);
		plugin.getConfig().set("TPManagement.Spawn.x", x);
		plugin.getConfig().set("TPManagement.Spawn.y", y);
		plugin.getConfig().set("TPManagement.Spawn.z", z);
		plugin.getConfig().set("TPManagement.Spawn.yaw", yaw);
		plugin.getConfig().set("TPManagement.Spawn.pitch", pitch);
		
		plugin.loadYamls();
		
		return true;
	}

}
