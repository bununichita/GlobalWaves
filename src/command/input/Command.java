package command.input;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import output.Output;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "command")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SearchCommand.class, name = "search"),
        @JsonSubTypes.Type(value = SelectCommand.class, name = "select")
})

public abstract class Command {
    protected String command;
    protected String username;
    protected int timestamp;

    public Command() {
    }

//    public Command(InitCommand source) {
//
//    }

    /**
     *
     * @return command name
     */
    public String getCommand() {
        return command;
    }

    /**
     *
     * @param command current command
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     *
     * @return user that initialized command
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username user that initialized command
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     *
     * @return timestamp of the command
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp timestamp of the command
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public Output getOutput() {
        return null;
    }
    public void setOutputMessage(String message) {

    }
    public void setResult(Object result) {

    }
}
