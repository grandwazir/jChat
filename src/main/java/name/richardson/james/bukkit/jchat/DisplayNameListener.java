/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * DisplayNameListener.java is part of jChat.
 * 
 * jChat is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * jChat is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * jChat. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.jchat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * The listener interface for receiving displayName events.
 * The class that is interested in processing a displayName
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDisplayNameListener<code> method. When
 * the displayName event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DisplayNameEvent
 */
public class DisplayNameListener implements Listener {

  /** The jChat plugin */
  private final jChat plugin;
  
  /**
   * Instantiates a new listener.
   *
   * @param plugin the plugin
   */
  public DisplayNameListener(jChat plugin) {
    this.plugin = plugin;
  }

  /**
   * Update the player's display name when they join the server.
   *
   * @param event the event
   */
  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerJoin(PlayerJoinEvent event) {
    plugin.setPlayerMetaData(event.getPlayer());
    plugin.setPlayerDisplayName(event.getPlayer());
  }

  /**
   * Update the player's display name when they change worlds.
   *
   * @param event the event
   */
  @EventHandler(priority = EventPriority.LOW)
  public void PlayerChangedWorld(PlayerChangedWorldEvent event) {
    plugin.invalidatePlayerMetaData(event.getPlayer());
    plugin.setPlayerDisplayName(event.getPlayer());
  }


}
