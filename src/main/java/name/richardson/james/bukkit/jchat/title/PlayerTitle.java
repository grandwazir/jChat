package name.richardson.james.bukkit.jchat.title;

import java.util.concurrent.Callable;
import java.util.logging.Level;

import org.bukkit.entity.Player;

/**
* Created with IntelliJ IDEA. User: james Date: 13/07/13 Time: 16:39 To change this template use File | Settings | File Templates.
*/
public class PlayerTitle implements Callable<Object> {

	private final Player player;
	private final TitleConfigurationEntry.TitleType titleType;
	private TitleManager titleManager;

	public PlayerTitle(TitleManager titleManager, TitleConfigurationEntry.TitleType title, Player player) {
		this.titleManager = titleManager;
		this.titleType = title;
		this.player = player;
	}

	public String call() {
		for (TitleConfigurationEntry entry : titleManager.getTitles()) {
			boolean permitted = player.hasPermission(TitleManager.PERMISSION_PREFIX + entry.getName());
			TitleManager.LOGGER.log(Level.FINEST, "Does " + player.getName() + " have permission for " + entry.getName() + "? " + permitted);
			if (permitted) return entry.getTitle(titleType);
		}
		return "";
	}

}
