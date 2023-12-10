package command.input;

public class LoadCommand extends Command {

    public LoadCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
    }

}
