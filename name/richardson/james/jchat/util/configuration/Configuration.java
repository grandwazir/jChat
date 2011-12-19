
package name.richardson.james.jchat.util.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public interface Configuration {

  YamlConfiguration getDefaults() throws IOException;

  File getFile();

  void load();

  void save() throws IOException;

  void setDefaults() throws IOException;

}
