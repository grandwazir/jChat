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

  public SystemMessageListener(jChat plugin, final jChatConfiguration configuration) {
    this.configuration = configuration;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onEntityDeath(final EntityDeathEvent event) {
    if ((event instanceof PlayerDeathEvent) && this.configuration.isColouringDeathMessages()) {
      final PlayerDeathEvent deathEvent = (PlayerDeathEvent) event;
      final Player player = deathEvent.getEntity();
      deathEvent.setDeathMessage((this.colourMessage(player, deathEvent.getDeathMessage())));
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerJoin(final PlayerJoinEvent event) {
    if (!this.configuration.isColouringJoinMessages()) {
      return;
    }
    final Player player = event.getPlayer();
    event.setJoinMessage((this.colourMessage(player, event.getJoinMessage())));
  }

  @EventHandler(priority = EventPriority.LOW)
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
