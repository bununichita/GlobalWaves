package command.input;

public class StatisticsCommand extends Command {
    public StatisticsCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.timestamp = source.getTimestamp();
    }
}
