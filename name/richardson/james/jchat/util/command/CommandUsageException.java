/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * CommandUsageException.java is part of jChat.
 * 
 * jChat is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * jChat is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * jChat. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.jchat.util.command;

public class CommandUsageException extends Exception {

  private static final long serialVersionUID = 8443259919961526754L;

  private final String message;
  private final String usage;

  public CommandUsageException(String message, String usage) {
    this.message = message;
    this.usage = usage;
  }

  public String getMessage() {
    return message;
  }

  public String getUsage() {
    return usage;
  }

}
