
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
