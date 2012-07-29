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
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;
import name.richardson.james.bukkit.utilities.command.PluginCommand;

@ConsoleCommand
public class RefreshCommand extends PluginCommand {

  private final jChat plugin;
  private final Server server;

  // The player who is the target of this command
  private Player player;

  public RefreshCommand(final jChat plugin) {
    super(plugin);
    this.server = plugin.getServer();
    this.plugin = plugin;
    this.registerPermissions();
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {

    if (sender.hasPermission(this.getPermission(1)) && this.player.getName().equalsIgnoreCase(sender.getName())) {
      this.plugin.invalidatePlayerMetaData(this.player);
      this.plugin.setPlayerDisplayName(this.player);
      sender.sendMessage(this.getMessage("display-name-refreshed"));
    } else if (this.player.getName().equalsIgnoreCase(sender.getName())) {
      throw new CommandPermissionException(null, this.getPermission(1));
    }

    if (sender.hasPermission(this.getPermission(2)) && !this.player.getName().equalsIgnoreCase(sender.getName())) {
      this.plugin.invalidatePlayerMetaData(this.player);
      this.plugin.setPlayerDisplayName(this.player);
      sender.sendMessage(this.getSimpleFormattedMessage("another-display-name-refreshed", this.player.getName()));
    } else if (!this.player.getName().equalsIgnoreCase(sender.getName())) {
      throw new CommandPermissionException(null, this.getPermission(2));
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
      throw new CommandArgumentException(this.getMessage("player-not-online"), this.getMessage("player-name-matching"));
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

  private void registerPermissions() {
    final String prefix = this.plugin.getDescription().getName().toLowerCase() + ".";
    final String wildcardDescription = String.format(this.plugin.getMessage("plugincommand.wildcard-permission-description"), this.getName());
    // create the wildcard permission
    final Permission wildcard = new Permission(prefix + this.getName() + ".*", wildcardDescription, PermissionDefault.OP);
    wildcard.addParent(this.plugin.getRootPermission(), true);
    this.addPermission(wildcard);
    // create the base permission
    final Permission base = new Permission(prefix + this.getName(), this.getMessage("permission-description"), PermissionDefault.TRUE);
    base.addParent(wildcard, true);
    this.addPermission(base);
    // add ability to set other user's homes
    final Permission others = new Permission(prefix + this.getName() + "." + this.getMessage("permission-others"), this.getMessage("permission-others-description"), PermissionDefault.OP);
    others.addParent(wildcard, true);
    this.addPermission(others);
  }

}
