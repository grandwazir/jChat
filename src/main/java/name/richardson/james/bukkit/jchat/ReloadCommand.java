/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ReloadCommand.java is part of jChat.

 jChat is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 jChat is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 jChat. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.jchat;

import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.permissions.Permissions;
import name.richardson.james.bukkit.utilities.plugin.Reloadable;

@Permissions(permissions = "jchat.reload")
public class ReloadCommand extends name.richardson.james.bukkit.utilities.command.ReloadCommand {

	public ReloadCommand(PermissionManager permissionManager, Reloadable reloadable) {
		super(permissionManager, reloadable);
	}

}
