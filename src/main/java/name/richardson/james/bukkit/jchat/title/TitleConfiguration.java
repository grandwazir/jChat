/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TitleConfiguration.java is part of jChat.

 jChat is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 jChat is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 jChat. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

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
import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;

public class TitleConfiguration extends YAMLStorage {

	public static Logger LOGGER = PrefixedLogger.getLogger(TitleConfiguration.class);

	public TitleConfiguration(File file, InputStream defaults)
	throws IOException {
		super(file, defaults);
	}

	public Set<? extends TitleConfigurationEntry> getTitles() {
		final Set<TitleConfigurationEntry> set = new TreeSet<TitleConfigurationEntry>();
		for (final String sectionKey : this.getConfiguration().getKeys(false)) {
			final ConfigurationSection section = this.getConfiguration().getConfigurationSection(sectionKey);
			TitleConfigurationEntry entry = new TitleConfigurationEntry(sectionKey, section);
			set.add(entry);
			LOGGER.log(Level.CONFIG, entry.toString());
		}
		return set;
	}

}
