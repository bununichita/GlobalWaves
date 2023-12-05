package output;

import command.input.Command;

public class PlayerOutput extends Output {
    public PlayerOutput() {
    }

    /**
     * Initialize particular type of output from generic command
     * @param command source
     */
    public void initPlayerFromCommand(final Command command) {
        super.command = command.getCommand();
        super.user = command.getUsername();
        super.timestamp = command.getTimestamp();
    }
}
