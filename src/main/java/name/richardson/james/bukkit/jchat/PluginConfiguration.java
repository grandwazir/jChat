/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 PluginConfiguration.java is part of jChat.

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
