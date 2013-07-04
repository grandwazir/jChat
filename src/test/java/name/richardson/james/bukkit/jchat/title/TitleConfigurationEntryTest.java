package name.richardson.james.bukkit.jchat.title;

import java.io.File;
import java.io.InputStream;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TitleConfigurationEntryTest extends TestCase {

	private TitleConfiguration configuration;
	private TitleConfigurationEntry entry;

	@Test
	public void testToString()
	throws Exception {
		Assert.assertNotNull(entry.toString());
	}

	@Test
	public void testGetTitle()
	throws Exception {
		Assert.assertTrue(entry.getTitle(TitleConfigurationEntry.TitleType.PREFIX).contentEquals("&BLUE"));
		Assert.assertTrue(entry.getTitle(TitleConfigurationEntry.TitleType.SUFFIX).contentEquals(""));
	}

	@Test
	public void testGetWeight()
	throws Exception {
		Assert.assertTrue(entry.getWeight() == 0);
	}

	@Test
	public void testGetSuffix()
	throws Exception {
		Assert.assertTrue(entry.getSuffix(), entry.getSuffix().contentEquals(""));
	}

	@Test
	public void testGetPrefix()
	throws Exception {
		Assert.assertTrue(entry.getPrefix().contentEquals("&BLUE"));
	}

	@Test
	public void testGetName()
	throws Exception {
		Assert.assertTrue(entry.getName().contentEquals("default"));
	}

	@Test
	public void testCompareToEqual()
	throws Exception {
		TitleConfigurationEntry entry = mock(TitleConfigurationEntry.class);
		when(entry.getWeight()).thenReturn(0);
		Assert.assertTrue(this.entry.compareTo(entry) == 0);
	}

	@Test
	public void testCompareToHigher()
	throws Exception {
		TitleConfigurationEntry entry = mock(TitleConfigurationEntry.class);
		when(entry.getWeight()).thenReturn(1);
		Assert.assertTrue(this.entry.compareTo(entry) == -1);
	}


	@Test
	public void testCompareToLower()
	throws Exception {
		TitleConfigurationEntry entry = mock(TitleConfigurationEntry.class);
		when(entry.getWeight()).thenReturn(-1);
		Assert.assertTrue(this.entry.compareTo(entry) == 1);
	}




	@Before
	public void setUp()
	throws Exception {
		File temporaryFile = File.createTempFile("jchat", "test");
		InputStream defaults = TitleConfigurationTest.class.getClassLoader().getResourceAsStream("titles.yml");
		configuration = new TitleConfiguration(temporaryFile, defaults);
		for(TitleConfigurationEntry entry : configuration.getTitles()) {
			this.entry = entry;
		}
	}

}
