package io.github.firefwing24.TestPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatEvent implements Listener{
	
	public ChatEvent(TestPlugin plugin) { 
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onMessage(AsyncPlayerChatEvent e){
		Player player = e.getPlayer();
		PermissionUser user = PermissionsEx.getUser(player);
		if (user.getPrefix() != null)
			e.setFormat(user.getPrefix() + ChatColor.RESET + "%1$s: %2$s");
		else
			e.setFormat(player.getDisplayName() + ChatColor.RESET + "%1$s: %2$s");
		
	}
}
