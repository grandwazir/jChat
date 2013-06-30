package name.richardson.james.bukkit.jchat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import name.richardson.james.bukkit.utilities.persistence.configuration.SimplePluginConfiguration;

public class PluginConfiguration extends SimplePluginConfiguration {

	public PluginConfiguration(File file, InputStream defaults)
	throws IOException {
		super(file, defaults);
	}

	public boolean isScoreboardEnabled() {
		return getConfiguration().getBoolean("scoreboard-enabled");
	}

}
