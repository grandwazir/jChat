package name.richardson.james.bukkit.jchat.title;

import java.io.File;
import java.io.InputStream;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class ScoreboardTitleConfigurationEntryTest extends TestCase {

	private ScoreboardTitleConfiguration configuration;
	private ScoreboardTitleConfigurationEntry entry;

	@Test
	public void testToString()
	throws Exception {
		Assert.assertNotNull(entry.toString());
	}

	@Test
	public void testIsAppendingTeamName()
	throws Exception {
		Assert.assertTrue(entry.isAppendingTeamName());
	}

	@Test
	public void testGetDisplayName()
	throws Exception {
		Assert.assertTrue(entry.getDisplayName(), entry.getDisplayName().contentEquals("Guest"));
	}

	@Test
	public void testCanSeeFriendlyInvisibles()
	throws Exception {
		Assert.assertFalse(entry.canSeeFriendlyInvisibles());
	}

	@Test
	public void testCanHurtFriendlies()
	throws Exception {
		Assert.assertFalse(entry.canHurtFriendlies());
	}

	@Before
	public void setUp()
	throws Exception {
		File temporaryFile = File.createTempFile("jchat", "test");
		InputStream defaults = ScoreboardTitleConfigurationTest.class.getClassLoader().getResourceAsStream("scoreboard-titles.yml");
		configuration = new ScoreboardTitleConfiguration(temporaryFile, defaults);
		for(ScoreboardTitleConfigurationEntry entry : configuration.getTitles()) {
			this.entry = entry;
		}
	}

}
