package command.input;

import fileio.input.SongInput;
import output.Output;
import output.UserOutput;

import java.util.List;

public class UserCommand extends Command {

    private String name;
    private int releaseYear;
    private String description;
    private List<SongInput> songs;
    private UserOutput output;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(List<SongInput> songs) {
        this.songs = songs;
    }

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
        this.songs = source.getSongs();
        this.name = source.getName();
        this.description = source.getDescription();
        this.releaseYear = source.getReleaseYear();
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
