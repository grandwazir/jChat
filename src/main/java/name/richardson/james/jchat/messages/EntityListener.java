/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * EntityListener.java is part of jChat.
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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EntityListener extends org.bukkit.event.entity.EntityListener {

  public void onEntityDeath(EntityDeathEvent event) {
    if (event instanceof PlayerDeathEvent) {
      PlayerDeathEvent e = (PlayerDeathEvent) event;
      final Player player = (Player) e.getEntity();
      e.setDeathMessage((colourMessage(player, e.getDeathMessage())));
    }
  }

  private String colourMessage(Player player, String message) {
    return message.replace(player.getName(), player.getDisplayName() + ChatColor.RED);
  }

}
