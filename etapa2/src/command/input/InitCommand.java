package command.input;

import fileio.input.EpisodeInput;
import fileio.input.SongInput;

import java.util.List;

public class InitCommand {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private int itemNumber;

    private String playlistName;

    private int playlistId;

    private int seed;

    private Filter filters;

    private int age;

    private String city;

    private String name;

    private int releaseYear;

    private String description;

    private List<SongInput> songs;

    private String date;

    private int price;

    private List<EpisodeInput> episodes;

    private String nextPage;

    public InitCommand() {

    }

    /**
     *
     * @return command parameter
     */
    public String getCommand() {
        return command;
    }
    /**
     *
     * @return username parameter
     */
    public String getUsername() {
        return username;
    }
    /**
     *
     * @return timestamp parameter
     */
    public int getTimestamp() {
        return timestamp;
    }
    /**
     *
     * @return type parameter
     */
    public String getType() {
        return type;
    }
    /**
     *
     * @return itemNumber parameter
     */
    public int getItemNumber() {
        return itemNumber;
    }
    /**
     *
     * @return playlistName parameter
     */
    public String getPlaylistName() {
        return playlistName;
    }
    /**
     *
     * @return playlistId parameter
     */
    public int getPlaylistId() {
        return playlistId;
    }
    /**
     *
     * @return seed parameter
     */
    public  int getSeed() {
        return seed;
    }
    /**
     *
     * @return filters
     */
    public Filter getFilters() {
        return filters;
    }

    /**
     *
     * @param command command filter
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     *
     * @param username username filter
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     *
     * @param timestamp timestamp filter
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @param type type filter
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     *
     * @param itemNumber itemNumber filter
     */
    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }

    /**
     *
     * @param playlistName playlistName filter
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /**
     *
     * @param playlistId playlistId filter
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }

    /**
     *
     * @param seed seed filter
     */
    public void setSeed(final int seed) {
        this.seed = seed;
    }

    /**
     *
     * @param filters the filters I want to set
     */
    public void setFilters(final Filter filters) {
        this.filters = filters;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<EpisodeInput> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }
}
