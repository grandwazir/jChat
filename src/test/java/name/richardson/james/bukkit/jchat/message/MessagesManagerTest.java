/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 MessagesManagerTest.java is part of jChat.

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

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessagesManagerTest extends TestCase {

	@Mock
	Plugin plugin;

	@Mock
	PluginManager pluginManager;

	@Mock
	MessagesConfiguration messagesConfiguration;

	@Mock
	Player player;

	private MessagesManager manager;

	@Before
	public void setUp()
	throws Exception {
		when(player.getName()).thenReturn("grandwazir");
		when(player.getDisplayName()).thenReturn("[mod] grandwazir");
		manager = new MessagesManager(plugin, pluginManager, messagesConfiguration);
	}

	@Test
	public void testColouringJoinMessages() {
		when(messagesConfiguration.isColouringJoinMessages()).thenReturn(true);
		PlayerJoinEvent event = new PlayerJoinEvent(player, "grandwazir joined the server");
		manager.onPlayerJoin(event);
		Assert.assertTrue(event.getJoinMessage(), event.getJoinMessage().contentEquals("[mod] grandwazir" + ChatColor.YELLOW + " joined the server"));
	}

	@Test
	public void testColouringQuitMessages() {
		when(messagesConfiguration.isColouringQuitMessages()).thenReturn(true);
		PlayerQuitEvent event = new PlayerQuitEvent(player, "grandwazir left the server");
		manager.onPlayerQuit(event);
		Assert.assertTrue(event.getQuitMessage(), event.getQuitMessage().contentEquals("[mod] grandwazir" + ChatColor.YELLOW + " left the server"));
	}


	@Test
	public void testColouringDeathMessages() {
		when(messagesConfiguration.isColouringDeathMessages()).thenReturn(true);
		PlayerDeathEvent event = new PlayerDeathEvent(player, null, 0, "grandwazir has died");
		manager.onEntityDeath(event);
		Assert.assertTrue(event.getDeathMessage(), event.getDeathMessage().contentEquals("[mod] grandwazir" + ChatColor.YELLOW + " has died"));
	}


}
