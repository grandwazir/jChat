/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * Command.java is part of jChat.
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

import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public interface Command extends CommandExecutor {

  void execute(CommandSender sender, Map<String, Object> arguments) throws CommandPermissionException, CommandUsageException;

  Map<String, Object> getArguments();

  String getDescription();

  String getName();

  Permission getPermission();

  String getUsage();

  Map<String, Object> parseArguments(List<String> arguments) throws CommandArgumentException;

  void registerPermission(Permission permission, Permission parentPermission);

  void setArguments(Map<String, Object> arguments);

}
