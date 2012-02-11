
package name.richardson.james.jchat;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.command.CommandManager;
import name.richardson.james.bukkit.util.configuration.AbstractConfiguration;
import name.richardson.james.jchat.management.RefreshCommand;
import name.richardson.james.jchat.management.ReloadCommand;
import name.richardson.james.jchat.messages.EntityListener;
import name.richardson.james.jchat.messages.PlayerListener;

public class jChat extends Plugin {

  private final Logger logger = new Logger(jChat.class);
  
  private PlayerListener playerListener;
  private final EntityListener entityListener;
  private final Set<Permission> permissions = new LinkedHashSet<Permission>();

  private jChatHandler handler;
  private jChatConfiguration configuration;
  private PluginDescriptionFile description;
  private PluginManager pluginManager;
  private CommandManager commandManager;
  private Permission rootPermission;

  public jChat() {
    entityListener = new EntityListener();
    this.logger.setPrefix("[jChat] ");
  }

  public Set<Permission> getPermissions() {
    return Collections.unmodifiableSet(permissions);
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
      if (!this.pluginManager.isPluginEnabled(this)) return;
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
    playerListener = new PlayerListener();
    pluginManager.registerEvent(Event.Type.PLAYER_CHANGED_WORLD, playerListener, Event.Priority.Monitor, this);
    if (configuration.isColouringDeathMessages()) pluginManager.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this);
    if (configuration.isColouringJoinMessages()) pluginManager.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
    if (configuration.isColouringQuitMessages()) pluginManager.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
  }
  
  public Permission getRootPermission() {
    return rootPermission;
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

  public jChatConfiguration getjChatConfiguration() {
    return this.getjChatConfiguration();
  }

  public jChatHandler getHandler(Class<?> parentClass) {
    return new jChatHandler(parentClass, this);
  }

}
