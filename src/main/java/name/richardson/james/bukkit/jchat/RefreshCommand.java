/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 RefreshCommand.java is part of jChat.

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

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.PluginManager;

import org.apache.commons.lang.ObjectUtils;

import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;
import name.richardson.james.bukkit.utilities.command.matcher.OnlinePlayerMatcher;
import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

import name.richardson.james.bukkit.jchat.title.TitleRequestInvalidationEvent;

public class RefreshCommand extends AbstractCommand {

	private static final String PERMISSION_ALL = "jchat.refresh";
	private static final String PERMISSION_SELF = "jchat.refresh.self";
	private static final String PERMISSION_OTHERS = "jchat.refresh.others";

	private final ColourFormatter colourFormatter = new DefaultColourFormatter();
	private final Localisation localisation = new ResourceBundleByClassLocalisation(RefreshCommand.class);
	private final PluginManager pluginManager;
	private final Server server;

	private Player player;

	public RefreshCommand(Server server, PluginManager pluginManager) {
		this.server = server;
		this.pluginManager = pluginManager;
		Matcher matcher = new OnlinePlayerMatcher(server);
		addMatcher(matcher);
	}

	@Override
	public void execute(CommandContext context) {
		if (!setPlayer(context)) return;
		if (!isAuthorised(context)) return;
		TitleRequestInvalidationEvent event = new TitleRequestInvalidationEvent(player);
		pluginManager.callEvent(event);
		String message = colourFormatter.format(localisation.getMessage("refreshed-player-name"), ColourFormatter.FormatStyle.INFO, player.getName());
		context.getCommandSender().sendMessage(message);
		player = null;
	}

	/**
	 * Returns {@code true} if the user is authorised to use this command.
	 * <p/>
	 * Authorisation does not guarantee that the user may use all the features associated with a command.
	 *
	 * @param permissible the permissible requesting authorisation
	 * @return {@code true} if the user is authorised; {@code false} otherwise
	 * @since 6.0.0
	 */
	@Override
	public boolean isAuthorised(Permissible permissible) {
		if (permissible.hasPermission(PERMISSION_ALL)) return true;
		if (permissible.hasPermission(PERMISSION_OTHERS)) return true;
		if (permissible.hasPermission(PERMISSION_SELF)) return true;
		return false;
	}

	private boolean isAuthorised(CommandContext context) {
		boolean targetingSelf = ObjectUtils.equals(player, context.getCommandSender());
		if (targetingSelf && context.getCommandSender().hasPermission("jchat.refresh.self")) return true;
		if (!targetingSelf && context.getCommandSender().hasPermission("jchat.refresh.others")) return true;
		String message = colourFormatter.format(localisation.getMessage("unable-to-target-player"), ColourFormatter.FormatStyle.ERROR, player.getName());
		context.getCommandSender().sendMessage(message);
		return false;
	}

	private boolean setPlayer(CommandContext context) {
		player = server.getPlayerExact(context.getString(0));
		if (player == null && !(context.getCommandSender() instanceof Player)) {
			String message = colourFormatter.format(localisation.getMessage("must-specify-online-player"), ColourFormatter.FormatStyle.ERROR);
			context.getCommandSender().sendMessage(message);
		} else if (player == null) {
			player = (Player) context.getCommandSender();
		}
		return (player != null);
	}

}
