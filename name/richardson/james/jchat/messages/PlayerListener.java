/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * PlayerListener.java is part of jChat.
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

package name.richardson.james.jchat.messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import name.richardson.james.jchat.jChatHandler;

public class PlayerListener extends org.bukkit.event.player.PlayerListener {

  private final static jChatHandler handler = new jChatHandler(PlayerListener.class);

  public void onPlayerDeath(PlayerDeathEvent event) {
    final Player player = (Player) event.getEntity();
    event.setDeathMessage((colourMessage(player, event.getDeathMessage())));
  }

  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    handler.setPlayerDisplayName(event.getPlayer());
    event.setJoinMessage((colourMessage(player, event.getJoinMessage())));
  }

  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    event.setQuitMessage((colourMessage(player, event.getQuitMessage())));
  }

  public void PlayerChangedWorld(PlayerChangedWorldEvent event) {
    final Player player = event.getPlayer();
    handler.setPlayerDisplayName(player);
  }

  private String colourMessage(Player player, String message) {
    return message.replace(player.getName(), player.getDisplayName() + ChatColor.YELLOW);
  }

}
