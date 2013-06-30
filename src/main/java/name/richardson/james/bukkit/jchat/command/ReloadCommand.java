/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ReloadCommand.java is part of jChatPlugin.
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

import java.io.IOException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.jchat.jChat;

import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;

@ConsoleCommand
public class ReloadCommand extends AbstractCommand {

  private final jChat plugin;

  public ReloadCommand(final jChat plugin) {
    super(plugin);
    this.plugin = plugin;
  }

  public void execute(final CommandSender sender) {
    try {
      this.plugin.reload();
      this.plugin.setPlayerDisplayName(this.plugin.getServer().getOnlinePlayers());
      sender.sendMessage(this.getLocalisation().getMessage(this, "complete"));
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    return;
  }

  public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
    return null;
  }

}
