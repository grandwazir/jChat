/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 MessagesConfigurationTest.java is part of jChat.

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
