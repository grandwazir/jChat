/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * jChat.java is part of jChat.
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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.jchat.management.RefreshCommand;
import name.richardson.james.bukkit.jchat.management.ReloadCommand;
import name.richardson.james.bukkit.jchat.messages.SystemMessageListener;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.internals.Logger;
import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class jChat extends SkeletonPlugin {

  /** The jChat configuration. Contains prefixes and suffixes. */
  private jChatConfiguration configuration;
  
  /** The API handler for jChat. */
  private jChatHandler handler;

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.updater.Updatable#getArtifactID()
   */
  public String getArtifactID() {
    return "jchat";
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.updater.Updatable#getGroupID()
   */
  public String getGroupID() {
    return "name.richardson.james.bukkit";
  }

  /**
   * Gets, and returns, a new jChat API handler.
   *
   * @param parentClass the parent class
   * @return the handler
   */
  public jChatHandler getHandler(Class<?> parentClass) {
    return new jChatHandler(parentClass, this);
  }

  /**
   * Gets the jChat configuration.
   *
   * @return the jChat configuration
   */
  public jChatConfiguration getjChatConfiguration() {
    return this.configuration;
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#onDisable()
   */
  public void onDisable() {
    logger.debug("Reverting display names for all online players...");
    handler.revertPlayerDisplayNames(this.getOnlinePlayers());
    logger.info(this.getSimpleFormattedMessage("plugin-disabled", this.getName()));
  }

  /**
   * Gets a set containing all online players.
   *
   * @return online players
   */
  private Set<Player> getOnlinePlayers() {
    return new HashSet<Player>(Arrays.asList(this.getServer().getOnlinePlayers()));
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#loadConfiguration()
   */
  protected void loadConfiguration() throws IOException {
    this.configuration = new jChatConfiguration(this);
    logger.debug("Setting display names for all online players...");
    handler = new jChatHandler(jChat.class, this);
    handler.setPlayerDisplayNames(this.getOnlinePlayers());
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#registerCommands()
   */
  protected void registerCommands() {
    CommandManager commandManager = new CommandManager(this);
    this.getCommand("jchat").setExecutor(commandManager);
    commandManager.addCommand(new RefreshCommand(this));
    commandManager.addCommand(new ReloadCommand(this));
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#registerEvents()
   */
  protected void registerEvents() {
    this.getServer().getPluginManager().registerEvents(new DisplayNameListener(this), this);
    this.getServer().getPluginManager().registerEvents(new SystemMessageListener(this), this);
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#registerPermissions()
   */
  protected void registerPermissions() {
    // register prefixes
    Set<String> permissionNames = new LinkedHashSet<String>();
    permissionNames.addAll(configuration.getPrefixPaths());
    permissionNames.addAll(configuration.getSuffixPaths());
    for (String titlePath : permissionNames) {
      String permissionPath = "jchat." + titlePath;
      Permission permission = new Permission(permissionPath, this.getSimpleFormattedMessage("jchat-permission-node", this.getDescription().getName()));
      if (permissionPath.contains(".default")) {
        permission.setDefault(PermissionDefault.TRUE);
      }
      this.addPermission(permission);
    }
  }

}
