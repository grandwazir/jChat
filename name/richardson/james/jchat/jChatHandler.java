/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ReservationRecordHandler.java is part of Reservation.
 * 
 * Reservation is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Reservation is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Reservation. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.jchat;


import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import name.richardson.james.jchat.util.Handler;
import name.richardson.james.jchat.util.Logger;

public final class jChatHandler extends Handler {

  protected final static Logger logger = new Logger(jChatHandler.class);
  
  public jChatHandler(Class<?> owner) {
    super(owner);
  }

  public void setPlayerDisplayName(final Player player) {
    String displayName = getPrefix(player) + player.getName() + getSuffix(player);
    player.setDisplayName(displayName);
    logger.debug(String.format("%s's display name set to %s.", player.getName(), player.getDisplayName()));
  }
  
  public void setPlayerDisplayNames(final Set<Player> players) {
    for (Player player : players) {
      setPlayerDisplayName(player);
    }
  }
  
  public void revertPlayerDisplayName(final Player player) {
    player.setDisplayName(player.getName());
    logger.debug(String.format("%s's display name has been reset.", player.getName()));
  }

  public void revertPlayerDisplayNames(final Set<Player> players) {
    for (Player player : players) {
      revertPlayerDisplayName(player);
    }
  }
  
  private String getPrefix(final Player player) {
    String title = getTitle(player, jChatConfiguration.getInstance().getPrefixPermissions());
    logger.debug(String.format("Using prefix: %s", title));
    return title.replace("&", "§");
  }
  
  private String getTitle(Player player, Set<String> keys) {
    String title = "";
    for (String key : keys) {
      String permissionName = "jchat." + key;
      logger.debug(String.format("Checking to see if %s has the permission node: %s", player.getName(), permissionName));
      if (player.hasPermission(permissionName)) {
        title = jChatConfiguration.getInstance().getTitle(key);
        break;
      }
    }
    return title.replace("&", "§");
  }
  
  private String getSuffix(final Player player) {
    String title = getTitle(player, jChatConfiguration.getInstance().getSuffixPermissions());
    logger.debug(String.format("Using suffix: %s", title));
    title = title.replace("&", "§");
    return title + ChatColor.WHITE;
  }
  
}
