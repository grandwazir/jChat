/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * PlayerCommand.java is part of jChat.
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

package name.richardson.james.jchat.util.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.jchat.jChat;
import name.richardson.james.jchat.util.Logger;

public abstract class PlayerCommand implements Command {

  public static final PermissionDefault PERMISSION_DEFAULT = PermissionDefault.OP;
  protected static final Logger logger = new Logger(PlayerCommand.class);

  protected Map<String, Object> arguments = new HashMap<String, Object>();

  public abstract void execute(CommandSender sender, Map<String, Object> arguments) throws CommandPermissionException, CommandUsageException;

  @Override
  public Map<String, Object> getArguments() {
    return Collections.unmodifiableMap(arguments);
  }

  /**
   * Check to see if a player has permission to use this command.
   * 
   * A console user is permitted to use all commands by default.
   * 
   * @param sender
   * The player/console that is attempting to use the command
   * @return true if the player has permission; false otherwise.
   */
  public boolean isSenderAuthorised(final CommandSender sender) {
    return sender.hasPermission(this.getPermission());
  }

  @Override
  public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {

    try {
      final LinkedList<String> arguments = new LinkedList<String>();
      arguments.addAll(Arrays.asList(args));
      final Map<String, Object> parsedArguments = this.parseArguments(arguments);
      this.execute(sender, parsedArguments);
    } catch (final IllegalArgumentException e) {
      sender.sendMessage(ChatColor.RED + this.getUsage());
      sender.sendMessage(ChatColor.YELLOW + e.getMessage());
    } catch (final IllegalStateException e) {
      sender.sendMessage(ChatColor.RED + this.getUsage());
      sender.sendMessage(ChatColor.YELLOW + e.getMessage());
    } catch (CommandPermissionException exception) {
      sender.sendMessage(ChatColor.RED + "You do not have permission to do this.");
    } catch (CommandUsageException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
    }
    return true;
  }

  public abstract Map<String, Object> parseArguments(List<String> arguments);

  public void registerPermission(Permission permission, Permission parentPermission) {
    permission.addParent(parentPermission, true);
    jChat.getInstance().getServer().getPluginManager().addPermission(permission);
  }

  @Override
  public void setArguments(Map<String, Object> arguments) {
    this.arguments = arguments;
  }

}
