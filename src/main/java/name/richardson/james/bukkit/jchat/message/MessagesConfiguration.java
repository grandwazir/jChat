package name.richardson.james.bukkit.jchat.message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;

public class MessagesConfiguration extends YAMLStorage {

	public MessagesConfiguration(File file, InputStream defaults)
	throws IOException {
		super(file, defaults);
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
