package output;

import command.input.Command;
import store.data.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatisticsOutput extends Output {

    private List<String> localResult = new ArrayList<>();
    private final int maxNumber = 5;

    public StatisticsOutput() {
    }

    public final List<String> getResult() {
        return localResult;
    }

    public final void setStatisticsResult(final List<String> result) {
        this.localResult = result;
    }

    /**
     * Initialize particular type of output from generic command
     * @param command of type Command
     */
    public void initStatisticsFromCommand(final Command command) {
       super.command = command.getCommand();
       super.timestamp = command.getTimestamp();
    }

    /**
     * Sets the Statistic result
     */
    public void setPlaylistResult() {
        List<Playlist> allPlaylists = StatisticsData.getInstance().getAllPlaylists();
        List<Playlist> sortedPlaylists = new ArrayList<>();
        sortedPlaylists.addAll(allPlaylists);
        Collections.sort(sortedPlaylists, Comparator.
                comparingInt(Playlist::getFollowers).reversed());
        int index = 0;
        for (Playlist currPlaylist : sortedPlaylists) {
            if (index == maxNumber) {
                break;
            }
            if (currPlaylist.getVisibility().equals("public")) {
                localResult.add(currPlaylist.getName());
                index++;
            }

        }
    }
    /**
     * Sets the Statistic result
     */
    public void setSongResult() {
        List<SongsByLikes> allSongs = StatisticsData.getInstance().getAllSongsByLikes();
        Collections.sort(allSongs, Comparator.comparingInt(SongsByLikes::getLikeCount).reversed());
        int index = 0;
        for (SongsByLikes currSong : allSongs) {
            if (index == maxNumber) {
                break;
            }
            localResult.add(currSong.getSong().getName());
            index++;
        }
    }
    public void setAlbumResult() {
        List<Album> allAlbums = StatisticsData.getInstance().getAllAlbums();
        Collections.sort(allAlbums, Comparator.comparingInt(Album::getTotalLikes).reversed());
        int index = 0;
        for (Album currAlbum : allAlbums) {
            if (index == maxNumber) {
                break;
            }
            localResult.add(currAlbum.getName());
            index++;
        }
    }

    public void setAllPlayersResult() {
        List<StoreUsers> allUsers = StatisticsData.getInstance().getAllUsers();
        for (StoreUsers currUser : allUsers) {
            if (currUser.getNormal() != null) {
                localResult.add(currUser.getUsername());
            }
        }
        for (StoreUsers currUser : allUsers) {
            if (currUser.getArtist() != null) {
                localResult.add(currUser.getUsername());
            }
        }
        for (StoreUsers currUser : allUsers) {
            if (currUser.getHost() != null) {
                localResult.add(currUser.getUsername());
            }
        }
    }

}
