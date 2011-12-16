
package name.richardson.james.jchat;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import name.richardson.james.jchat.messages.EntityListener;
import name.richardson.james.jchat.messages.PlayerListener;
import name.richardson.james.jchat.util.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class jChat extends JavaPlugin {

  private final static Logger logger = new Logger(jChat.class);
  private final static jChatHandler handler = new jChatHandler(jChat.class);
  
  private final PlayerListener playerListener;
  private final EntityListener entityListener;
  private final Set<Permission> permissions = new LinkedHashSet<Permission>();
  
  private static jChat instance;
  
  private PluginDescriptionFile description;
  private PluginManager pluginManager;
  private CommandManager commandManager;
  
  
  public jChat() {
    jChat.instance = this;
    commandManager = new CommandManager();
    playerListener = new PlayerListener();
    entityListener = new EntityListener();
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
      handler.setPlayerDisplayNames(this.getOnlinePlayers());
    } catch (final IOException exception) {
      jChat.logger.severe("Unable to load configuration!");
      this.pluginManager.disablePlugin(this);
    } catch (final IllegalStateException exception) {
      jChat.logger.severe(exception.getMessage());
      this.pluginManager.disablePlugin(this);
    } finally {
      if (!this.pluginManager.isPluginEnabled(this)) return;
    }
    
    logger.info(String.format("%s is enabled.", description.getFullName()));
      
  }

  private Set<Player> getOnlinePlayers() {
    return new HashSet<Player>(Arrays.asList(this.getServer().getOnlinePlayers()));
  }
  
  private void registerListeners() {
    jChatConfiguration configuration = jChatConfiguration.getInstance();
    pluginManager.registerEvent(Event.Type.PLAYER_CHANGED_WORLD, playerListener, Event.Priority.Monitor, this);
    if (configuration.isColouringDeathMessages()) pluginManager.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this); 
    if (configuration.isColouringJoinMessages()) pluginManager.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
    if (configuration.isColouringQuitMessages()) pluginManager.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
  }
  
  private void registerCommands() {
    this.getCommand("jchat").setExecutor(this.commandManager);
    this.commandManager.registerCommand("refresh", new RefreshCommand(this));
  }
  
  private void registerPermissions() {
    // register prefixes
    Set<String> permissionNames = new LinkedHashSet<String>();
    permissionNames.addAll(jChatConfiguration.getInstance().getPrefixPaths());
    permissionNames.addAll(jChatConfiguration.getInstance().getSuffixPaths());
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

  private void loadConfiguration() throws IOException {
    jChatConfiguration configuration = new jChatConfiguration();
    if (configuration.isDebugging()) {
      Logger.enableDebugging();
      configuration.logValues();
    }
  }

  public static jChat getInstance() {
    return instance;
  }

  public Set<Permission> getPermissions() {
    return Collections.unmodifiableSet(permissions);
  }

  
}
