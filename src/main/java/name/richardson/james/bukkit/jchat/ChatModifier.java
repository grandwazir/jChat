package name.richardson.james.bukkit.jchat;

import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import name.richardson.james.bukkit.utilities.internals.Logger;

public class ChatModifier implements Callable<Object> {

  public enum Type {
    PREFIX,
    SUFFIX
  }

  private static final Logger logger = new Logger(ChatModifier.class);

  private static final jChat plugin = ChatModifier.getPlugin();

  private static final jChat getPlugin() {
    return (jChat) Bukkit.getServer().getPluginManager().getPlugin("jChat");
  }

  private final Player player;

  private final Type type;

  public ChatModifier(final Player player, final Type type) {
    if (ChatModifier.plugin == null) {
      ChatModifier.getPlugin();
    }
    this.player = player;
    this.type = type;
  }

  public String call() {
    return this.calcuateTitle();
  }

  private String calcuateTitle() {
    String title = ChatModifier.plugin.getTitle(this.player, this.type);
    if (this.type.equals(Type.SUFFIX)) title = title + ChatColor.WHITE;
    ChatModifier.logger.debug(String.format("Using title: %s", title));
    return title;
  }

}
