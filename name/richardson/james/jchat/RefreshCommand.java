/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ListCommand.java is part of Reservation.
 * 
 * Reservation is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Reservation is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Reservation. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.jchat;

import java.util.List;
import java.util.Map;

import name.richardson.james.jchat.util.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

public class RefreshCommand extends Command {

  public RefreshCommand(final jChat plugin) {
    super(plugin);
    this.name = "refresh";
    this.description = "refresh your display name";
    this.usage = "/jchat refresh";
    this.permission = "jchat." + this.name;
    this.registerPermission(this.permission, "allows players to refresh their display name", PermissionDefault.TRUE);
  }

  @Override
  public void execute(final CommandSender sender, final Map<String, Object> arguments) {
    if (sender instanceof Player) {
      handler.setPlayerDisplayName((Player) sender);
      sender.sendMessage(ChatColor.GREEN + "Your display name has been refreshed.");
    } else {
      throw new IllegalStateException("You may not use this command from the console.");
    }
  }

  @Override
  protected Map<String, Object> parseArguments(final List<String> arguments) throws IllegalArgumentException {
    return null;
  }

}
