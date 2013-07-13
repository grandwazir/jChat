/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TitleConfigurationEntryTest.java is part of jChat.

 jChat is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 jChat is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 jChat. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

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
		Assert.assertTrue(entry.getTitle(TitleConfigurationEntry.TitleType.PREFIX).contentEquals("§e"));
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
		Assert.assertTrue(entry.getPrefix().contentEquals("§e"));
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
