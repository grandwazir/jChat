/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * jChatHandler.java is part of jChat.
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

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.util.Colour;
import name.richardson.james.bukkit.util.Handler;
import name.richardson.james.bukkit.util.Logger;

public final class jChatHandler extends Handler {

  public static final int LIST_NAME_LIMIT = 16;
  
  private final jChat plugin;
  private final jChatConfiguration configuration;
  
  protected final static Logger logger = new Logger(jChatHandler.class);

  public jChatHandler(Class<?> owner, jChat plugin) {
    super(owner);
    this.plugin = plugin;
    this.configuration = plugin.getjChatConfiguration();
  }

  public void revertPlayerDisplayName(final Player player) {
    player.setDisplayName(player.getName());
    player.setPlayerListName(player.getName());
    logger.debug(String.format("%s's display name has been reset.", player.getName()));
  }

  public void revertPlayerDisplayNames(final Set<Player> players) {
    for (Player player : players) {
      revertPlayerDisplayName(player);
    }
  }

  public void setPlayerDisplayName(final Player player) {
    String displayName = getPrefix(player) + player.getName() + getSuffix(player);
    player.setDisplayName(displayName);
    try {
      player.setPlayerListName(ChatColor.RED + "sergeant_subtle");
    } catch (IllegalArgumentException exception) {
      if (!configuration.isSupressListNameWarning()) {
        logger.warning(String.format("DisplayName is too long by %d characters to be used on the PlayerList: %s", displayName.length() - LIST_NAME_LIMIT, displayName));
      }
    }
    logger.debug(String.format("%s's display name set to %s.", player.getName(), player.getDisplayName()));
  }

  public void setPlayerDisplayNames(final Set<Player> players) {
    for (Player player : players) {
      setPlayerDisplayName(player);
    }
  }

  private String getPrefix(final Player player) {
    String title = getTitle(player, configuration.getPrefixPaths(), "prefix");
    logger.debug(String.format("Using prefix: %s", title));
    return title;
  }

  private String getSuffix(final Player player) {
    String title = getTitle(player, configuration.getSuffixPaths(), "suffix");
    logger.debug(String.format("Using suffix: %s", title));
    return title + ChatColor.WHITE;
  }

  private String getTitle(Player player, Set<String> keys, String filter) {
    String title = "";
    for (Permission permission : plugin.getPermissions()) {
      logger.debug(String.format("Checking to see if %s has the permission node: %s", player.getName(), permission.getName()));
      if (player.hasPermission(permission) && permission.getName().contains(filter)) {
        title = configuration.getTitle(permission.getName().replaceFirst("jchat.", ""));
        break;
      }
    }
    return Colour.replace("&", title);
  }
}
