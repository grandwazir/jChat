package name.richardson.james.jchat.messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EntityListener extends org.bukkit.event.entity.EntityListener {
  
  public void onEntityDeath(EntityDeathEvent event) {
    if (event instanceof PlayerDeathEvent) {
      PlayerDeathEvent e = (PlayerDeathEvent) event;
      final Player player = (Player) e.getEntity();
      e.setDeathMessage((colourMessage(player, e.getDeathMessage())));
    }
  }
  
  private String colourMessage(Player player, String message) {
    return message.replace(player.getName(), player.getDisplayName() + ChatColor.RED);
  }
  
}
