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
import java.util.Collections;
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
import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.command.CommandManager;

public class jChat extends Plugin {

  private final Logger logger = new Logger(jChat.class);
  private final Set<Permission> permissions = new LinkedHashSet<Permission>();

  private CommandManager commandManager;
  private jChatConfiguration configuration;
  private PluginDescriptionFile description;
  private DisplayNameListener displayNameListener;
  private jChatHandler handler;
  private PluginManager pluginManager;
  private Permission rootPermission;
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

  public Set<Permission> getPermissions() {
    return Collections.unmodifiableSet(permissions);
  }

  public Permission getRootPermission() {
    return rootPermission;
  }

  public void onDisable() {
    logger.debug("Reverting display names for all online players...");
    handler.revertPlayerDisplayNames(this.getOnlinePlayers());
    logger.info("jChat is disabled.");
  }

  public void onEnable() {
    description = getDescription();
    pluginManager = getServer().getPluginManager();

    try {
      this.loadConfiguration();
      this.setPermission();
      this.registerListeners();
      this.registerPermissions();
      this.registerCommands();
      logger.debug("Setting display names for all online players...");
      handler = new jChatHandler(jChat.class, this);
      handler.setPlayerDisplayNames(this.getOnlinePlayers());
    } catch (final IOException exception) {
      logger.severe("Unable to load configuration!");
      this.pluginManager.disablePlugin(this);
    } catch (final IllegalStateException exception) {
      logger.severe(exception.getMessage());
      this.pluginManager.disablePlugin(this);
    } finally {
      if (!this.pluginManager.isPluginEnabled(this))
        return;
    }

    logger.info(String.format("%s is enabled.", description.getFullName()));

  }

  private Set<Player> getOnlinePlayers() {
    return new HashSet<Player>(Arrays.asList(this.getServer().getOnlinePlayers()));
  }

  private void loadConfiguration() throws IOException {
    this.configuration = new jChatConfiguration(this);
    if (configuration.isDebugging()) {
      Logger.enableDebugging(this.getDescription().getName().toLowerCase());
      configuration.logValues();
    }
  }

  private void registerCommands() {
    commandManager = new CommandManager(this.getDescription());
    this.getCommand("jchat").setExecutor(this.commandManager);
    this.commandManager.registerCommand("refresh", new RefreshCommand(this));
    this.commandManager.registerCommand("reload", new ReloadCommand(this));
  }

  private void registerListeners() {
    displayNameListener = new DisplayNameListener(this);
    systemMessageListener = new SystemMessageListener(this);
    pluginManager.registerEvents(displayNameListener, this);
    pluginManager.registerEvents(systemMessageListener, this);
  }

  private void registerPermissions() {
    // register root permission
    rootPermission = new Permission("jchat.*", "Allow access to all jChat commands", PermissionDefault.OP);
    pluginManager.addPermission(rootPermission);
    // register prefixes
    Set<String> permissionNames = new LinkedHashSet<String>();
    permissionNames.addAll(configuration.getPrefixPaths());
    permissionNames.addAll(configuration.getSuffixPaths());
    for (String titlePath : permissionNames) {
      String permissionPath = "jchat." + titlePath;
      Permission permission = new Permission(permissionPath, "jChat permission node");
      logger.debug(String.format("Creating permission node: %s", permissionPath));
      pluginManager.addPermission(permission);
      permissions.add(permission);
      // if the default prefix make it a permission default
      if (permissionPath.contains(".default")) {
        permission.setDefault(PermissionDefault.TRUE);
      }
    }
  }

}
