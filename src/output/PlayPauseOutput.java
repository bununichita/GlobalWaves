package output;

import command.input.Command;

public class PlayPauseOutput extends Output {
    /**
     * Default constructor
     */
    public PlayPauseOutput() {
    }

    /**
     * Initialize particular type of output from generic command
     * @param command of type Command
     */
    public void initPPFromCommand(final Command command) {
        super.command = command.getCommand();
        super.user = command.getUsername();
        super.timestamp = command.getTimestamp();
    }

}
