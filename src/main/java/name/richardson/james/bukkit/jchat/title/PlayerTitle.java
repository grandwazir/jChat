package name.richardson.james.bukkit.jchat.title;

import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class PlayerTitle implements Callable<Object> {

	private final String playerName;
	private final Server server;
	private final TitleConfigurationEntry.TitleType titleType;
	private final TitleManager titleManager;

	public PlayerTitle(TitleManager titleManager, TitleConfigurationEntry.TitleType title, Server server, String playerName) {
		this.titleManager = titleManager;
		this.titleType = title;
		this.server = server;
		this.playerName = playerName;
	}

	public String call() {
		for (TitleConfigurationEntry entry : titleManager.getTitles()) {
			boolean permitted = this.server.getPlayerExact(playerName).hasPermission(TitleManager.PERMISSION_PREFIX + entry.getName());
			String title = entry.getTitle(titleType);
			if (permitted) return title;
		}
		return "";
	}

}
