/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 *
 * RefreshCommand.java is part of jChatPlugin.
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

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import org.apache.commons.lang.ObjectUtils;

import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.Context;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.command.matcher.OnlinePlayerMatcher;
import name.richardson.james.bukkit.utilities.formatters.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;
import name.richardson.james.bukkit.utilities.permissions.Permissions;

import name.richardson.james.bukkit.jchat.title.TitleRequestInvalidationEvent;

@Permissions(permissions = {"jchat.refresh", "jchat.refresh.self", "jchat.refresh.others"})
public class RefreshCommand extends AbstractCommand {

	private final PluginManager pluginManager;
	private Player player;

	public RefreshCommand(PermissionManager permissionManager, Server server, PluginManager pluginManager) {
		super(permissionManager);
		this.pluginManager = pluginManager;
		Matcher matcher = new OnlinePlayerMatcher(server);
		addMatcher(matcher);
	}

	@Override
	public void execute(Context context) {
		if (!setPlayer(context)) return;
		if (!isAuthorised(context)) return;
		TitleRequestInvalidationEvent event = new TitleRequestInvalidationEvent(player);
		pluginManager.callEvent(event);
		String message = getColouredMessage(ColourScheme.Style.INFO, "refreshed-player-name", player.getName());
		context.getCommandSender().sendMessage(message);
		player = null;
	}

	private boolean setPlayer(Context context) {
		if (context.has(0)) {
			context.getPlayer(0);
		}
		if (player == null && context.isConsoleCommandSender()) {
			String message = getColouredMessage(ColourScheme.Style.ERROR, "must-specify-online-player");
			context.getCommandSender().sendMessage(message);
		} else if (player == null) {
			player = (Player) context.getCommandSender();
		}
		return (player != null);
	}

	private boolean isAuthorised(Context context) {
		boolean targetingSelf = ObjectUtils.equals(player, context.getCommandSender());
		if (targetingSelf && context.getCommandSender().hasPermission("jchat.refresh.self")) return true;
		if (!targetingSelf && context.getCommandSender().hasPermission("jchat.refresh.others")) return true;
		String message = getColouredMessage(ColourScheme.Style.ERROR, "unable-to-target-player", player.getName());
		context.getCommandSender().sendMessage(message);
		return false;
	}

}
