
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
