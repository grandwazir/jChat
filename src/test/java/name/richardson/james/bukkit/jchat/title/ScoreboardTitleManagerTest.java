package name.richardson.james.bukkit.jchat.title;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScoreboardTitleManagerTest extends TestCase {

	private ScoreboardTitleConfiguration configuration;
	private TitleManager manager;
	private Set<ScoreboardTitleConfigurationEntry> titles;

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

	@Mock
	private Scoreboard scoreboard;

	@Mock
	private Team team;

	@Test
	public void testRefreshAll()
	throws Exception {
		when(playerWithMetaData.hasPermission(anyString())).thenReturn(true);
		List<MetadataValue> list = new LinkedList<MetadataValue>();
		list.add(metadataValue);
		when(playerWithMetaData.getMetadata(TitleManager.METADATA_PREFIX_KEY)).thenReturn(list);
		when(playerWithMetaData.getMetadata(TitleManager.METADATA_SUFFIX_KEY)).thenReturn(list);
		when(playerWithMetaData.getName()).thenReturn("grandwazir");
		when(metadataValue.asString()).thenReturn("test");
		Player[] players = {playerWithMetaData};
		when(server.getOnlinePlayers()).thenReturn(players);
		manager.refreshAll();
		verify(playerWithMetaData, atLeastOnce()).hasPermission("jchat.title.default");
		verify(scoreboard, atLeastOnce()).getTeam("default");
	}

	@Test
	public void testOnPlayerChangedWorld()
	throws Exception {
		when(playerWithoutMetaData.hasPermission(anyString())).thenReturn(true);
		PlayerChangedWorldEvent event = new PlayerChangedWorldEvent(playerWithoutMetaData, null);
		List<MetadataValue> list = new LinkedList<MetadataValue>();
		list.add(metadataValue);
		when(playerWithoutMetaData.getMetadata(TitleManager.METADATA_PREFIX_KEY)).thenReturn(list);
		when(playerWithoutMetaData.getMetadata(TitleManager.METADATA_SUFFIX_KEY)).thenReturn(list);
		when(playerWithoutMetaData.getName()).thenReturn("grandwazir");
		when(metadataValue.asString()).thenReturn("test");
		manager.onPlayerChangedWorld(event);
		verify(playerWithoutMetaData, atLeastOnce()).hasPermission("jchat.title.default");
		verify(scoreboard, atLeastOnce()).getTeam("default");
	}

	@Test
	public void testOnPlayerJoin()
	throws Exception {
		when(playerWithoutMetaData.hasPermission(anyString())).thenReturn(true);
		PlayerJoinEvent event = new PlayerJoinEvent(playerWithoutMetaData, "");
		List<MetadataValue> list = new LinkedList<MetadataValue>();
		list.add(metadataValue);
		when(playerWithoutMetaData.getMetadata(TitleManager.METADATA_PREFIX_KEY)).thenReturn(list);
		when(playerWithoutMetaData.getMetadata(TitleManager.METADATA_SUFFIX_KEY)).thenReturn(list);
		when(playerWithoutMetaData.getName()).thenReturn("grandwazir");
		when(metadataValue.asString()).thenReturn("test");
		manager.onPlayerJoin(event);
		verify(playerWithoutMetaData, atLeastOnce()).hasPermission("jchat.title.default");
		verify(scoreboard, atLeastOnce()).getTeam("default");
	}

	@Before
	public void setUp() throws Exception {
		File temporaryFile = File.createTempFile("jchat", "test");
		InputStream defaults = ScoreboardTitleManagerTest.class.getClassLoader().getResourceAsStream("scoreboard-titles.yml");
		configuration = new ScoreboardTitleConfiguration(temporaryFile, defaults);
		titles = configuration.getTitles();
		when(scoreboard.getTeam(anyString())).thenReturn(team);
		when(scoreboard.registerNewTeam(anyString())).thenReturn(team);
		when(playerWithMetaData.hasMetadata(anyString())).thenReturn(true);
		when(playerWithoutMetaData.hasMetadata(anyString())).thenReturn(false);
		manager = new ScoreboardTitleManager(plugin, pluginManager, server, titles, scoreboard);
	}

}
