/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * AbstractConfiguration.java is part of jChat.
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

package name.richardson.james.jchat.util.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import name.richardson.james.jchat.jChat;
import name.richardson.james.jchat.util.Logger;

public abstract class AbstractConfiguration implements Configuration {

  protected static final Logger logger = new Logger(AbstractConfiguration.class);

  protected org.bukkit.configuration.file.YamlConfiguration configuration;

  public AbstractConfiguration() throws IOException {
    this.load();
    this.setDefaults();
  }

  public org.bukkit.configuration.file.YamlConfiguration getDefaults() throws IOException {
    final InputStream resource = jChat.getInstance().getResource(this.getFile().getName());
    final org.bukkit.configuration.file.YamlConfiguration defaults = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(resource);
    resource.close();
    return defaults;
  }

  public void load() {
    final File file = this.getFile();
    logger.debug(String.format("Loading configuration: %s.", file.getName()));
    logger.debug(String.format("Using path: %s.", file.getPath()));
    configuration = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
  }

  public void save() throws IOException {
    logger.debug(String.format("Saving configuration: %s.", this.getFile().getName()));
    configuration.save(this.getFile());
  }

  public void setDefaults() throws IOException {
    logger.debug(String.format("Apply default configuration."));
    final org.bukkit.configuration.file.YamlConfiguration defaults = this.getDefaults();
    configuration.setDefaults(defaults);
    configuration.options().copyDefaults(true);
    this.save();
  }

}
