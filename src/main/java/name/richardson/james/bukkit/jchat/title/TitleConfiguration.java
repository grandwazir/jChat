package name.richardson.james.bukkit.jchat.title;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;

public class TitleConfiguration extends YAMLStorage {

	public TitleConfiguration(File file, InputStream defaults)
	throws IOException {
		super(file, defaults);
	}

	public Set<? extends TitleConfigurationEntry> getTitles() {
		final Set<TitleConfigurationEntry> set = new TreeSet<TitleConfigurationEntry>();
		for (final String sectionKey : this.getConfiguration().getKeys(false)) {
			final ConfigurationSection section = this.getConfiguration().getConfigurationSection(sectionKey);
			set.add(new TitleConfigurationEntry(sectionKey, section));
		}
		return set;
	}

}
