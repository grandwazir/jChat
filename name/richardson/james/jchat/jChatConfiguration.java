
package name.richardson.james.jchat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import name.richardson.james.jchat.util.Configuration;
import name.richardson.james.jchat.util.Logger;

public class jChatConfiguration extends Configuration {

  protected final static Logger logger = new Logger(jChatConfiguration.class);
  protected final static String fileName = "config.yml";

  protected final InputStream defaults = jChat.getInstance().getResource(fileName);
  private Set<String> prefixPermissions;
  private Set<String> suffixPermissions;

  public jChatConfiguration() throws IOException {
    super();
    this.setPrefixPermissions();
    this.setSuffixPermissions();
  }

  public static jChatConfiguration getInstance() {
    return (jChatConfiguration) instance;
  }

  public boolean isDebugging() {
    return configuration.getBoolean("debugging");
  }
  
  public String getTitle(String path) {
    return configuration.getString(path);
  }
  
  private void setPrefixPermissions() {
    Set<String> set = new HashSet<String>();
    for (String prefix : configuration.getConfigurationSection("prefix").getKeys(true)) {
      String title =  "prefix." + prefix;
      set.add(title);
    }
    prefixPermissions = set;
  }
  
  private void setSuffixPermissions() {
    Set<String> set = new HashSet<String>();
    for (String suffix : configuration.getConfigurationSection("suffix").getKeys(true)) {
      String title = "suffix." + suffix;
      set.add(title);
    }
    suffixPermissions = set;
  } 

  public void logValues() {
    logger.config(String.format("debugging : %b", this.isDebugging()));
    logger.config(String.format("colour-messages.death : %b", this.isColouringDeathMessages()));
    logger.config(String.format("colour-messages.join : %b", this.isColouringJoinMessages()));
    logger.config(String.format("colour-messages.quit : %b", this.isColouringDeathMessages()));
    for (String path : getPrefixPaths()) {
      logger.config(String.format("%s : %s", path, this.getTitle(path)));
    }
    for (String path : getSuffixPaths()) {
      logger.config(String.format("%s : %s", path, this.getTitle(path)));
    }
  }
  
  public boolean isColouringDeathMessages() {
    return configuration.getBoolean("colour-messages.death");
  }
  
  public boolean isColouringJoinMessages() {
    return configuration.getBoolean("colour-messages.join");
  }
  
  public boolean isColouringQuitMessages() {
    return configuration.getBoolean("colour-messages.quit");
  }

  public Set<String> getSuffixPaths() {
    return Collections.unmodifiableSet(suffixPermissions);
  }

  public Set<String> getPrefixPaths() {
    return Collections.unmodifiableSet(prefixPermissions);
  }
  
}
