package name.richardson.james.bukkit.jchat.title;

import java.util.Set;
import java.util.concurrent.Callable;
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
	private final Set<? extends TitleConfigurationEntry> titles;

	public TitleManager(Plugin plugin, PluginManager pluginManager, Server server, Set<? extends TitleConfigurationEntry> titles) {
		super(plugin, pluginManager);
		this.titles = titles;
		this.plugin = plugin;
		this.server = server;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		LOGGER.log(Level.FINEST, "Recieved " + event.getClass().getSimpleName());
		this.updateDisplayName(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		LOGGER.log(Level.FINEST, "Recieved " + event.getClass().getSimpleName());
		this.updateDisplayName(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onTitleRequestInvalidation(TitleRequestInvalidationEvent event) {
		LOGGER.log(Level.FINEST, "Recieved " + event.getClass().getSimpleName());
		this.updateDisplayName(event.getPlayer());
	}

	public void refreshAll() {
		for (Player player : server.getOnlinePlayers()) {
			updateDisplayName(player);
		}
	}

	private void updateDisplayName(Player player) {
		this.setMetaData(player);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(player.getMetadata(METADATA_PREFIX_KEY).get(0).asString());
		stringBuilder.append(player.getName());
		stringBuilder.append(player.getMetadata(METADATA_SUFFIX_KEY).get(0).asString());
		stringBuilder.append(ChatColor.RESET);
		LOGGER.log(Level.FINE, "Setting " + player.getName() + " display name to " + stringBuilder.toString());
		player.setDisplayName(stringBuilder.toString());
	}

	private void setMetaData(Player player) {
		if (player.hasMetadata(METADATA_PREFIX_KEY)) {
			player.getMetadata(METADATA_PREFIX_KEY).get(0).invalidate();
			LOGGER.log(Level.FINER, "Invalidating existing metadata for " + player.getName());
		} else {
			PlayerTitle playerTitle = new PlayerTitle(TitleConfigurationEntry.TitleType.PREFIX, player);
			LazyMetadataValue metadataValue = new LazyMetadataValue(plugin, playerTitle);
			player.setMetadata(METADATA_PREFIX_KEY, metadataValue);
			LOGGER.log(Level.FINER, "Created metadata for " + player.getName());
		}
		if (player.hasMetadata(METADATA_SUFFIX_KEY)) {
			player.getMetadata(METADATA_SUFFIX_KEY).get(0).invalidate();
		} else {
			PlayerTitle playerTitle = new PlayerTitle(TitleConfigurationEntry.TitleType.SUFFIX, player);
			LazyMetadataValue metadataValue = new LazyMetadataValue(plugin, playerTitle);
			player.setMetadata(METADATA_SUFFIX_KEY, metadataValue);
		}
	}

	public class PlayerTitle implements Callable<Object> {

		private final Player player;
		private final TitleConfigurationEntry.TitleType titleType;

		public PlayerTitle(TitleConfigurationEntry.TitleType title, Player player) {
			this.titleType = title;
			this.player = player;
		}

		public String call() {
			for (TitleConfigurationEntry entry : titles) {
				boolean permitted = player.hasPermission(PERMISSION_PREFIX + entry.getName());
				LOGGER.log(Level.FINEST, "Does " + player.getName() + " have permission for " + entry.getName() + "? " + permitted);
				if (permitted) return entry.getTitle(titleType);
			}
			return "";
		}

	}

}
