package store.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StatisticsData {
    private static StatisticsData instance;
    @Getter
    private List<Playlist> allPlaylists;
    @Getter
    private List<Album> allAlbums;
    @Getter
    private List<StoreUsers> allUsers;
    @Getter
    private List<SongsByLikes> allSongsByLikes;

    private StatisticsData() {
        allPlaylists = new ArrayList<>();
        allAlbums = new ArrayList<>();
        allUsers = new ArrayList<>();
        allSongsByLikes = new ArrayList<>();
    }
    public static StatisticsData getInstance() {
        if (instance == null) {
            instance = new StatisticsData();
        }
        return instance;
    }

    public void setAllPlaylists(final List<Playlist> allPlaylists) {
        this.allPlaylists = allPlaylists;
    }

    public void setAllAlbums(final List<Album> allAlbums) {
        this.allAlbums = allAlbums;
    }

    public void setAllUsers(final List<StoreUsers> allUsers) {
        this.allUsers = allUsers;
    }

    public void setAllSongsByLikes(final List<SongsByLikes> allSongsByLikes) {
        this.allSongsByLikes = allSongsByLikes;
    }
}
