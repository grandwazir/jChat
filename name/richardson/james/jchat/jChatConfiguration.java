/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * jChatConfiguration.java is part of jChat.
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

package name.richardson.james.jchat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import name.richardson.james.jchat.util.Configuration;
import name.richardson.james.jchat.util.Logger;

public class jChatConfiguration extends Configuration {

  protected final static String fileName = "config.yml";
  protected final static Logger logger = new Logger(jChatConfiguration.class);

  private Set<String> prefixPermissions;
  private Set<String> suffixPermissions;

  protected final InputStream defaults = jChat.getInstance().getResource(fileName);

  public jChatConfiguration() throws IOException {
    super();
    this.setPrefixPermissions();
    this.setSuffixPermissions();
  }

  public static jChatConfiguration getInstance() {
    return (jChatConfiguration) instance;
  }

  public Set<String> getPrefixPaths() {
    return Collections.unmodifiableSet(prefixPermissions);
  }

  public Set<String> getSuffixPaths() {
    return Collections.unmodifiableSet(suffixPermissions);
  }

  public String getTitle(String path) {
    return configuration.getString(path);
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

  public boolean isDebugging() {
    return configuration.getBoolean("debugging");
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

  private void setPrefixPermissions() {
    Set<String> set = new LinkedHashSet<String>();
    for (String prefix : configuration.getConfigurationSection("prefix").getKeys(true)) {
      String title = "prefix." + prefix;
      set.add(title);
    }
    prefixPermissions = set;
  }

  private void setSuffixPermissions() {
    Set<String> set = new LinkedHashSet<String>();
    for (String suffix : configuration.getConfigurationSection("suffix").getKeys(true)) {
      String title = "suffix." + suffix;
      set.add(title);
    }
    suffixPermissions = set;
  }

}
