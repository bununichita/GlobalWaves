package command.input;

public class SearchCommand extends Command {
    private String type;
//    private Filter filters;

    // Getters and setters...

    private Filter filters;

    /**
     * Constructor from InitCommand
     * @param source of type InitCommand
     */
    public SearchCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
        this.type = source.getType();
        this.filters = source.getFilters();
    }

    /**
     *
     * @return command search type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type command search type
     */
    public void setType(final String type) {
        this.type = type;
    }
    /**
     *
     * @return command filters
     */
    public Filter getFilters() {
        return filters;
    }

    /**
     *
     * @param filters filters to set
     */
    public void setFilters(final Filter filters) {
        this.filters = filters;
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

    public SearchCommand(final String type, final Filter filters) {
        this.type = type;
        this.filters = filters;
    }
}
