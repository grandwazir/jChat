
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
