package name.richardson.james.bukkit.jchat.title;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.configuration.ConfigurationSection;

public class ScoreboardTitleConfiguration extends TitleConfiguration {

	public ScoreboardTitleConfiguration(File file, InputStream defaults)
	throws IOException {
		super(file, defaults);
	}

	@Override
	public Set<ScoreboardTitleConfigurationEntry> getTitles() {
		final Set<ScoreboardTitleConfigurationEntry> set = new TreeSet<ScoreboardTitleConfigurationEntry>();
		for (final String sectionKey : this.getConfiguration().getKeys(false)) {
			final ConfigurationSection section = this.getConfiguration().getConfigurationSection(sectionKey);
			set.add(new ScoreboardTitleConfigurationEntry(sectionKey, section));
		}
		return set;
	}

}
