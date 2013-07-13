/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 MessagesConfiguration.java is part of jChat.

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

package name.richardson.james.bukkit.jchat.message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import name.richardson.james.bukkit.utilities.persistence.configuration.AbstractConfiguration;

public class MessagesConfiguration extends AbstractConfiguration {

	public MessagesConfiguration(File file, InputStream defaults)
	throws IOException {
		super(file, defaults, true);
	}

	public boolean isColouringDeathMessages() {
		return this.getConfiguration().getBoolean("death");
	}

	public boolean isColouringJoinMessages() {
		return this.getConfiguration().getBoolean("join");
	}

	public boolean isColouringQuitMessages() {
		return this.getConfiguration().getBoolean("quit");
	}

}
