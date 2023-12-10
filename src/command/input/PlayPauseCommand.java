package command.input;

public class PlayPauseCommand extends Command {

    public PlayPauseCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
    }

}
