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
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.bukkit.entity.Player;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.jchat.ChatModifier.Type;
import name.richardson.james.bukkit.jchat.management.RefreshCommand;
import name.richardson.james.bukkit.jchat.management.ReloadCommand;
import name.richardson.james.bukkit.jchat.messages.SystemMessageListener;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class jChat extends SkeletonPlugin {

  /** The jChat configuration. Contains prefixes and suffixes. */
  private jChatConfiguration configuration;

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.updater.Updatable#getArtifactID()
   */
  public String getArtifactID() {
    return "jchat";
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.updater.Updatable#getGroupID()
   */
  public String getGroupID() {
    return "name.richardson.james.bukkit";
  }

  public void invalidatePlayerMetaData(final Player player) {
    final String[] keys = { "chatPrefix", "chatSuffix" };
    for (final String key : keys) {
      for (final MetadataValue value : player.getMetadata(key)) {
        value.invalidate();
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#onDisable()
   */
  @Override
  public void onDisable() {
    this.logger.debug("Reverting display names for all online players...");
    Player[] players = this.getServer().getOnlinePlayers();
    this.revertPlayerDisplayName(players);
    this.removePlayerMetaData(players);
    this.logger.info(this.getSimpleFormattedMessage("plugin-disabled", this.getName()));
  }

  public void reload() throws IOException {
    this.loadConfiguration();
    this.establishPlayerDisplayNames();
  }

  /**
   * Revert player display name.
   * 
   * @param players the player
   */
  public void revertPlayerDisplayName(final Player player) {
    player.setDisplayName(player.getName());
    player.setPlayerListName(player.getName());
    this.logger.debug(String.format("%s's display name has been reset.", player.getName()));
  }

  /**
   * Revert player display names.
   * 
   * @param players the players
   */
  public void revertPlayerDisplayName(final Player[] players) {
    for (final Player player : players) {
      this.revertPlayerDisplayName(player);
    }
  }

  public void setPlayerDisplayName(final Player player) {
    final StringBuilder displayNameBuilder = new StringBuilder();
    displayNameBuilder.append(player.getMetadata("chatPrefix").get(0).asString());
    displayNameBuilder.append(player.getName());
    displayNameBuilder.append(player.getMetadata("chatSuffix").get(0).asString());
    final String displayName = ColourFormatter.replace("&", displayNameBuilder.toString());
    player.setDisplayName(displayName);
  }

  /**
   * Sets the player display names.
   * 
   * @param players the new player display names
   */
  public void setPlayerDisplayName(final Player[] players) {
    for (final Player player : players) {
      this.setPlayerDisplayName(player);
    }
  }

  private void establishPlayerDisplayNames() {
    // register initial prefixes
    this.logger.debug("Setting display names for all online players...");
    final Player[] players = this.getServer().getOnlinePlayers();
    this.setPlayerMetaData(players);
    this.setPlayerDisplayName(players);
  }

  private void setPlayerPrefix(final Player player) {
    final Callable<Object> title = new ChatModifier(player, ChatModifier.Type.PREFIX);
    final LazyMetadataValue value = new LazyMetadataValue(this, title);
    player.setMetadata("chatPrefix", value);
  }

  private void setPlayerSuffix(final Player player) {
    final Callable<Object> title = new ChatModifier(player, ChatModifier.Type.SUFFIX);
    final LazyMetadataValue value = new LazyMetadataValue(this, title);
    player.setMetadata("chatSuffix", value);
  }

  protected String getTitle(final String playerName, final Type type) {
    String title = "";
    final Player player = this.getServer().getPlayerExact(playerName);
    for (final Permission permission : this.getPermissions()) {
      this.logger.debug(String.format("Checking to see if %s has the permission node: %s", player.getName(), permission.getName()));
      if (player.hasPermission(permission) && permission.getName().contains(type.toString().toLowerCase())) {
        title = this.configuration.getTitle(permission.getName().replaceFirst("jchat.", ""));
        break;
      }
    }
    return ColourFormatter.replace("&", title);
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#loadConfiguration
   * ()
   */
  @Override
  protected void loadConfiguration() throws IOException {
    this.configuration = new jChatConfiguration(this);
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#registerCommands
   * ()
   */
  @Override
  protected void registerCommands() {
    final CommandManager commandManager = new CommandManager(this);
    this.getCommand("jchat").setExecutor(commandManager);
    commandManager.addCommand(new RefreshCommand(this));
    commandManager.addCommand(new ReloadCommand(this));
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#registerEvents
   * ()
   */
  @Override
  protected void registerEvents() {
    this.getServer().getPluginManager().registerEvents(new DisplayNameListener(this), this);
    this.getServer().getPluginManager().registerEvents(new SystemMessageListener(this.configuration), this);
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#
   * registerPermissions()
   */
  @Override
  protected void registerPermissions() {
    // register prefixes
    final Set<String> permissionNames = new LinkedHashSet<String>();
    permissionNames.addAll(this.configuration.getPrefixPaths());
    permissionNames.addAll(this.configuration.getSuffixPaths());
    for (final String titlePath : permissionNames) {
      final String permissionPath = "jchat." + titlePath;
      final Permission permission = new Permission(permissionPath, this.getSimpleFormattedMessage("jchat-permission-node", this.getDescription().getName()));
      if (permissionPath.contains(".default")) {
        permission.setDefault(PermissionDefault.TRUE);
      }
      this.addPermission(permission);
    }
    this.establishPlayerDisplayNames();
  }

  protected void removePlayerMetaData(final Player player) {
    final String[] keys = { "chatPrefix", "chatSuffix" };
    for (final String key : keys) {
      player.removeMetadata(key, this);
    }
  }
  
  protected void removePlayerMetaData(final Player[] players) {
    for (final Player player : players) {
      removePlayerMetaData(player);
    }
  }

  protected void setPlayerMetaData(final Player player) {
    if (!player.hasMetadata("chatPrefix")) {
      this.setPlayerPrefix(player);
    }
    if (!player.hasMetadata("chatSuffix")) {
      this.setPlayerSuffix(player);
    }
  }

  protected void setPlayerMetaData(final Player[] players) {
    for (final Player player : players) {
      this.setPlayerMetaData(player);
    }
  }

}
