/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TitleRequestInvalidationEventTest.java is part of jChat.

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

import org.bukkit.entity.Player;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TitleRequestInvalidationEventTest extends TestCase {

	private TitleRequestInvalidationEvent event;

	@Mock
	private Player player;

	@Test
	public void testGetPlayer()
	throws Exception {
		Assert.assertEquals(player, event.getPlayer());

	}

	@Test
	public void testGetHandlers()
	throws Exception {
		Assert.assertNotNull(event.getHandlers());
	}

	@Before
	public void setUp()
	throws Exception {
		event = new TitleRequestInvalidationEvent(player);
	}
}
