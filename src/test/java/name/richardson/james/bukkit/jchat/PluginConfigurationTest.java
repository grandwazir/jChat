/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PluginConfigurationTest.java is part of jChat.

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
