package name.richardson.james.bukkit.jchat.title;

import java.io.File;
import java.io.InputStream;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class ScoreboardTitleConfigurationTest extends TestCase {

	private ScoreboardTitleConfiguration configuration;

	@Test
	public void testGetKeys() {
		Set<? extends TitleConfigurationEntry> entries = configuration.getTitles();
		Assert.assertTrue(entries.size() == 1);
	}

	@Before
	public void setUp()
	throws Exception {
		File temporaryFile = File.createTempFile("jchat", "test");
		InputStream defaults = TitleConfigurationTest.class.getClassLoader().getResourceAsStream("scoreboard-titles.yml");
		configuration = new ScoreboardTitleConfiguration(temporaryFile, defaults);
	}

}
