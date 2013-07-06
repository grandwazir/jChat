/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 ReloadCommandTest.java is part of jChat.

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

import org.bukkit.plugin.PluginManager;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

@RunWith(MockitoJUnitRunner.class)
public class ReloadCommandTest extends TestCase {

	private ReloadCommand command;

	@Mock
	private jChat plugin;

	@Mock
	private PermissionManager permissionManager;

	@Test
	public void setUp()
	throws Exception {
		command = new ReloadCommand(permissionManager, plugin);
	}
}
