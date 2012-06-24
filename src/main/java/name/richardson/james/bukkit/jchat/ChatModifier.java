/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ChatModifier.java is part of jChat.
 * 
 * jChat is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * jChat is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * jChat. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.jchat;

import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import name.richardson.james.bukkit.utilities.internals.Logger;

public class ChatModifier implements Callable<Object> {

  public enum Type {
    PREFIX,
    SUFFIX
  }

  private static final Logger logger = new Logger(ChatModifier.class);

  private static final jChat plugin = ChatModifier.getPlugin();

  private static final jChat getPlugin() {
    return (jChat) Bukkit.getServer().getPluginManager().getPlugin("jChat");
  }

  private final Player player;

  private final Type type;

  public ChatModifier(final Player player, final Type type) {
    if (ChatModifier.plugin == null) {
      ChatModifier.getPlugin();
    }
    this.player = player;
    this.type = type;
  }

  public String call() {
    return this.calcuateTitle();
  }

  private String calcuateTitle() {
    String title = ChatModifier.plugin.getTitle(this.player, this.type);
    if (this.type.equals(Type.SUFFIX)) {
      title = title + ChatColor.WHITE;
    }
    ChatModifier.logger.debug(String.format("Using title: %s", title));
    return title;
  }

}
