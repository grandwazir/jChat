/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * RefreshCommand.java is part of jChat.
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

package name.richardson.james.bukkit.jchat.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.jchat.jChat;
import name.richardson.james.bukkit.jchat.jChatHandler;
import name.richardson.james.bukkit.util.command.CommandArgumentException;
import name.richardson.james.bukkit.util.command.CommandPermissionException;
import name.richardson.james.bukkit.util.command.CommandUsageException;
import name.richardson.james.bukkit.util.command.PlayerCommand;

public class RefreshCommand extends PlayerCommand {

  public static final String NAME = "refresh";
  public static final String DESCRIPTION = "Refresh your display name.";
  public static final String USAGE = "/jchat refresh [name]";
  public static final String PERMISSION_DESCRIPTION = "Allow users to refresh their display names.";
  public static final PermissionDefault PERMISSION_DEFAULT = PermissionDefault.TRUE;
  
  public static final Permission PERMISSION = new Permission("jchat.refresh", PERMISSION_DESCRIPTION, PERMISSION_DEFAULT);
  public static final Permission PERMISSION_OTHER = new Permission("jchat.refresh.others", "Allow users to refresh other player's display names.", PermissionDefault.OP);

  private final jChatHandler handler;
  private final jChat plugin;

  public RefreshCommand(jChat plugin) {
    super(plugin, NAME, DESCRIPTION, USAGE, PERMISSION_DESCRIPTION, PERMISSION);
    this.plugin = plugin;
    this.handler = plugin.getHandler(RefreshCommand.class);
    plugin.addPermission(PERMISSION, true);
    // plugin.addPermission(PERMISSION_OTHER ,
    // jChat.getInstance().getRootPermission());
  }

  @Override
  public void execute(final CommandSender sender, final Map<String, Object> arguments) throws CommandPermissionException, CommandUsageException {
    if (!(sender instanceof Player) && arguments.isEmpty()) {
      throw new CommandUsageException("You must specify a player to use this from the console.");
    } else if (!arguments.isEmpty()) {
      if (sender.hasPermission(PERMISSION_OTHER) || arguments.get("player").equals(sender)) {
        final Player player = (Player) arguments.get("player");
        handler.setPlayerDisplayName(player);
        sender.sendMessage(ChatColor.GREEN + player.getName() + "'s display name has been refreshed.");
      } else {
        throw new CommandPermissionException("You do not have permission to do this.", PERMISSION_OTHER);
      }
    } else {
      final Player player = (Player) sender;
      handler.setPlayerDisplayName(player);
      sender.sendMessage(ChatColor.GREEN + "Your display name has been refreshed.");
    }
  }

  @Override
  public Map<String, Object> parseArguments(final List<String> arguments) throws CommandArgumentException {
    HashMap<String, Object> map = new HashMap<String, Object>();

    if (!arguments.isEmpty()) {
      final Player player = plugin.getServer().getPlayer(arguments.get(0));
      if (player != null) {
        map.put("player", player);
      } else {
        throw new CommandArgumentException("You must specify a player who is online.", "You only need to type in part of the name.");
      }
    }
    return map;
  }

}
