package name.richardson.james.bukkit.jchat.title;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TitleManagerTest extends TestCase {

	private TitleConfiguration configuration;
	private TitleManager manager;

	@Mock
	private Plugin plugin;

	@Mock
	private PluginManager pluginManager;

	@Mock
	private Server server;

	@Mock
	private Player playerWithoutMetaData;

	@Mock
	private Player playerWithMetaData;

	@Mock
	private LazyMetadataValue metadataValue;

	@Test
	public void testRefreshAll()
	throws Exception {
		List<MetadataValue> list = new LinkedList<MetadataValue>();
		list.add(metadataValue);
		when(playerWithMetaData.getMetadata(TitleManager.METADATA_PREFIX_KEY)).thenReturn(list);
		when(playerWithMetaData.getMetadata(TitleManager.METADATA_SUFFIX_KEY)).thenReturn(list);
		when(playerWithMetaData.getName()).thenReturn("grandwazir");
		when(metadataValue.asString()).thenReturn("test");
		Player[] players = {playerWithMetaData};
		when(server.getOnlinePlayers()).thenReturn(players);
		manager.refreshAll();
		verify(metadataValue, Mockito.times(2)).asString();
		verify(playerWithMetaData).setDisplayName("testgrandwazirtest§r");
		verify(playerWithMetaData).setDisplayName("testgrandwazirtest§r");
	}

	@Test
	public void testOnPlayerChangedWorld()
	throws Exception {
		PlayerChangedWorldEvent event = new PlayerChangedWorldEvent(playerWithMetaData, null);
		List<MetadataValue> list = new LinkedList<MetadataValue>();
		list.add(metadataValue);
		when(playerWithMetaData.getMetadata(TitleManager.METADATA_PREFIX_KEY)).thenReturn(list);
		when(playerWithMetaData.getMetadata(TitleManager.METADATA_SUFFIX_KEY)).thenReturn(list);
		when(playerWithMetaData.getName()).thenReturn("grandwazir");
		when(metadataValue.asString()).thenReturn("test");
		manager.onPlayerChangedWorld(event);
		verify(metadataValue, Mockito.times(2)).asString();
		verify(playerWithMetaData).setDisplayName("testgrandwazirtest§r");
	}

	@Test
	public void testOnPlayerJoin()
	throws Exception {
		PlayerJoinEvent event = new PlayerJoinEvent(playerWithoutMetaData, "");
		List<MetadataValue> list = new LinkedList<MetadataValue>();
		list.add(metadataValue);
		when(playerWithoutMetaData.getMetadata(TitleManager.METADATA_PREFIX_KEY)).thenReturn(list);
		when(playerWithoutMetaData.getMetadata(TitleManager.METADATA_SUFFIX_KEY)).thenReturn(list);
		when(playerWithoutMetaData.getName()).thenReturn("grandwazir");
		when(metadataValue.asString()).thenReturn("test");
		manager.onPlayerJoin(event);
		verify(metadataValue, Mockito.times(2)).asString();
		verify(playerWithoutMetaData).setDisplayName("testgrandwazirtest§r");
	}


	@Before
	public void setUp()
	throws Exception {
		File temporaryFile = File.createTempFile("jchat", "test");
		InputStream defaults = TitleConfigurationTest.class.getClassLoader().getResourceAsStream("titles.yml");
		configuration = new TitleConfiguration(temporaryFile, defaults);
		manager = new TitleManager(plugin, pluginManager, server, configuration.getTitles());
		when(playerWithoutMetaData.hasPermission((String) EasyMock.anyObject())).thenReturn(true);
		when(playerWithMetaData.hasPermission((String) EasyMock.anyObject())).thenReturn(true);
		when(playerWithMetaData.hasMetadata(anyString())).thenReturn(true);
		when(playerWithoutMetaData.hasMetadata(anyString())).thenReturn(false);
	}

}
