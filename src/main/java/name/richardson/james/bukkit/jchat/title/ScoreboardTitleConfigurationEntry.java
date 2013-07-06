package name.richardson.james.bukkit.jchat.title;

import org.bukkit.configuration.ConfigurationSection;

public class ScoreboardTitleConfigurationEntry extends TitleConfigurationEntry {

	public static final String SCOREBOARD_PREFIX = "scoreboard.";
	public static final String DISPLAY_NAME_KEY = SCOREBOARD_PREFIX + "display-name";
	public static final String APPEND_TEAM_KEY = SCOREBOARD_PREFIX + "append-team-name";
	public static final String FRIENDLY_FIRE_KEY = SCOREBOARD_PREFIX + "friendly-fire";
	public static final String SEE_FRIENDLY_INVISIBLES_KEY = SCOREBOARD_PREFIX + "see-invisibles";

	private final boolean appendTeamName;
	private final String displayName;
	private final boolean friendlyFire;
	private final boolean friendlyInvisibles;

	public ScoreboardTitleConfigurationEntry(String sectionKey, ConfigurationSection section) {
		super(sectionKey, section);
		displayName = section.getString(DISPLAY_NAME_KEY);
		appendTeamName = section.getBoolean(APPEND_TEAM_KEY);
		friendlyFire = section.getBoolean(FRIENDLY_FIRE_KEY);
		friendlyInvisibles = section.getBoolean(SEE_FRIENDLY_INVISIBLES_KEY);
	}

	public boolean canHurtFriendlies() {
		return friendlyFire;
	}

	public boolean canSeeFriendlyInvisibles() {
		return friendlyInvisibles;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean isAppendingTeamName() {
		return appendTeamName;
	}

	@Override
	public String toString() {
		return "ScoreboardTitleConfigurationEntry{" +
		"appendTeamName=" + appendTeamName +
		", displayName='" + displayName + '\'' +
		", friendlyFire=" + friendlyFire +
		", friendlyInvisibles=" + friendlyInvisibles +
		'}';
	}
}
