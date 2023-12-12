package command.input;

import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import lombok.Getter;
import output.Output;
import output.UserOutput;

import java.util.List;

public class UserCommand extends Command {

    @Getter
    private String name;
    @Getter
    private int releaseYear;
    @Getter
    private String description;
    @Getter
    private List<SongInput> songs;
    private UserOutput output;
    @Getter
    private String date;
    @Getter
    private int price;
    @Getter
    private List<EpisodeInput> episodes;
    @Getter
    private String nextPage;

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public void setEpisodes(List<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSongs(List<SongInput> songs) {
        this.songs = songs;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public UserCommand(){
        this.output = new UserOutput();
    }
    public UserCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
        this.output = new UserOutput();
        this.date = source.getDate();
        output.setCommand(source.getCommand());
        output.setUser(source.getUsername());
        output.setTimestamp(source.getTimestamp());
        this.songs = source.getSongs();
        this.episodes = source.getEpisodes();
        this.name = source.getName();
        this.description = source.getDescription();
        this.releaseYear = source.getReleaseYear();
        this.price = source.getPrice();
        this.nextPage = source.getNextPage();
    }

    public Output getOutput() {
        return output;
    }

    public void setOutputMessage(String message) {
        output.setMessage(message);
    }

    public void setResult(Object result) {
        output.setResult(result);
    }

}
