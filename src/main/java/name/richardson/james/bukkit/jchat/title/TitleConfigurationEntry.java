/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TitleConfigurationEntry.java is part of jChat.

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

import org.bukkit.configuration.ConfigurationSection;

public class TitleConfigurationEntry implements Comparable<TitleConfigurationEntry> {

	public enum TitleType {
		PREFIX,
		SUFFIX
	}

	public static final String PREFIX_KEY = "prefix";
	public static final String SUFFIX_KEY = "suffix";
	public static final String WEIGHT = "weight";

	private final String name;
	private final String prefix;
	private final String suffix;
	private final int weight;

	public TitleConfigurationEntry(String sectionKey, ConfigurationSection section) {
		name = sectionKey;
		prefix = section.getString(PREFIX_KEY);
		suffix = section.getString(SUFFIX_KEY);
		weight = section.getInt(WEIGHT);
	}

	@Override
	public int compareTo(TitleConfigurationEntry titleConfigurationEntry) {
		if (titleConfigurationEntry.getWeight() > getWeight()) return -1;
		if (titleConfigurationEntry.getWeight() < getWeight()) return 1;
		return 0;
	}

	public String getName() {
		return name;
	}

	public String getPrefix() {
		return (prefix == null) ? "" : prefix;
	}

	public String getSuffix() {
		return (suffix == null) ? "" : suffix;
	}

	public int getWeight() {
		return weight;
	}

	public String getTitle(TitleType titleType) {
		switch (titleType) {
			case PREFIX:
				return getPrefix();
			case SUFFIX:
				return getSuffix();
			default:
				return "";
		}
	}

	@Override
	public String toString() {
		return "TitleConfigurationEntry [" +
		"name:'" + name + '\'' +
		", prefix:'" + prefix + '\'' +
		", suffix:'" + suffix + '\'' +
		", weight:" + weight +
		']';
	}

}
