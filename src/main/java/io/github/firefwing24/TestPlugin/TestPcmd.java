package io.github.firefwing24.TestPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TestPcmd {
	private  TestPlugin plugin;

	public TestPcmd() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean test(CommandSender sender, Command cmd, String label, String[] args){
		if (sender.hasPermission("TestPlugin.testpcmd.test")){
			sender.sendMessage("TestPlugin is online and working !");
		}
		else
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
	
		return true;
	}
	
	public boolean reload(CommandSender sender, Command cmd, String label, String[] args) {
		plugin.reloadConfig();
		sender.sendMessage("TestPlugin Config Reloaded!");
		return true;
	}
}
