/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ScoreboardTitleConfigurationEntryTest.java is part of jChat.

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
