package command.input;

import output.Output;
import output.UserOutput;

public class UserCommand extends Command {


    UserOutput output;



    public UserCommand(){
        this.output = new UserOutput();
    }
    public UserCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
        this.output = new UserOutput();
        output.setCommand(source.getCommand());
        output.setUser(source.getUsername());
        output.setTimestamp(source.getTimestamp());
    }

    public Output getOutput() {
        return output;
    }

//    public void setOutput() {
//        this.output = (UserOutput) outputInstance;
//    }

    public void setOutputMessage(String message) {
        output.setMessage(message);
    }

    public void setResult(Object result) {
        output.setResult(result);
    }

}
