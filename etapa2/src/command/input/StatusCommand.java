package command.input;

public class StatusCommand extends Command {
    public StatusCommand() {
    }
    public StatusCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
    }
}
