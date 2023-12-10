package command.input;

import output.AdminOutput;
import output.UserOutput;

public class AdminCommand extends Command{

    private String type;
    private int age;
    private String city;

    private AdminOutput output;

    public AdminCommand() {
        this.output = new AdminOutput();
    }
    public AdminCommand(InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
        this.type = source.getType();
        this.output = new AdminOutput();
        output.setCommand(source.getCommand());
        output.setUser(source.getUsername());
        output.setTimestamp(source.getTimestamp());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public AdminOutput getOutput() {
        return output;
    }

    public void setOutput(AdminOutput output) {
        this.output = output;
    }
    @Override
    public void setOutputMessage(String message) {
        output.setMessage(message);
    }
}
