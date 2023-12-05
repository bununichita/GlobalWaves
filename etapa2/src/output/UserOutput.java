package output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import command.input.Command;

public class UserOutput extends Output {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;
    public UserOutput() {

    }

    public void initUserFromCommand(final Command command) {
        super.command = command.getCommand();
        super.user = command.getUsername();
        super.timestamp = command.getTimestamp();
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
