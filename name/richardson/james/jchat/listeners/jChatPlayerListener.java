package name.richardson.james.jchat.listeners;

import name.richardson.james.jchat.jChat;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;


public class jChatPlayerListener extends PlayerListener {
  private final jChat plugin;
  
  
  public jChatPlayerListener(jChat plugin) {
    this.plugin = plugin;
  }
  
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    
    plugin.setDisplayName(event.getPlayer());
    if (plugin.conf.getBoolean("colourMessages.join", true)) {
      event.setJoinMessage((colourMessage(player, event.getJoinMessage())));
    }
  }
  
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    
    plugin.players.remove(player.getName());
    if (plugin.conf.getBoolean("colourMessages.quit", true)) {
      event.setQuitMessage((colourMessage(player, event.getQuitMessage())));
    }
  }
  
  public void onPlayerChat(PlayerChatEvent event) {
    final Player player = event.getPlayer();
    final World world = player.getWorld();
    
    if (!plugin.players.get(player.getName()).equals(world.getUID())) {
      plugin.setDisplayName(player);
    }
  }
  
  private String colourMessage(Player player, String message) {
    return message.replace(player.getName(), player.getDisplayName() + ChatColor.YELLOW);
  }
  
}
