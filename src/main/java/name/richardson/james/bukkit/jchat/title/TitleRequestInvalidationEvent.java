package name.richardson.james.bukkit.jchat.title;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TitleRequestInvalidationEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;

	public TitleRequestInvalidationEvent(Player player) {
		this.player = player;
	}

	@Override
	public HandlerList getHandlers() {
		return TitleRequestInvalidationEvent.handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return player;
	}

}
