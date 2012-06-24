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

import name.richardson.james.bukkit.utilities.configuration.PluginConfiguration;

public class jChatConfiguration extends PluginConfiguration {

  private Set<String> prefixPermissions;
  private Set<String> suffixPermissions;

  public jChatConfiguration(final jChat plugin) throws IOException {
    super(plugin);
    this.setPrefixPermissions();
    this.setSuffixPermissions();
  }

  public Set<String> getPrefixPaths() {
    return Collections.unmodifiableSet(this.prefixPermissions);
  }

  public Set<String> getSuffixPaths() {
    return Collections.unmodifiableSet(this.suffixPermissions);
  }

  public String getTitle(final String path) {
    return this.configuration.getString(path);
  }

  public boolean isColouringDeathMessages() {
    return this.configuration.getBoolean("colour-messages.death");
  }

  public boolean isColouringJoinMessages() {
    return this.configuration.getBoolean("colour-messages.join");
  }

  public boolean isColouringListNames() {
    return this.configuration.getBoolean("colour-messages.list");
  }

  public boolean isColouringQuitMessages() {
    return this.configuration.getBoolean("colour-messages.quit");
  }

  public boolean isSupressListNameWarning() {
    return this.configuration.getBoolean("surpress-list-name-too-long-warning");
  }

  public void logValues() {
    this.logger.config(String.format("debugging : %b", this.isDebugging()));
    this.logger.config(String.format("colour-messages.death : %b", this.isColouringDeathMessages()));
    this.logger.config(String.format("colour-messages.join : %b", this.isColouringJoinMessages()));
    this.logger.config(String.format("colour-messages.quit : %b", this.isColouringDeathMessages()));
    this.logger.config(String.format("surpress-list-name-too-long-warning : %b", this.isSupressListNameWarning()));
    for (final String path : this.getPrefixPaths()) {
      this.logger.config(String.format("%s : %s", path, this.getTitle(path)));
    }
    for (final String path : this.getSuffixPaths()) {
      this.logger.config(String.format("%s : %s", path, this.getTitle(path)));
    }
  }

  @Override
  public void setDefaults() throws IOException {
    this.logger.debug(String.format("Apply default configuration."));
    final org.bukkit.configuration.file.YamlConfiguration defaults = this.getDefaults();
    this.configuration.setDefaults(defaults);
    this.configuration.options().copyDefaults(true);
    // check if the default prefix and suffix is there.
    // we do this to avoid reordering the list
    if (!this.configuration.isConfigurationSection("prefix")) {
      this.configuration.createSection("prefix");
      this.configuration.getConfigurationSection("prefix").set("default", "&DARK_BLUE");
    }
    // now check the suffixes
    if (!this.configuration.isConfigurationSection("suffix")) {
      this.configuration.createSection("suffix");
      this.configuration.getConfigurationSection("suffix").set("default", "");
    }
    this.save();
  }

  private void setPrefixPermissions() {
    final Set<String> set = new LinkedHashSet<String>();
    for (final String prefix : this.configuration.getConfigurationSection("prefix").getKeys(true)) {
      final String title = "prefix." + prefix;
      set.add(title);
    }
    this.prefixPermissions = set;
  }

  private void setSuffixPermissions() {
    final Set<String> set = new LinkedHashSet<String>();
    for (final String suffix : this.configuration.getConfigurationSection("suffix").getKeys(true)) {
      final String title = "suffix." + suffix;
      set.add(title);
    }
    this.suffixPermissions = set;
  }

}
