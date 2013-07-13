/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ScoreboardTitleManager.java is part of jChat.

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

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import name.richardson.james.bukkit.utilities.logging.PrefixedLogger;

public class ScoreboardTitleManager extends TitleManager {

	public static final Logger LOGGER = PrefixedLogger.getLogger(TitleManager.class);

	private final Scoreboard scoreboard;
	private final Server server;

	public ScoreboardTitleManager(Plugin plugin, PluginManager pluginManager, Server server, Set<ScoreboardTitleConfigurationEntry> titles, Scoreboard scoreboard) {
		super(plugin, pluginManager, server, titles);
		this.scoreboard = scoreboard;
		this.server = server;
		setTeams();
	}

	public void setTeams() {
		Set<ScoreboardTitleConfigurationEntry> titles = (Set<ScoreboardTitleConfigurationEntry>) getTitles();
		for (ScoreboardTitleConfigurationEntry title : titles) {
			Team team = (scoreboard.getTeam(title.getName()) == null) ? scoreboard.registerNewTeam(title.getName()) : scoreboard.getTeam(title.getName());
			team.setPrefix(title.getTitle(TitleConfigurationEntry.TitleType.PREFIX));
			team.setSuffix(title.getTitle(TitleConfigurationEntry.TitleType.SUFFIX));
			team.setDisplayName(title.getDisplayName());
			team.setAllowFriendlyFire(title.canHurtFriendlies());
			team.setCanSeeFriendlyInvisibles(title.canSeeFriendlyInvisibles());
			if (title.isAppendingTeamName()) {
				team.setSuffix(team.getSuffix() + ChatColor.WHITE + " (" + team.getDisplayName() + ")");
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		super.onPlayerJoin(event);
		this.updateScoreboard(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		super.onPlayerChangedWorld(event);
		this.updateScoreboard(event.getPlayer());
	}

	@Override
	public void refreshAll() {
		super.refreshAll();
		setTeams();
		for (Player player : server.getOnlinePlayers()) {
			updateScoreboard(player);
		}
	}

	private void updateScoreboard(Player player) {
		Set<ScoreboardTitleConfigurationEntry> titles = (Set<ScoreboardTitleConfigurationEntry>) getTitles();
		for (ScoreboardTitleConfigurationEntry entry : titles) {
			if (player.hasPermission(TitleManager.PERMISSION_PREFIX + entry.getName())) {
				LOGGER.log(Level.FINE, "Adding " + player.getName() + " to " + entry.getName() + " team.");
				Team team = scoreboard.getTeam(entry.getName());
				team.addPlayer(player);
				player.setScoreboard(scoreboard);
				break;
			}
		}
	}

}
