package io.github.firefwing24.TestPlugin;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatEvent implements Listener{
	
	public ChatEvent(TestPlugin plugin) { 
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onMessage(AsyncPlayerChatEvent e){
		String prefix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getPrefix();
		e.setFormat(prefix + ChatColor.RESET + "%1$s: %2$s");
		
	}
}
