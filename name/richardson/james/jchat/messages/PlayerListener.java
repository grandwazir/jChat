package name.richardson.james.jchat.messages;

import name.richardson.james.jchat.jChatHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerListener extends org.bukkit.event.player.PlayerListener {
  
  private final static jChatHandler handler = new jChatHandler(PlayerListener.class);
  
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    handler.setPlayerDisplayName(event.getPlayer());
    event.setJoinMessage((colourMessage(player, event.getJoinMessage())));
  }
  
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    event.setQuitMessage((colourMessage(player, event.getQuitMessage())));
  }
  
  public void PlayerChangedWorld(PlayerChangedWorldEvent event) {
    final Player player = event.getPlayer();
    handler.setPlayerDisplayName(player);
  }
  
  public void onPlayerDeath(PlayerDeathEvent event) {
    final Player player = (Player) event.getEntity();
    event.setDeathMessage((colourMessage(player, event.getDeathMessage())));
  }
  
  private String colourMessage(Player player, String message) {
    return message.replace(player.getName(), player.getDisplayName() + ChatColor.YELLOW);
  }
  
}
