/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * RefreshCommand.java is part of jChatPlugin.
 * 
 * jChatPlugin is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * jChatPlugin is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * jChatPlugin. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.jchat.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Server;
import org.bukkit.command.Command;
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

  // The player who is the target of this command
  private Player player;

  public RefreshCommand(final jChat plugin) {
    super(plugin);
    this.server = plugin.getServer();
    this.plugin = plugin;
    this.registerPermissions();
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    try {
      if (this.getPermissionManager().hasPlayerPermission(sender, this.getPermissions().get(1)) && (this.player.getName().equalsIgnoreCase(sender.getName()))) {
        this.plugin.invalidatePlayerMetaData(this.player);
        this.plugin.setPlayerDisplayName(this.player);
        sender.sendMessage(this.getLocalisation().getMessage(this, ("display-name-refreshed")));
      } else if (this.player.getName().equalsIgnoreCase(sender.getName())) {
        throw new CommandPermissionException(null, this.getPermissions().get(1));
      }
      if (this.getPermissionManager().hasPlayerPermission(sender, this.getPermissions().get(2))  && !(this.player.getName().equalsIgnoreCase(sender.getName()))) {
        this.plugin.invalidatePlayerMetaData(this.player);
        this.plugin.setPlayerDisplayName(this.player);
        sender.sendMessage(this.getLocalisation().getMessage(this, "another-display-name-refreshed", this.player.getName()));
      } else if (!this.player.getName().equalsIgnoreCase(sender.getName())) {
        throw new CommandPermissionException(null, this.getPermissions().get(2));
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
      final String playerName = arguments[0];
      this.player = this.server.getPlayerExact(playerName);
    }
    // check to see if we have a target player
    if (this.player == null) {
      throw new CommandArgumentException(this.getLocalisation().getMessage(this, "player-not-online"), null);
    }
  }

  private void registerPermissions() {
    Permission own = this.getPermissionManager().createPermission(this, "own", PermissionDefault.TRUE, this.getPermissions().get(0), true);
    this.addPermission(own);
    Permission others = this.getPermissionManager().createPermission(this, "others", PermissionDefault.OP, this.getPermissions().get(0), true);
    this.addPermission(others);
  }

  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] arguments) {
    List<String> list = new ArrayList<String>();
    Set<String> temp = new TreeSet<String>();
    if (arguments.length <= 1) {
      for (Player player : this.server.getOnlinePlayers()) {
        if (arguments.length < 1) {
          temp.add(player.getName());
        } else if (player.getName().startsWith(arguments[0])) {
          temp.add(player.getName());
        }
      } 
    }
    list.addAll(temp);
    return list;
  }

}
