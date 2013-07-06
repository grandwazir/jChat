package name.richardson.james.bukkit.jchat;

import org.bukkit.plugin.PluginManager;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

@RunWith(MockitoJUnitRunner.class)
public class ReloadCommandTest extends TestCase {

	private ReloadCommand command;

	@Mock
	private jChat plugin;

	@Mock
	private PermissionManager permissionManager;

	@Test
	public void setUp()
	throws Exception {
		command = new ReloadCommand(permissionManager, plugin);
	}
}
