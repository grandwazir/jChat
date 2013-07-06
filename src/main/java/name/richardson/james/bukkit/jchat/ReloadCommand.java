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
