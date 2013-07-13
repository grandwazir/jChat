/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 jChat.java is part of jChat.

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
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import name.richardson.james.bukkit.utilities.command.Command;
import name.richardson.james.bukkit.utilities.command.HelpCommand;
import name.richardson.james.bukkit.utilities.command.invoker.CommandInvoker;
import name.richardson.james.bukkit.utilities.command.invoker.FallthroughCommandInvoker;
import name.richardson.james.bukkit.utilities.permissions.Permissions;
import name.richardson.james.bukkit.utilities.plugin.AbstractPlugin;
import name.richardson.james.bukkit.utilities.plugin.Reloadable;

import name.richardson.james.bukkit.jchat.message.MessagesConfiguration;
import name.richardson.james.bukkit.jchat.message.MessagesManager;
import name.richardson.james.bukkit.jchat.title.*;

@Permissions(permissions = "jchat")
public class jChat extends AbstractPlugin implements Reloadable {

	public static final String COMMAND_LABEL = "jchat";
	public static final String MESSAGES_CONFIG_NAME = "messages.yml";
	public static final String TITLE_CONFIG_NAME = "titles.yml";

	private PluginConfiguration configuration;
	private MessagesConfiguration messagesConfiguration;
	private TitleConfiguration titleConfiguration;
	private TitleManager titleManager;
	private Set<? extends TitleConfigurationEntry> titles;


	@Override
	public String getArtifactId() {
		return "jchat";
	}

	@Override
	public void onEnable() {
		try {
			super.onEnable();
			this.loadConfiguration();
			this.loadTitleConfiguration();
			this.loadMessageConfiguration();
			this.registerListeners();
			this.registerCommands();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void registerCommands() {
		Map<String, Command> commands = new HashMap<String, Command>();
		ReloadCommand reloadCommand = new ReloadCommand(getPermissionManager(), this);
		commands.put(reloadCommand.getName(), reloadCommand);
		RefreshCommand refreshCommand = new RefreshCommand(getPermissionManager(), getServer(), getServer().getPluginManager());
		commands.put(refreshCommand.getName(), refreshCommand);
		HelpCommand helpCommand = new HelpCommand(getPermissionManager(), COMMAND_LABEL, getDescription(), commands);
		CommandInvoker commandInvoker = new FallthroughCommandInvoker(helpCommand);
		commandInvoker.addCommands(commands.values());
		PluginCommand rootCommand = getCommand(COMMAND_LABEL);
		rootCommand.setExecutor(commandInvoker);
		System.out.print(commandInvoker.getCommands().containsKey("reload"));
		System.out.print(commandInvoker.getCommands().get("reload"));
	}

	@Override
	public boolean reload() {
		try {
			loadConfiguration();
			loadTitleConfiguration();
			loadMessageConfiguration();
			titleManager.refreshAll();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private void loadMessageConfiguration()
	throws IOException {
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + MESSAGES_CONFIG_NAME);
		final InputStream defaults = this.getResource(MESSAGES_CONFIG_NAME);
		this.messagesConfiguration = new MessagesConfiguration(file, defaults);
	}

	private void loadConfiguration()
	throws IOException {
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + AbstractPlugin.CONFIG_NAME);
		final InputStream defaults = this.getResource(CONFIG_NAME);
		this.configuration = new PluginConfiguration(file, defaults);
	}

	private void loadTitleConfiguration()
	throws IOException {
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + TITLE_CONFIG_NAME);
		if (this.configuration.isScoreboardEnabled()) {
			final InputStream defaults = this.getResource("scoreboard-" + TITLE_CONFIG_NAME);
			this.titleConfiguration = new ScoreboardTitleConfiguration(file, defaults);
		} else {
			final InputStream defaults = this.getResource(TITLE_CONFIG_NAME);
			this.titleConfiguration = new TitleConfiguration(file, defaults);
		}
		this.titles = titleConfiguration.getTitles();
	}

	private void registerListeners() {
		if (this.configuration.isScoreboardEnabled()) {
			titleManager = new ScoreboardTitleManager(this, this.getServer().getPluginManager(), this.getServer(), (Set<ScoreboardTitleConfigurationEntry>) titles, this.getServer().getScoreboardManager().getNewScoreboard());
		} else {
			titleManager = new TitleManager(this, this.getServer().getPluginManager(), this.getServer(), titles);
		}
		new MessagesManager(this, this.getServer().getPluginManager(), messagesConfiguration);
	}

}
