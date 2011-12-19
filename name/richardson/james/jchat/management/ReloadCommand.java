/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ReloadCommand.java is part of jChat.
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.jchat.jChat;
import name.richardson.james.jchat.jChatConfiguration;
import name.richardson.james.jchat.jChatHandler;
import name.richardson.james.jchat.util.command.CommandPermissionException;
import name.richardson.james.jchat.util.command.CommandUsageException;
import name.richardson.james.jchat.util.command.PlayerCommand;

public class ReloadCommand extends PlayerCommand {

  public static final String NAME = "reload";
  public static final String DESCRIPTION = "Reload jChat.";
  public static final String PERMISSION_DESCRIPTION = "Allow users to reload jChat.";
  public static final String USAGE = "/jchat reload";
  public static final PermissionDefault PERMISSION_DEFAULT = PermissionDefault.OP;
  public static final Permission PERMISSION = new Permission("jchat.reload", PERMISSION_DESCRIPTION, PERMISSION_DEFAULT);

  private final jChatHandler handler = new jChatHandler(ReloadCommand.class);

  public ReloadCommand() {
    super();
    this.registerPermission(PERMISSION, jChat.getInstance().getRootPermission());
  }

  @Override
  public void execute(final CommandSender sender, final Map<String, Object> arguments) throws CommandPermissionException, CommandUsageException {
    if (!sender.hasPermission(PERMISSION)) {
      throw new CommandPermissionException(ReloadCommand.NAME, ReloadCommand.PERMISSION);
    } else {
      final Set<Player> players = new HashSet<Player>();
      players.addAll(Arrays.asList(jChat.getInstance().getServer().getOnlinePlayers()));
      jChatConfiguration.getInstance().load();
      handler.setPlayerDisplayNames(players);
      sender.sendMessage(ChatColor.GREEN + "jChat has been reloaded.");
    }
  }

  @Override
  public String getDescription() {
    return ReloadCommand.DESCRIPTION;
  }

  @Override
  public String getName() {
    return ReloadCommand.NAME;
  }

  @Override
  public Permission getPermission() {
    return ReloadCommand.PERMISSION;
  }

  @Override
  public String getUsage() {
    return ReloadCommand.USAGE;
  }

  @Override
  public Map<String, Object> parseArguments(final List<String> arguments) throws IllegalArgumentException {
    return null;
  }

}
