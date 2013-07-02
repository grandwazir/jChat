/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 *
 * MessagesManager.java is part of jChatPlugin.
 *
 * jChatPlugin is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * jChatPlugin is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * jChatPlugin. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.jchat.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.utilities.listener.AbstractListener;

public class MessagesManager extends AbstractListener {

	private final MessagesConfiguration configuration;

	public MessagesManager(Plugin plugin, PluginManager pluginManager, MessagesConfiguration configuration) {
		super(plugin, pluginManager);
		this.configuration = configuration;
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onEntityDeath(final EntityDeathEvent event) {
		if ((event instanceof PlayerDeathEvent) && this.configuration.isColouringDeathMessages()) {
			final PlayerDeathEvent deathEvent = (PlayerDeathEvent) event;
			final Player player = deathEvent.getEntity();
			deathEvent.setDeathMessage((this.colourMessage(player, deathEvent.getDeathMessage())));
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		if (!this.configuration.isColouringJoinMessages()) return;
		final Player player = event.getPlayer();
		event.setJoinMessage((this.colourMessage(player, event.getJoinMessage())));
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerQuit(final PlayerQuitEvent event) {
		if (!this.configuration.isColouringQuitMessages()) {
			return;
		}
		final Player player = event.getPlayer();
		event.setQuitMessage((this.colourMessage(player, event.getQuitMessage())));
	}

	private String colourMessage(final Player player, final String message) {
		return message.replace(player.getName(), player.getDisplayName() + ChatColor.YELLOW);
	}

}
