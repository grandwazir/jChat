/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 *
 * jChat.java is part of jChatPlugin.
 *
 * jChatPlugin is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * jChatPlugin is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * jChatPlugin. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.jchat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import name.richardson.james.bukkit.utilities.command.Permissions;
import name.richardson.james.bukkit.utilities.plugin.AbstractPlugin;
import name.richardson.james.bukkit.utilities.plugin.Reloadable;

import name.richardson.james.bukkit.jchat.title.*;

@Permissions(permissions = "jchat")
public class jChat extends AbstractPlugin implements Reloadable {

	public static final String TITLE_CONFIG_NAME = "titles.yml";
	private PluginConfiguration configuration;
	private TitleConfiguration titleConfiguration;
	private TitleManager titleManager;
	private Set<? extends TitleConfigurationEntry> titles;

	@Override
	public String getArtifactID() {
		return "jchat";
	}

	@Override
	public String getVersion() {
		return this.getDescription().getVersion();
	}

	@Override
	public void onEnable() {
		try {
			this.loadConfiguration();
			this.loadTitleConfiguration();
			this.registerListeners();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean reload() {
		try {
			loadTitleConfiguration();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	protected void loadConfiguration()
	throws IOException {
		super.loadConfiguration();
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + AbstractPlugin.CONFIG_NAME);
		final InputStream defaults = this.getResource(CONFIG_NAME);
		this.configuration = new PluginConfiguration(file, defaults);
	}

	private void loadTitleConfiguration()
	throws IOException {
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + TITLE_CONFIG_NAME);
		final InputStream defaults = this.getResource(TITLE_CONFIG_NAME);
		this.titleConfiguration = new TitleConfiguration(file, defaults);
		this.titles = titleConfiguration.getTitles();
	}

	private void registerListeners() {
		if (this.configuration.isScoreboardEnabled()) {
			titleManager = new ScoreboardTitleManager(this, this.getServer().getPluginManager(), this.getServer(), (Set<ScoreboardTitleConfigurationEntry>) titles, this.getServer().getScoreboardManager().getMainScoreboard());
		} else {
			titleManager = new TitleManager(this, this.getServer().getPluginManager(), this.getServer(), titles);
		}
		titleManager.refreshAll();
	}

}
