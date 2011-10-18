
package name.richardson.james.jchat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.richardson.james.jchat.listeners.jChatEntityListener;
import name.richardson.james.jchat.listeners.jChatPlayerListener;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class jChat extends JavaPlugin {

  private final static File confFile = new File("plugins/jChat/config.yml");
  private final static Logger logger = Logger.getLogger("Minecraft");
  
  private final jChatPlayerListener playerListener;
  private final jChatEntityListener entityListener;
  
  private static jChat instance;
  
  private PluginDescriptionFile desc;
  private PluginManager pm;
  
  public Configuration conf;
  public PermissionHandler externalPermissions;
  public Map<String, UUID> players = new HashMap<String, UUID>();
  
  public jChat() {
    jChat.instance = this;
    playerListener = new jChatPlayerListener(this);
    entityListener = new jChatEntityListener();
  }

  public static void log(final Level level, final String msg) {
    logger.log(level, "[jChat] " + msg);
  }

  public void onDisable() {
    for (final Player player : getServer().getOnlinePlayers())
      revertDisplayName(player);
    players.clear();
    log(Level.INFO, String.format("%s is disabled", desc.getName()));
  }

  public void onEnable() {
    desc = getDescription();
    pm = getServer().getPluginManager();
    conf = new Configuration(confFile);

    // load configuration
    loadConfiguration();
    connectPermissions();

    // register events
    pm.registerEvent(Event.Type.PLAYER_CHANGED_WORLD, playerListener, Event.Priority.Monitor, this);
    
    if (conf.getBoolean("colourMessages.join", true)) {
    pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
    }
    
    if (conf.getBoolean("colourMessages.death", true)) {
      pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this);
    }
    
    if (conf.getBoolean("colourMessages.quit", true)) {
      pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
    }

    // apply initial colours
    for (final Player player : getServer().getOnlinePlayers()) {
      setDisplayName(player);
    }
      
    log(Level.INFO, String.format("%s is enabled!", desc.getFullName()));
  }

  public void revertDisplayName(final Player player) {
    player.setDisplayName(player.getName());
  }

  public void setDisplayName(final Player player) {
    final String prefix = searchNodes(player, "prefix");
    final String suffix = searchNodes(player, "suffix") + "§f";
    player.setDisplayName(prefix + player.getName() + suffix);
    players.put(player.getName(), player.getWorld().getUID());
  }

  private void createConfiguration() {
    try {
      log(Level.WARNING, String.format("Configuration file not found!", confFile.getPath()));
      log(Level.INFO, String.format("Creating new configuration: %s", confFile.getPath()));
      confFile.getParentFile().mkdirs();
      confFile.createNewFile();
      conf.getString("colourMessages", "");
      conf.getBoolean("colourMessages.death", true);
      conf.getBoolean("colourMessages.join", true);
      conf.getBoolean("colourMessages.quit", true);
      conf.getString("prefix", null);
      conf.getString("prefix.guest", "&2");
      conf.getString("prefix.admin", "&e");
      conf.getString("suffix", null);
      conf.getString("suffix.guest", null);
      conf.getString("suffix.admin", null);
      conf.save();
    } catch (final IOException e) {
      log(Level.SEVERE, String.format("Unable to load configuration: %s", confFile.getPath()));
      pm.disablePlugin(instance);
    }
  }

  private void loadConfiguration() {
    conf.load();
    if (conf.getAll().isEmpty()) {
      createConfiguration();
    }
    log(Level.INFO, String.format("Loaded configuration: %s", confFile.getPath()));
  }

  private String searchNodes(final Player player, final String parentNode) {
    for (final String node : conf.getKeys(parentNode)) {
      final String permission = String.format("%s.%s.%s", desc.getName().toLowerCase(), parentNode, node);
      final String path = String.format("%s.%s", parentNode, node);
      String title = conf.getString(path);
      
      if (title != null) {
        if (player.hasPermission(permission)) {
            return title.replace("&", "§");
        } else if (externalPermissions != null) {
          if (externalPermissions.has(player, permission)) {
            return title.replace("&", "§");
          }
        }
      } else {
        log(Level.WARNING, "Found a " + parentNode + " that is not defined: " + node);
      }
    }
    return "";
  }
  
  private void connectPermissions() {
    final Plugin permissionsPlugin = getServer().getPluginManager().getPlugin("Permissions");
    if (permissionsPlugin != null) {
      externalPermissions = ((Permissions) permissionsPlugin).getHandler();
      log(Level.INFO, String.format("External permissions system found: %s", ((Permissions) permissionsPlugin).getDescription().getFullName()));
    }
  }
  
}
