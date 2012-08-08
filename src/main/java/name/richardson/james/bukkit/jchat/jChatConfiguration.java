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
    this.setDefaultSettings();
    this.setPrefixPermissions();
    this.setSuffixPermissions();
  }

  private void setDefaultSettings() {
    if (!this.getConfiguration().isConfigurationSection("prefix")) {
      this.getConfiguration().createSection("prefix");
      this.getConfiguration().getConfigurationSection("prefix").set("default", "&DARK_BLUE");
    }
    // now check the suffixes
    if (!this.getConfiguration().isConfigurationSection("suffix")) {
      this.getConfiguration().createSection("suffix");
      this.getConfiguration().getConfigurationSection("suffix").set("default", "");
    }
    this.save();
  }

  public Set<String> getPrefixPaths() {
    return Collections.unmodifiableSet(this.prefixPermissions);
  }

  public Set<String> getSuffixPaths() {
    return Collections.unmodifiableSet(this.suffixPermissions);
  }

  public String getTitle(final String path) {
    return this.getConfiguration().getString(path);
  }

  public boolean isColouringDeathMessages() {
    return this.getConfiguration().getBoolean("colour-messages.death");
  }

  public boolean isColouringJoinMessages() {
    return this.getConfiguration().getBoolean("colour-messages.join");
  }

  public boolean isColouringListNames() {
    return this.getConfiguration().getBoolean("colour-messages.list");
  }

  public boolean isColouringQuitMessages() {
    return this.getConfiguration().getBoolean("colour-messages.quit");
  }

  private void setPrefixPermissions() {
    final Set<String> set = new LinkedHashSet<String>();
    for (final String prefix : this.getConfiguration().getConfigurationSection("prefix").getKeys(true)) {
      final String title = "prefix." + prefix;
      set.add(title);
    }
    this.prefixPermissions = set;
  }

  private void setSuffixPermissions() {
    final Set<String> set = new LinkedHashSet<String>();
    for (final String suffix : this.getConfiguration().getConfigurationSection("suffix").getKeys(true)) {
      final String title = "suffix." + suffix;
      set.add(title);
    }
    this.suffixPermissions = set;
  }

}
