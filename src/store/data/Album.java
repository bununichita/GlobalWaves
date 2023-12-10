package store.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import command.input.UserCommand;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
public class Album {
    private String name;
    @JsonIgnore
    private int releaseYear;
    @JsonIgnore
    private String description;
    @JsonIgnore
    private List<SongInput> songList;
    @JsonIgnore
    private String owner;
    private List<String> songs;
    @JsonIgnore
    private int totalLikes = 0;
    public Album() {
        this.songList = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public Album(final Album source) {
        this.name = source.getName();
        this.songs = new ArrayList<>();
        this.songs.addAll(source.getSongs());
        this.releaseYear = source.getReleaseYear();
        this.description = source.getDescription();
        this.owner = source.getOwner();
        this.songList = new ArrayList<>();
        this.songList.addAll(source.getSongList());
        this.totalLikes = source.getTotalLikes();
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

    public void setSongList(List<SongInput> songList) {
        this.songList = songList;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

    public void setTotalLikes(final int totalLikes) {
        this.totalLikes = totalLikes;
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

    /**
     * Initialize instance before calling
     * Changes the current Album instance fields
     * @param command current command
     */
    public void initAlbumFromCommand(UserCommand command) {
        this.name = command.getName();
        this.songList = command.getSongs();
        this.releaseYear = command.getReleaseYear();
        this.owner = command.getUsername();
        this.description = command.getDescription();
        for (SongInput currSong : songList) {
            this.songs.add(currSong.getName());
        }
    }
}
