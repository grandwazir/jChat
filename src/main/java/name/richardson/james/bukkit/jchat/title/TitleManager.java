/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TitleManager.java is part of jChat.

 jChat is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 jChat is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 jChat. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.jchat.title;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.utilities.listener.AbstractListener;
import name.richardson.james.bukkit.utilities.logging.PrefixedLogger;

public class TitleManager extends AbstractListener {

	public static final Logger LOGGER = PrefixedLogger.getLogger(TitleManager.class);

	public static String METADATA_PREFIX_KEY = "chatPrefix";
	public static String METADATA_SUFFIX_KEY = "chatSuffix";
	public static String PERMISSION_PREFIX = "jchat.title.";

	private final Plugin plugin;
	private final Server server;
	private Set<? extends TitleConfigurationEntry> titles;

	public TitleManager(Plugin plugin, PluginManager pluginManager, Server server, Set<? extends TitleConfigurationEntry> titles) {
		super(plugin, pluginManager);
		this.titles = titles;
		this.plugin = plugin;
		this.server = server;
	}

	public Set<? extends TitleConfigurationEntry> getTitles() {
		return titles;
	}

	public void setTitles(Set<? extends TitleConfigurationEntry> titles) {
		this.titles = titles;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		LOGGER.log(Level.FINEST, "Recieved " + event.getClass().getSimpleName());
		this.updateDisplayName(event.getPlayer(), false);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		LOGGER.log(Level.FINEST, "Recieved " + event.getClass().getSimpleName());
		this.updateDisplayName(event.getPlayer(), false);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onTitleRequestInvalidation(TitleRequestInvalidationEvent event) {
		LOGGER.log(Level.FINEST, "Recieved " + event.getClass().getSimpleName());
		this.updateDisplayName(event.getPlayer(), false);
	}

	public void refreshAll() {
		for (Player player : server.getOnlinePlayers()) {
			updateDisplayName(player, true);
		}
	}

	private void updateDisplayName(Player player, boolean recreateMetaData) {
		this.setMetaData(player, recreateMetaData);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(player.getMetadata(METADATA_PREFIX_KEY).get(0).asString());
		stringBuilder.append(player.getName());
		stringBuilder.append(player.getMetadata(METADATA_SUFFIX_KEY).get(0).asString());
		stringBuilder.append(ChatColor.RESET);
		LOGGER.log(Level.FINE, "Setting " + player.getName() + " display name to " + stringBuilder.toString());
		player.setDisplayName(stringBuilder.toString());
	}

	private void setMetaData(Player player, boolean recreateMetaData) {
		if (player.hasMetadata(METADATA_PREFIX_KEY) && !recreateMetaData) {
			player.getMetadata(METADATA_PREFIX_KEY).get(0).invalidate();
			LOGGER.log(Level.FINER, "Invalidating existing metadata for " + player.getName());
		} else {
			PlayerTitle playerTitle = new PlayerTitle(this, TitleConfigurationEntry.TitleType.PREFIX, player);
			LazyMetadataValue metadataValue = new LazyMetadataValue(plugin, playerTitle);
			player.setMetadata(METADATA_PREFIX_KEY, metadataValue);
			LOGGER.log(Level.FINER, "Created metadata for " + player.getName());
		}
		if (player.hasMetadata(METADATA_SUFFIX_KEY) && !recreateMetaData) {
			player.getMetadata(METADATA_SUFFIX_KEY).get(0).invalidate();
		} else {
			PlayerTitle playerTitle = new PlayerTitle(this, TitleConfigurationEntry.TitleType.SUFFIX, player);
			LazyMetadataValue metadataValue = new LazyMetadataValue(plugin, playerTitle);
			player.setMetadata(METADATA_SUFFIX_KEY, metadataValue);
		}
	}

}
