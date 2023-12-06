package store.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.input.SongInput;

import java.util.LinkedList;
import java.util.List;

public class Album {
    private String name;
    private int releaseYear;
    private String description;
    private List<SongInput> songList;
    private String owner;
    private List<String> songs;
    public Album() {

    }

    public Album(final Album source) {
        this.name = source.getName();
        this.songs.addAll(source.getSongs());
        this.releaseYear = source.getReleaseYear();
        this.description = source.getDescription();
        this.owner = source.getOwner();
        this.songList.addAll(source.getSongList());
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

    public List<SongInput> getSongList() {
        return songList;
    }

    public void setSongList(List<SongInput> songList) {
        this.songList = songList;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

    /**
     *
     * @return the sum of
     */
    @JsonIgnore
    public int getDuration() {
        int duration = 0;
        for (SongInput currSong : songList) {
            duration += currSong.getDuration();
        }
        return duration;
    }

    /**
     * Populates the song name list
     */
    public void setSongsNameBasedOnSongs() {
        songs.clear();
        for (SongInput currSong : songList) {
            songs.add(currSong.getName());
        }
    }
}
