package name.richardson.james.bukkit.jchat.title;

import java.util.Set;

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

public class ScoreboardTitleManager extends TitleManager {

	private final Scoreboard scoreboard;
	private final Server server;
	private final Set<ScoreboardTitleConfigurationEntry> titles;

	public ScoreboardTitleManager(Plugin plugin, PluginManager pluginManager, Server server, Set<ScoreboardTitleConfigurationEntry> titles, Scoreboard scoreboard) {
		super(plugin, pluginManager, server, titles);
		this.scoreboard = scoreboard;
		this.titles = titles;
		this.server = server;
		setTeams();
	}

	public void setTeams() {
		for (ScoreboardTitleConfigurationEntry title : titles) {
			Team team = scoreboard.registerNewTeam(title.getName());
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
		for (Player player : server.getOnlinePlayers()) {
			updateScoreboard(player);
		}
	}

	private void updateScoreboard(Player player) {
		for (ScoreboardTitleConfigurationEntry entry : titles) {
			if (player.hasPermission(TitleManager.PERMISSION_PREFIX + entry.getName())) {
				scoreboard.getTeam(entry.getName()).addPlayer(player);
				player.setScoreboard(scoreboard);
				break;
			}
		}
	}

}
