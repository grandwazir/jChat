package name.richardson.james.bukkit.jchat.title;

import java.io.File;
import java.io.InputStream;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.jchat.message.MessagesConfiguration;

public class TitleConfigurationTest extends TestCase {

	private TitleConfiguration configuration;

	@Test
	public void testGetKeys() {
		Set<? extends TitleConfigurationEntry> entries = configuration.getTitles();
		Assert.assertTrue(entries.size() == 1);
	}

	@Before
	public void setUp()
	throws Exception {
		File temporaryFile = File.createTempFile("jchat", "test");
		InputStream defaults = TitleConfigurationTest.class.getClassLoader().getResourceAsStream("titles.yml");
		configuration = new TitleConfiguration(temporaryFile, defaults);
	}

}
