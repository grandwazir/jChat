package name.richardson.james.bukkit.jchat;

import java.io.File;
import java.io.InputStream;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.jchat.title.TitleConfiguration;

/**
 * Created with IntelliJ IDEA. User: james Date: 04/07/13 Time: 23:14 To change this template use File | Settings | File Templates.
 */
public class PluginConfigurationTest extends TestCase {

	private PluginConfiguration configuration;

	@Test
	public void testIsScoreboardEnabled()
	throws Exception {
		Assert.assertFalse(configuration.isScoreboardEnabled());
	}


	@Before
	public void setUp()
	throws Exception {
		File temporaryFile = File.createTempFile("jchat", "test");
		InputStream defaults = PluginConfigurationTest.class.getClassLoader().getResourceAsStream("config.yml");
		configuration = new PluginConfiguration(temporaryFile, defaults);
	}

}
