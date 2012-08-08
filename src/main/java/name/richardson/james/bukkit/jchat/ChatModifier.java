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

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatModifier implements Callable<Object> {

  public enum Type {
    PREFIX,
    SUFFIX
  }

  private final String playerName;

  private final jChat plugin;
  
  private final Type type;

  public ChatModifier(jChat plugin, final Player player, final Type type) {
    this.plugin = plugin;
    this.playerName = player.getName();
    this.type = type;
  }

  public String call() {
    return this.calcuateTitle();
  }

  private String calcuateTitle() {
    String title = plugin.getTitle(this.playerName, this.type);
    if (this.type.equals(Type.SUFFIX)) {
      title = title + ChatColor.WHITE;
    }
    return title;
  }

}
