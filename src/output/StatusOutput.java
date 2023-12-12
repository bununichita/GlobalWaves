package output;

import command.input.Command;

public class StatusOutput extends Output {
    private Stats stats;
    public StatusOutput() {
    }

    /**
     * Initialize particular type of output from generic command
     * @param command of type Command
     */
    public void initStatusFromCommand(final Command command) {
        super.command = command.getCommand();
        super.user = command.getUsername();
        super.timestamp = command.getTimestamp();
    }

    public final Stats getStats() {
        return stats;
    }

    public final void setStats(final Stats stats) {
        this.stats = stats;
    }
}
