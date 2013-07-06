/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TitleConfigurationTest.java is part of jChat.

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
