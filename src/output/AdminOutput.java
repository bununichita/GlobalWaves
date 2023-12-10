package output;

import command.input.Command;

public class AdminOutput extends Output {
    private String message;
    public AdminOutput() {
    }
    public void initAdminFromCommand(final Command command) {
        super.command = command.getCommand();
        super.user = command.getUsername();
        super.timestamp = command.getTimestamp();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
