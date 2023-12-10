package command.input;

public class SelectCommand extends Command {
    private int itemNumber;

    public SelectCommand() {

    }

    /**
     * Constructor from InitCommand
     * @param source InitCommand
     */
    public SelectCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
        this.itemNumber = source.getItemNumber();
    }

    /**
     *
     * @return item number
     */
    public int getItemNumber() {
        return itemNumber;
    }
    /**
     *
     * @return command type
     */
    public String getCommand() {
        return super.command;
    }
    /**
     *
     * @return command username
     */
    public String getUsername() {
        return super.username;
    }
    /**
     *
     * @return command timestamp
     */
    public int getTimestamp() {
        return super.timestamp;
    }

    /**
     *
     * @param itemNumber item number
     */
    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }
}
