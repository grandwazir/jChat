package name.richardson.james.bukkit.jchat;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReloadCommandTest extends TestCase {

	private ReloadCommand command;

	@Mock
	private jChat plugin;

	@Test
	public void setUp()
	throws Exception {
		command = new ReloadCommand(plugin);
	}
}
