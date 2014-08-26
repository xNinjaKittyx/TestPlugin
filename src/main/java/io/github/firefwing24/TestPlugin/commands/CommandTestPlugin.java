package io.github.firefwing24.TestPlugin.commands;

import io.github.firefwing24.TestPlugin.TestPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandTestPlugin {
	TestPlugin plugin;
	
	public boolean test(CommandSender sender, Command cmd, String label, String[] args){
		if (sender.hasPermission("TestPlugin.tpc.test")){
			sender.sendMessage("TestPlugin is online and working !");
		}
		else
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
	
		return true;
	}
	
	public boolean reload(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TestPlugin.tpc.reload")){
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		plugin.loadYamls();
		sender.sendMessage(ChatColor.GREEN + "Configs Reloaded!");
		return true;
	}
}
