package store.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private List<String> songs = new ArrayList<>();
    private String visibility = "public";
    private int followers = 0;
    @JsonIgnore
    private String owner;
    @JsonIgnore
    private List<SongInput> songList = new ArrayList<>();

    public Playlist() {

    }

    public Playlist(final Playlist source) {
        this.name = source.getName();
        this.songs.addAll(source.getSongs());
        this.visibility = source.getVisibility();
        this.followers = source.getFollowers();
        this.owner = source.getOwner();
        this.songList.addAll(source.getSongList());
    }


    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final List<String> getSongs() {
        return songs;
    }

    public final void setSongs(final List<String> songs) {
        this.songs = songs;
    }

    public final String getVisibility() {
        return visibility;
    }

    public final void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    public final int getFollowers() {
        return followers;
    }

    public final void setFollowers(final int followers) {
        this.followers = followers;
    }

    public final String getOwner() {
        return owner;
    }

    public final void setOwner(final String owner) {
        this.owner = owner;
    }

    public final List<SongInput> getSongList() {
        return songList;
    }

    public final void setSongList(final List<SongInput> songList) {
        this.songList = songList;
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
