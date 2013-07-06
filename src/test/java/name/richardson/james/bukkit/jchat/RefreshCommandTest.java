package name.richardson.james.bukkit.jchat;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import name.richardson.james.bukkit.utilities.command.CommandContext;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RefreshCommandTest extends TestCase {

	private RefreshCommand command;

	@Mock
	private Server server;

	@Mock
	private PluginManager pluginManager;

	@Mock
	private Player player;

	@Mock
	private PermissionManager permissionManager;


	@Test
	public void testExecuteOthersSuccess() {
		when(player.hasPermission(anyString())).thenReturn(true);
		when(server.getPlayer(anyString())).thenReturn(player);
		String[] arguments = {"grandwazir"};
		CommandContext context = new CommandContext(arguments, player, server);
		command.execute(context);
		verify(player).sendMessage("§a§bgrandwazir§a's display name refreshed.");
	}

	@Test
	public void testExecuteOthersFailure() {
		when(player.hasPermission(anyString())).thenReturn(false);
		when(server.getPlayer(anyString())).thenReturn(player);
		String[] arguments = {"grandwazir"};
		CommandContext context = new CommandContext(arguments, player, server);
		command.execute(context);
		verify(player).sendMessage("§cYou are not allowed to target §egrandwazir§c.");
	}

	@Test
	public void testExecuteSelf()
	throws Exception {
		String[] arguments = {""};
		CommandContext context = new CommandContext(arguments, player);
		when(player.hasPermission(anyString())).thenReturn(true);
		command.execute(context);
		verify(player).sendMessage(anyString());
	}

	@Test
	public void testCommandSenderExecute() {
		CommandSender sender = mock(ConsoleCommandSender.class);
		String[] arguments = {""};
		CommandContext context = new CommandContext(arguments, sender);
		command.execute(context);
		verify(sender).sendMessage("§cYou must specify the name of a player!");
	}


	@Before
	public void setUp()
	throws Exception {
		Permission permission = mock(Permission.class);
		when(pluginManager.getPermission(anyString())).thenReturn(permission);
		command = new RefreshCommand(permissionManager, server, pluginManager);
		when(player.getName()).thenReturn("grandwazir");
	}

}
