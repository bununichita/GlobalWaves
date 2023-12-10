package output;

import command.input.LoadCommand;

public class LoadOutput extends Output {
    public LoadOutput() {
    }

    /**
     * Initializer from LoadCommand
     * @param command LoadCommand
     */
    public void initLoadFromCommand(final LoadCommand command) {
        super.command = command.getCommand();
        super.user = command.getUsername();
        super.timestamp = command.getTimestamp();
    }
}
