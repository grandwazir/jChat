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
