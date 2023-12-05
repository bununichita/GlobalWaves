package output;

import com.fasterxml.jackson.annotation.JsonInclude;

public abstract class Output {
    protected String command;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String user;
    protected int timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String message;

    public Output() {
    }

    /**
     *
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     *
     * @param command command type
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     *
     * @return username
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param username Output
     */
    public void setUser(final String username) {
        this.user = username;
    }

    /**
     *
     * @return timestamp Output
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp to set for Output
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return Output message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Constructs the message
     */
    public void doMessage() {

    }
}
