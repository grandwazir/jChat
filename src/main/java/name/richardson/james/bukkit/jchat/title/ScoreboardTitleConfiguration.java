package name.richardson.james.bukkit.jchat.title;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.utilities.logging.PrefixedLogger;

public class ScoreboardTitleConfiguration extends TitleConfiguration {

	public static final Logger LOGGER = PrefixedLogger.getLogger(ScoreboardTitleConfiguration.class);

	public ScoreboardTitleConfiguration(File file, InputStream defaults)
	throws IOException {
		super(file, defaults);
	}

	@Override
	public Set<ScoreboardTitleConfigurationEntry> getTitles() {
		final Set<ScoreboardTitleConfigurationEntry> set = new TreeSet<ScoreboardTitleConfigurationEntry>();
		for (final String sectionKey : this.getConfiguration().getKeys(false)) {
			final ConfigurationSection section = this.getConfiguration().getConfigurationSection(sectionKey);
			ScoreboardTitleConfigurationEntry entry = new ScoreboardTitleConfigurationEntry(sectionKey, section);
			set.add(entry);
			LOGGER.log(Level.CONFIG, entry.toString());
		}
		return set;
	}

}
