/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * SystemMessageListener.java is part of jChat.
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
package name.richardson.james.bukkit.jchat.messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import name.richardson.james.bukkit.jchat.jChat;
import name.richardson.james.bukkit.jchat.jChatConfiguration;

public class SystemMessageListener implements Listener {

  private final jChatConfiguration configuration;

  public SystemMessageListener(jChat plugin) {
    this.configuration = plugin.getjChatConfiguration();
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onEntityDeath(EntityDeathEvent event) {
    if (event instanceof PlayerDeathEvent && configuration.isColouringDeathMessages()) {
      PlayerDeathEvent deathEvent = (PlayerDeathEvent) event;
      Player player = (Player) deathEvent.getEntity();
      deathEvent.setDeathMessage((colourMessage(player, deathEvent.getDeathMessage())));
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerDeath(PlayerDeathEvent event) {
    if (!configuration.isColouringDeathMessages())
      return;
    final Player player = (Player) event.getEntity();
    event.setDeathMessage((colourMessage(player, event.getDeathMessage())));
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerJoin(PlayerJoinEvent event) {
    if (!configuration.isColouringJoinMessages())
      return;
    Player player = event.getPlayer();
    event.setJoinMessage((colourMessage(player, event.getJoinMessage())));
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerQuit(PlayerQuitEvent event) {
    if (!configuration.isColouringQuitMessages())
      return;
    Player player = event.getPlayer();
    event.setQuitMessage((colourMessage(player, event.getQuitMessage())));
  }

  private String colourMessage(Player player, String message) {
    return message.replace(player.getName(), player.getDisplayName() + ChatColor.RED);
  }

}
