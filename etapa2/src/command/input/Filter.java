package command.input;

import java.util.ArrayList;

public class Filter {
    private String name;
    private String album;
    private ArrayList<String> tags = new ArrayList<>();
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private String owner;




    // Getters and setters...
    public Filter() {

    }

    /**
     *
     * @return name filter
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return album filter
     */
    public String getAlbum() {
        return album;
    }

    /**
     *
     * @return tags filter
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     *
     * @return lyrics filter
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     *
     * @return genre filter
     */
    public String getGenre() {
        return genre;
    }
    /**
     *
     * @return release year filter
     */
    public String getReleaseYear() {
        return releaseYear;
    }
    /**
     *
     * @return artist filter
     */
    public String getArtist() {
        return artist;
    }
    /**
     *
     * @return owner filter
     */
    public String getOwner() {
        return owner;
    }
    /**
     *
     * @param name filter
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     *
     * @param album filter
     */
    public void setAlbum(final String album) {
        this.album = album;
    }
    /**
     *
     * @param tags filter
     */
    public void setTags(final ArrayList<String> tags) {
        this.tags = tags;
    }
    /**
     *
     * @param lyrics filter
     */
    public void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }
    /**
     *
     * @param genre filter
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }
    /**
     *
     * @param releaseYear filter
     */
    public void setReleaseYear(final String releaseYear) {
        this.releaseYear = releaseYear;
    }
    /**
     *
     * @param artist filter
     */
    public void setArtist(final String artist) {
        this.artist = artist;
    }
    /**
     *
     * @param owner filter
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }
}
