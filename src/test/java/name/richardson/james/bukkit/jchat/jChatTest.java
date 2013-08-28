/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 jChatTest.java is part of jChat.

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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class jChatTest extends TestCase {

	private jChat plugin;

	@Mock
	private Server server;

	@Mock
	private PluginManager pluginManager;

	@Mock
	private BukkitScheduler scheduler;

	private PluginCommand pluginCommand;

	private PluginDescriptionFile pluginDescriptionFile;

	private File folder = new File("/tmp");

	@Mock
	private ScoreboardManager scoreboardManager;

	@Mock
	private Scoreboard scoreboard;

	@Mock
	private Team team;

	@Test
	public void testReload()
	throws Exception {
		plugin.onEnable();
		plugin.reload();
	}

	@Test
	public void testEnableWithScoreBoard()
	throws Exception {
		when(server.getScoreboardManager()).thenReturn(scoreboardManager);
		when(scoreboardManager.getNewScoreboard()).thenReturn(scoreboard);
		when(scoreboard.getTeam(anyString())).thenReturn(team);
		Field field = plugin.getClass().getDeclaredField("configuration");
		field.setAccessible(true);
		PluginConfiguration pluginConfiguration = mock(PluginConfiguration.class);
		when(pluginConfiguration.isScoreboardEnabled()).thenReturn(true);
		field.set(plugin, pluginConfiguration);
		// Access the methods
		Method method = plugin.getClass().getDeclaredMethod("loadTitleConfiguration");
		method.setAccessible(true);
		method.invoke(plugin);
		method = plugin.getClass().getDeclaredMethod("registerListeners");
		method.setAccessible(true);
		method.invoke(plugin);
	}

	@Test
	public void testOnEnableWithoutScoreBoard()
	throws Exception {
 		plugin.onEnable();
	}

	@Before
	public void setUp()
	throws Exception {
		plugin = new jChat();
		pluginDescriptionFile = new PluginDescriptionFile("Test", "1.0", null);
		when(server.getLogger()).thenReturn(Logger.getAnonymousLogger());
		when(server.getPluginManager()).thenReturn(pluginManager);
		when(server.getScheduler()).thenReturn(scheduler);
		when(server.getOnlinePlayers()).thenReturn(new Player[]{});
		// Get an instance of PluginCommand
		Class pluginClass = PluginCommand.class;
		Constructor constructor = pluginClass.getDeclaredConstructor(String.class, Plugin.class);
		constructor.setAccessible(true);
		pluginCommand = (PluginCommand) constructor.newInstance("jchat", plugin);
		when(server.getPluginCommand(anyString())).thenReturn(pluginCommand);
		// Set server - required for permission setting
		Field field = Bukkit.class.getDeclaredField("server");
		field.setAccessible(true);
		field.set(null, server);
		// Initalize the plugin
		Class<?> clazz = plugin.getClass().getSuperclass();
		Method method = clazz.getDeclaredMethod("initialize", PluginLoader.class, Server.class, PluginDescriptionFile.class, File.class, File.class, ClassLoader.class);
		method.setAccessible(true);
		method.invoke(plugin, null, server, pluginDescriptionFile, folder, null, getClass().getClassLoader());
	}

	@After
	public void tearDown() throws Exception {
		Field field = Bukkit.class.getDeclaredField("server");
		field.setAccessible(true);
		field.set(null, null);
	}

}
