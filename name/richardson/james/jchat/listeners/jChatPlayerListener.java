package name.richardson.james.jchat.listeners;

import name.richardson.james.jchat.jChat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
    if (plugin.conf.getBoolean("colourMessages.quit", true)) {
      event.setQuitMessage((colourMessage(player, event.getQuitMessage())));
    }
  }
  
  private String colourMessage(Player player, String message) {
    return message.replace(player.getName(), player.getDisplayName() + ChatColor.YELLOW);
  }
  
}
