/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TitleRequestInvalidationEvent.java is part of jChat.

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
