/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * CommandManager.java is part of jChat.
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

package name.richardson.james.jchat;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import name.richardson.james.jchat.util.command.Command;

public class CommandManager implements CommandExecutor {

  private final HashMap<String, Command> commands = new LinkedHashMap<String, Command>();

  public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {

    if (args.length != 0) {
      if (this.commands.containsKey(args[0])) {
        this.commands.get(args[0]).onCommand(sender, command, label, args);
      } else if (args[0].equalsIgnoreCase("help")) {
        if (args.length != 2) {
          sender.sendMessage(ChatColor.RED + "/jchat help <command>");
          sender.sendMessage(ChatColor.YELLOW + "You must specify a command.");
        } else if (commands.containsKey(args[1])) {
          final Command c = commands.get(args[1]);
          sender.sendMessage(ChatColor.LIGHT_PURPLE + c.getUsage());
          sender.sendMessage(ChatColor.YELLOW + c.getDescription());
        } else {
          sender.sendMessage(ChatColor.RED + "/jchat help <command>");
          sender.sendMessage(ChatColor.YELLOW + "You must specify a valid command.");
        }
      } else {
        sender.sendMessage(ChatColor.RED + "Invalid command!");
        sender.sendMessage(ChatColor.YELLOW + "Type /jchat for a list of commands.");
      }
    } else {
      final String name = jChat.getInstance().getDescription().getFullName();
      final String description = jChat.getInstance().getDescription().getDescription();
      sender.sendMessage(ChatColor.LIGHT_PURPLE + name);
      sender.sendMessage(ChatColor.LIGHT_PURPLE + description);
      sender.sendMessage(ChatColor.GREEN + "Type /jchat help <command> for details on a command.");
      for (Entry<String, Command> c : commands.entrySet()) {
        sender.sendMessage(ChatColor.YELLOW + "- " + c.getValue().getUsage());
      }
    }
    return true;
  }



  /**
   * Register a sub command underneath the root command defined the executor was
   * created.
   * 
   * @param commandName
   * The string to associated this command with (without the prefix).
   * @param command
   * An instance of the command that should be registered.
   */
  protected void registerCommand(final String command, final Command executor) {
    this.commands.put(command, executor);
  }


}
