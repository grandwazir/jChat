/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
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

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.jchat.jChat;
import name.richardson.james.bukkit.jchat.jChatHandler;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.PluginCommand;

public class RefreshCommand extends PluginCommand {

  private final jChatHandler handler;
  private final jChat plugin;
  private final Server server;
  
  // The player who is the target of this command
  private Player player;

  public RefreshCommand(jChat plugin) {
    super(plugin);
    this.server = plugin.getServer();
    this.plugin = plugin;
    this.handler = plugin.getHandler(RefreshCommand.class);
    this.registerPermissions();
  }

  private void registerPermissions() {
    final String prefix = plugin.getDescription().getName().toLowerCase() + ".";
    final String wildcardDescription = String.format(plugin.getMessage("wildcard-permission-description"), this.getName());
    // create the wildcard permission
    Permission wildcard = new Permission(prefix + this.getName() + ".*", wildcardDescription, PermissionDefault.OP);
    wildcard.addParent(plugin.getRootPermission(), true);
    this.addPermission(wildcard);
    // create the base permission
    Permission base = new Permission(prefix + this.getName(), plugin.getMessage("refreshcommand-permission-description"), PermissionDefault.TRUE);
    base.addParent(wildcard, true);
    this.addPermission(base);
    // add ability to set other user's homes
    Permission others = new Permission(prefix + this.getName() + "." + plugin.getMessage("refreshcommand-permission-others"), plugin.getMessage("refreshcommand-permission-others-description"), PermissionDefault.OP);
    others.addParent(wildcard, true);
    this.addPermission(others);
  }


  public void parseArguments(final String[] arguments, CommandSender sender) throws CommandArgumentException {

    if (arguments.length == 0) {
      player = (Player) sender;
    } else {
      String playerName = matchPlayerName(arguments[0]);
      player = this.server.getPlayerExact(playerName);
    }
    
    // check to see if we have a target player
    if (player == null) throw new CommandArgumentException(this.plugin.getMessage("player-not-online"), this.plugin.getMessage("player-name-matching"));
    
  }

  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    
    if (sender.hasPermission(this.getPermission(1)) && player.getName().equalsIgnoreCase(sender.getName())) {
      handler.setPlayerDisplayName(player);
      sender.sendMessage(ChatColor.GREEN + this.plugin.getMessage("display-name-refreshed"));
    } else if (player.getName().equalsIgnoreCase(sender.getName())) {
      throw new CommandPermissionException(null, this.getPermission(1));
    }
    
    if (sender.hasPermission(this.getPermission(2)) && !player.getName().equalsIgnoreCase(sender.getName())) {
      handler.setPlayerDisplayName(player);
      sender.sendMessage(ChatColor.GREEN + this.plugin.getSimpleFormattedMessage("another-display-name-refreshed", player.getName()));
    } else if (!player.getName().equalsIgnoreCase(sender.getName())) {
      throw new CommandPermissionException(null, this.getPermission(2));
    }
    
  }
  

  private String matchPlayerName(String playerName) {
    List<Player> matches = this.server.matchPlayer(playerName);
    if (matches.isEmpty()) {
      return playerName;
    } else {
      return matches.get(0).getName();
    }
  }

}
