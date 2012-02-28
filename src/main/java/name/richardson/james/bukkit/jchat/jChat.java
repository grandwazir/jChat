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
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.jchat.management.RefreshCommand;
import name.richardson.james.bukkit.jchat.management.ReloadCommand;
import name.richardson.james.bukkit.jchat.messages.SystemMessageListener;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.internals.Logger;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

public class jChat extends SimplePlugin {

  private final Logger logger = new Logger(jChat.class);
  private final Set<Permission> permissions = new LinkedHashSet<Permission>();

  private CommandManager commandManager;
  private jChatConfiguration configuration;
  private PluginDescriptionFile description;
  private DisplayNameListener displayNameListener;
  private jChatHandler handler;
  private PluginManager pluginManager;
  private SystemMessageListener systemMessageListener;

  public jChat() {
    this.logger.setPrefix("[jChat] ");
  }

  public jChatHandler getHandler(Class<?> parentClass) {
    return new jChatHandler(parentClass, this);
  }

  public jChatConfiguration getjChatConfiguration() {
    return this.configuration;
  }

  public void onDisable() {
    logger.debug("Reverting display names for all online players...");
    handler.revertPlayerDisplayNames(this.getOnlinePlayers());
    logger.info(this.getSimpleFormattedMessage("plugin-disabled", description.getFullName()));
  }

  public void onEnable() {
    description = getDescription();
    pluginManager = getServer().getPluginManager();

    try {
      this.loadConfiguration();
      this.setRootPermission();
      this.registerPermissions();
      this.registerListeners();
      this.registerCommands();
      logger.debug("Setting display names for all online players...");
      handler = new jChatHandler(jChat.class, this);
      handler.setPlayerDisplayNames(this.getOnlinePlayers());
    } catch (final IOException exception) {
      logger.severe(this.getMessage("configuration-missing"));
      this.pluginManager.disablePlugin(this);
    } catch (final IllegalStateException exception) {
      logger.severe(exception.getMessage());
      this.pluginManager.disablePlugin(this);
    } finally {
      if (!this.pluginManager.isPluginEnabled(this))
        return;
    }

    logger.info(this.getSimpleFormattedMessage("plugin-enabled", description.getFullName()));

  }

  private Set<Player> getOnlinePlayers() {
    return new HashSet<Player>(Arrays.asList(this.getServer().getOnlinePlayers()));
  }

  private void loadConfiguration() throws IOException {
    this.configuration = new jChatConfiguration(this);
    if (configuration.isDebugging())
      Logger.setDebugging(this, true);
  }

  private void registerCommands() {
    commandManager = new CommandManager(this);
    this.getCommand("jchat").setExecutor(this.commandManager);
    this.commandManager.addCommand(new RefreshCommand(this));
    this.commandManager.addCommand(new ReloadCommand(this));
  }

  private void registerListeners() {
    displayNameListener = new DisplayNameListener(this);
    systemMessageListener = new SystemMessageListener(this);
    pluginManager.registerEvents(displayNameListener, this);
    pluginManager.registerEvents(systemMessageListener, this);
  }

  private void registerPermissions() {
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
      permissions.add(permission);
    }
  }

}
