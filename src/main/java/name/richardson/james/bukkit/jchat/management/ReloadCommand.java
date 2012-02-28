/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
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
package name.richardson.james.bukkit.jchat.management;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.jchat.jChat;
import name.richardson.james.bukkit.jchat.jChatHandler;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.PluginCommand;

public class ReloadCommand extends PluginCommand {

  private final jChatHandler handler;
  private final jChat plugin;

  public ReloadCommand(jChat plugin) {
    super(plugin);
    this.plugin = plugin;
    this.handler = plugin.getHandler(ReloadCommand.class);
    this.registerPermissions();
  }

  private void registerPermissions() {
    final String prefix = plugin.getDescription().getName().toLowerCase() + ".";
    // create the base permission
    Permission base = new Permission(prefix + this.getName(), plugin.getMessage("reloadcommand-permission-description"), PermissionDefault.TRUE);
    this.addPermission(base);
  }

  public void execute(final CommandSender sender) {
    final Set<Player> players = new HashSet<Player>();
    players.addAll(Arrays.asList(plugin.getServer().getOnlinePlayers()));
    plugin.getjChatConfiguration().load();
    handler.setPlayerDisplayNames(players);
    sender.sendMessage(ChatColor.GREEN + this.plugin.getSimpleFormattedMessage("reloadcommand-complete", this.plugin.getDescription().getName()));
  }

  public void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException {
    return;
  }

}
