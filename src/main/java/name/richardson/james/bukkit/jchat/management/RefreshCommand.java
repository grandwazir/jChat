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

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.jchat.jChat;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;

@ConsoleCommand
public class RefreshCommand extends AbstractCommand {

  private final jChat plugin;
  
  private final Server server;
  
  private Permission own;

  private Permission others;


  // The player who is the target of this command
  private Player player;

  public RefreshCommand(final jChat plugin) {
    super(plugin, false);
    this.server = plugin.getServer();
    this.plugin = plugin;
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    try {
      if (sender.hasPermission(own) && this.player.getName().equalsIgnoreCase(sender.getName())) {
        this.plugin.invalidatePlayerMetaData(this.player);
        this.plugin.setPlayerDisplayName(this.player);
        sender.sendMessage(this.getLocalisation().getMessage(this, ("display-name-refreshed")));
      } else if (this.player.getName().equalsIgnoreCase(sender.getName())) {
        throw new CommandPermissionException(null, own);
      }
      if (sender.hasPermission(others) && !this.player.getName().equalsIgnoreCase(sender.getName())) {
        this.plugin.invalidatePlayerMetaData(this.player);
        this.plugin.setPlayerDisplayName(this.player);
        sender.sendMessage(this.getLocalisation().getMessage(this, "another-display-name-refreshed", this.player.getName()));
      } else if (!this.player.getName().equalsIgnoreCase(sender.getName())) {
        throw new CommandPermissionException(null, others);
      }
    } finally {
      this.player = null;
    }
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    if (sender instanceof ConsoleCommandSender) {
      this.player = null;
    } else if (arguments.length == 0) {
      this.player = (Player) sender;
    } else {
      final String playerName = this.matchPlayerName(arguments[0]);
      this.player = this.server.getPlayerExact(playerName);
    }
    // check to see if we have a target player
    if (this.player == null) {
      throw new CommandArgumentException(this.getLocalisation().getMessage(this, "player-not-online"), null);
    }
  }

  private String matchPlayerName(final String playerName) {
    final List<Player> matches = this.server.matchPlayer(playerName);
    if (matches.isEmpty()) {
      return playerName;
    } else {
      return matches.get(0).getName();
    }
  }

  @Override
  protected void registerPermissions(boolean wildcard) {
    super.registerPermissions(wildcard);
    final String prefix = this.getRootPermission().getName().replace("*", "");
    // add ability to refresh their own display name
    own = new Permission(prefix + "." + this.getLocalisation().getMessage(this, "own-permission-name"), this.getLocalisation().getMessage(this, "own-permission-description"), PermissionDefault.TRUE);
    own.addParent(this.getRootPermission(), true);
    this.getPermissionManager().addPermission(own, false);
    // add ability to refresh others
    others = new Permission(prefix + "." + this.getLocalisation().getMessage(this, "others-permission-name"), this.getLocalisation().getMessage(this, "others-permission-description"), PermissionDefault.OP);
    others.addParent(this.getRootPermission(), true);
    this.getPermissionManager().addPermission(others, false);
  }

}
