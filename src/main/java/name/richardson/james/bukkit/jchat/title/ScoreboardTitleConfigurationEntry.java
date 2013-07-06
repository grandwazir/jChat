/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ScoreboardTitleConfigurationEntry.java is part of jChat.

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
		return (displayName == null) ? getName() : displayName;
	}

	public boolean isAppendingTeamName() {
		return appendTeamName;
	}

	@Override
	public String toString() {
		return super.toString() + " ScoreboardTitleConfigurationEntry [" +
		"appendTeamName:" + appendTeamName +
		", displayName:'" + displayName + '\'' +
		", friendlyFire:" + friendlyFire +
		", friendlyInvisibles:" + friendlyInvisibles +
		']';
	}
}
