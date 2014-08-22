package io.github.firefwing24.TestPlugin;

import io.github.firefwing24.TestPlugin.commands.*;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestPluginCommandExecutor implements CommandExecutor {
	private final TestPlugin plugin;
	private CommandTP tp = new CommandTP();
	private UserManagement um = new UserManagement();
	private TestPcmd tpc = new TestPcmd();
	private CommandKill kill = new CommandKill();
	private CommandHome home = new CommandHome();
	private CommandChat chat = new CommandChat();
	private CommandFly fly = new CommandFly();
	private CommandHeal heal = new CommandHeal();
	private CommandGod god = new CommandGod();
	private CommandBroadcast broadcast = new CommandBroadcast();
	
	public TestPluginCommandExecutor(TestPlugin plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need
								// it.
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// TP COMMAND
		if (cmd.getName().equalsIgnoreCase("tp")) { // example tp command
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
				} else
					return false;

			}

			else {
				if (args.length == 2)
					return tp.tpP2P(sender, cmd, label, args);

				else
					// Not usuable by console.
					sender.sendMessage("You can only use Player to Player teleportation!");
			}
			return false;
		}

		// TPHERE COMMAND
		else if (cmd.getName().equalsIgnoreCase("tphere"))
			return tp.tpHere(sender, cmd, label, args);
		
		//TPA COMMANDS
		else if (cmd.getName().equalsIgnoreCase("tpa"))
			return tp.tpa(sender, cmd, label, args);
		
		else if (cmd.getName().equalsIgnoreCase("tpaccept"))
			return tp.tpaccept(sender, cmd, label, args);
		
		else if (cmd.getName().equalsIgnoreCase("tpdeny"))
			return tp.tpdeny(sender, cmd, label, args);

		// WHO COMMAND
		else if (cmd.getName().equalsIgnoreCase("who"))
			return um.who(sender, cmd, label, args);

		// WHISPER COMMAND
		else if (cmd.getName().equalsIgnoreCase("w"))
			return chat.whisper(sender, cmd, label, args);

		// KILL COMMAND
		else if (cmd.getName().equalsIgnoreCase("kill")) 
			return kill.kill(sender, cmd, label, args);

		// SETHOME COMMAND
		else if (cmd.getName().equalsIgnoreCase("sethome"))
			return home.sethome(sender, cmd, label, args);

		// HOME COMMAND
		else if (cmd.getName().equalsIgnoreCase("home"))
			return home.home(sender, cmd, label, args);

		// FLY COMMAND
		else if (cmd.getName().equalsIgnoreCase("fly"))
			return fly.fly(sender, cmd, label, args);

		// HEAL COMMAND
		else if (cmd.getName().equalsIgnoreCase("heal"))
			return heal.heal(sender, cmd, label, args);

		// BROADCAST COMMAND
		else if (cmd.getName().equalsIgnoreCase("broadcast"))
			return broadcast.broadcast(sender, cmd, label, args);

		// GOD COMMAND
		else if (cmd.getName().equalsIgnoreCase("god"))
			return god.god(sender, cmd, label, args);

		// KICK COMMAND
		else if (cmd.getName().equalsIgnoreCase("kick"))
			return um.kick(sender, cmd, label, args);
		
		//TESTP COMMAND
		else if (cmd.getName().equalsIgnoreCase("TestP")) {
			if (args.length == 0) {
				return tpc.test(sender, cmd, label, args);
			}
			
			else {
				if (args[0] == "reload") {
					return tpc.reload(sender, cmd, label, args);
				}
				else {
					sender.sendMessage(ChatColor.RED + "Invalid Command!");
					return false;
				}
			}
		}
		
		//Repair Command
		else if (cmd.getName().equalsIgnoreCase("repair")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
				return true;
			}
			if (!sender.hasPermission("TestPlugin.repair")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			Player player = (Player) sender;
			ItemStack i = player.getItemInHand();
			i.setDurability((short)0);
			player.sendMessage(ChatColor.GREEN + "Repaired!");
			return false;
		}
		
		else if (cmd.getName().equalsIgnoreCase("speed")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.GRAY + "Cannot be used by Non-Player");
				return true;
			}
			if (!sender.hasPermission("TestPlugin.speed")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			Player player = (Player) sender;
			return false;
			
			
			
		}
		
		else if (cmd.getName().equalsIgnoreCase("viewinv")) {
			return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("")) {
			return false;
		}

		//
		// args.length is number of arguments. args is an array :)
		/*
		 * if (arg.length > 4) would mean they gave more than 3 syntax stuff
		 * which autofails the command
		 */

		sender.sendMessage("Something went wrong with the command. This message means that there's a boolean leak in the command.");
		return false;
	}
}
