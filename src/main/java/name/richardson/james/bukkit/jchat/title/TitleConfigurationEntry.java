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
		return prefix;
	}

	public String getSuffix() {
		return suffix;
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
		return "TitleConfigurationEntry {" +
		"name='" + name + '\'' +
		", prefix='" + prefix + '\'' +
		", suffix='" + suffix + '\'' +
		", weight=" + weight +
		'}';
	}

}
