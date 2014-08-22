package io.github.firefwing24.TestPlugin.events;

import io.github.firefwing24.TestPlugin.TestPlugin;
import io.github.firefwing24.TestPlugin.commands.CommandGod;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodEvent implements Listener {

	private CommandGod god = new CommandGod();

	public GodEvent(TestPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerDeath(EntityDamageEvent e) {
		Entity ent = e.getEntity();

		if (ent instanceof Player) {
			if (god.godToggleList.contains(((Player) ent)
					.getName()))
				e.setCancelled(true);
		} else {
			e.setCancelled(false);
		}
	}

}
