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

package name.richardson.james.jchat.management;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.jchat.jChatHandler;
import name.richardson.james.jchat.util.command.CommandPermissionException;
import name.richardson.james.jchat.util.command.CommandUsageException;
import name.richardson.james.jchat.util.command.PlayerCommand;

public class RefreshCommand extends PlayerCommand {

  public static final String NAME = "refresh";
  public static final String DESCRIPTION = "refresh your display name";
  public static final String PERMISSION_DESCRIPTION = "Allow users to refresh their display names.";
  public static final String USAGE = "/jchat refresh";
  public static final PermissionDefault PERMISSION_DEFAULT = PermissionDefault.TRUE;
  public static final Permission PERMISSION = new Permission("jchat.refresh", PERMISSION_DESCRIPTION, PERMISSION_DEFAULT);

  private final jChatHandler handler = new jChatHandler(RefreshCommand.class);

  public RefreshCommand() {
    super();
  }

  @Override
  public void execute(final CommandSender sender, final Map<String, Object> arguments) throws CommandPermissionException, CommandUsageException {
    if (!sender.hasPermission(PERMISSION)) {
      throw new CommandPermissionException(RefreshCommand.NAME, RefreshCommand.PERMISSION);
    } else if (sender instanceof Player) {
      throw new CommandUsageException("This command may not be used from the console.", RefreshCommand.USAGE);
    } else {
      final Player player = (Player) sender;
      handler.setPlayerDisplayName(player);
      sender.sendMessage(ChatColor.GREEN + "Your display name has been refreshed.");
    }
  }

  @Override
  public String getDescription() {
    return RefreshCommand.DESCRIPTION;
  }

  @Override
  public String getName() {
    return RefreshCommand.NAME;
  }

  @Override
  public Permission getPermission() {
    return RefreshCommand.PERMISSION;
  }

  @Override
  public String getUsage() {
    return RefreshCommand.USAGE;
  }

  @Override
  public Map<String, Object> parseArguments(final List<String> arguments) throws IllegalArgumentException {
    return null;
  }

}
