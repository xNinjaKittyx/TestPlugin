package io.github.firefwing24.TestPlugin;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatEvent implements Listener{
	public ChatEvent(TestPlugin plugin) { 
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onMessage(AsyncPlayerChatEvent e){
		
		if (e.isCancelled()) {
			return;
		}
		
		Player player = e.getPlayer();
		
		String WorldName = player.getWorld().getName();
		PermissionUser user = PermissionsEx.getPermissionManager().getUser(player);
		
		if (user == null)
			return;
		
		String message = e.getMessage();
		String format = "%prefix%name%suffix: %message";
		format.replace("%prefix",user.getPrefix(WorldName));
		format.replace("%suffix",user.getSuffix(WorldName));
		format.replace("%name", "%1$s");
		format.replace("%message", "%2$s");
		
		e.setFormat(format);
		e.setMessage(message);
		
		//e.setFormat(ChatColor.WHITE + "<" + prefix + "%1$s" + suffix + ChatColor.WHITE + "> %2$s");
		
		
	}
}
