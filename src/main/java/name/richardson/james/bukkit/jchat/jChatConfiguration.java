/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
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
package name.richardson.james.bukkit.jchat;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import name.richardson.james.bukkit.utilities.configuration.AbstractConfiguration;

public class jChatConfiguration extends AbstractConfiguration {

  public final static String FILE_NAME = "config.yml";

  private Set<String> prefixPermissions;
  private Set<String> suffixPermissions;

  public jChatConfiguration(jChat plugin) throws IOException {
    super(plugin, FILE_NAME);
    this.setPrefixPermissions();
    this.setSuffixPermissions();
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

  public boolean isColouringListNames() {
    return configuration.getBoolean("colour-messages.list");
  }

  public boolean isColouringQuitMessages() {
    return configuration.getBoolean("colour-messages.quit");
  }

  public boolean isDebugging() {
    return configuration.getBoolean("debugging");
  }

  public boolean isSupressListNameWarning() {
    return configuration.getBoolean("surpress-list-name-too-long-warning");
  }

  public boolean isCheckingForUpdates() {
    return configuration.getBoolean("automatic-updates.enabled");
  }

  public boolean isAutomaticallyUpdating() {
    if (configuration.getString("automatic-updates.action").equalsIgnoreCase("install")) return true;
    return false;
  }

  public void logValues() {
    logger.config(String.format("debugging : %b", this.isDebugging()));
    logger.config(String.format("colour-messages.death : %b", this.isColouringDeathMessages()));
    logger.config(String.format("colour-messages.join : %b", this.isColouringJoinMessages()));
    logger.config(String.format("colour-messages.quit : %b", this.isColouringDeathMessages()));
    logger.config(String.format("surpress-list-name-too-long-warning : %b", this.isSupressListNameWarning()));
    for (String path : getPrefixPaths()) {
      logger.config(String.format("%s : %s", path, this.getTitle(path)));
    }
    for (String path : getSuffixPaths()) {
      logger.config(String.format("%s : %s", path, this.getTitle(path)));
    }
  }

  @Override
  public void setDefaults() throws IOException {
    logger.debug(String.format("Apply default configuration."));
    final org.bukkit.configuration.file.YamlConfiguration defaults = this.getDefaults();
    configuration.setDefaults(defaults);
    configuration.options().copyDefaults(true);
    // check if the default prefix and suffix is there.
    // we do this to avoid reordering the list
    if (!configuration.isConfigurationSection("prefix")) {
      configuration.createSection("prefix");
      configuration.getConfigurationSection("prefix").set("default", "&DARK_BLUE");
    }
    // now check the suffixes
    if (!configuration.isConfigurationSection("suffix")) {
      configuration.createSection("suffix");
      configuration.getConfigurationSection("suffix").set("default", "");
    }
    this.save();
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
