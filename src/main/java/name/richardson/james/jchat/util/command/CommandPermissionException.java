/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * CommandPermissionException.java is part of jChat.
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

import org.bukkit.permissions.Permission;

public class CommandPermissionException extends Exception {

  private static final long serialVersionUID = 4498180605868829834L;

  private String message;
  private Permission permission;

  public CommandPermissionException(String message, Permission permission) {
    this.setMessage(message);
    this.setPermission(permission);
  }

  public String getMessage() {
    return message;
  }

  public Permission getPermission() {
    return permission;
  }

  private void setMessage(String message) {
    this.message = message;
  }

  private void setPermission(Permission permission) {
    this.permission = permission;
  }

}
