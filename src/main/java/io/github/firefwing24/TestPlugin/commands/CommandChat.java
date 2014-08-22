package io.github.firefwing24.TestPlugin.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChat {

	public CommandChat() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean whisper(CommandSender sender, Command cmd, String label, String args[]) {
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
			player.sendMessage(ChatColor.RED + args[0] + "is not Online");
			return false;
		} else {

			String message = StringUtils.join(args, ' ', 1, args.length);

			target.sendMessage(ChatColor.GRAY + "[Whisper: "
					+ sender.getName() + "]: " + message);
			return true;
		}
	}

}
