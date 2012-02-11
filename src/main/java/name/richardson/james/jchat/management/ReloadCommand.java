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

import name.richardson.james.bukkit.util.command.PlayerCommand;
import name.richardson.james.jchat.jChat;
import name.richardson.james.jchat.jChatHandler;

public class ReloadCommand extends PlayerCommand {

  public static final String NAME = "reload";
  public static final String DESCRIPTION = "Reload jChat.";
  public static final String PERMISSION_DESCRIPTION = "Allow users to reload jChat.";
  public static final String USAGE = "/jchat reload";
  public static final PermissionDefault PERMISSION_DEFAULT = PermissionDefault.OP;
  public static final Permission PERMISSION = new Permission("jchat.reload", PERMISSION_DESCRIPTION, PERMISSION_DEFAULT);

  private final jChatHandler handler = new jChatHandler(ReloadCommand.class);
  private final jChat plugin;

  public ReloadCommand(jChat plugin) {
    super(plugin, NAME, DESCRIPTION, USAGE, PERMISSION_DESCRIPTION, PERMISSION);
    this.plugin = plugin;
    plugin.addPermission(PERMISSION, true);
  }

  @Override
  public void execute(final CommandSender sender, final Map<String, Object> arguments) {
    final Set<Player> players = new HashSet<Player>();
    players.addAll(Arrays.asList(plugin.getServer().getOnlinePlayers()));
    plugin.getjChatConfiguration().load();
    handler.setPlayerDisplayNames(players);
    sender.sendMessage(ChatColor.GREEN + "jChat has been reloaded.");
  }

}
