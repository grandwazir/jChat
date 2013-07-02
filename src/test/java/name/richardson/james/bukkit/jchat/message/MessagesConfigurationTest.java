package name.richardson.james.bukkit.jchat.message;

import java.io.File;
import java.io.InputStream;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class MessagesConfigurationTest extends TestCase {

	private MessagesConfiguration configuration;

	@Before
	public void setUp() throws Exception {
		File temporaryFile = File.createTempFile("jchat", "test");
		InputStream defaults = MessagesConfigurationTest.class.getClassLoader().getResourceAsStream("messages.yml");
		if (defaults == null) System.out.print("defaults null");
		configuration = new MessagesConfiguration(temporaryFile, defaults);
	}

	@Test
	public void testIsColouringQuitMessages()
	throws Exception {
		Assert.assertTrue(configuration.isColouringQuitMessages());
	}

	@Test
	public void testIsColouringJoinMessages()
	throws Exception {
		Assert.assertTrue(configuration.isColouringJoinMessages());
	}

	@Test
	public void testIsColouringDeathMessages()
	throws Exception {
		Assert.assertTrue(configuration.isColouringDeathMessages());
	}

}
