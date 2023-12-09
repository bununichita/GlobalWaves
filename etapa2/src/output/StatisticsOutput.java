package output;

import command.input.Command;
import store.data.Playlist;
import store.data.SongsByLikes;
import store.data.StoreUsers;

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
     * @param allPlaylists global playlist list
     */
    public void setPlaylistResult(final List<Playlist> allPlaylists) {
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
     * @param allSongs global playlist list
     */
    public void setSongResult(final List<SongsByLikes> allSongs) {
//        List<SongsByLikes> sortedSongs = new ArrayList<>();
//        sortedSongs.addAll(allSongs);
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

    public void setAllPlayersResult(final List<StoreUsers> allUsers) {
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
