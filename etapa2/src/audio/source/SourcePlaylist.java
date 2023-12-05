package audio.source;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.input.SongInput;
import lombok.Getter;
import store.data.Playlist;
import java.util.Collections;
import java.util.Random;

public class SourcePlaylist extends SourceAudio {
    private Playlist currentPlaylist;
    private String repeat;
    /**
     * -- GETTER --
     *
     * @return index in playlist of the repeated song
     */
    @Getter
    @JsonIgnore
    private int indexRepeatedSong = -1;
    @JsonIgnore
    private int startRepeatedSong = -1;
    @JsonIgnore
    private Playlist unshuffledPlaylist;
    private boolean isShuffled = false;

    /**
     *
     * @return audio source total played
     */
    @Override
    public int getTotalPlayed() {
        return super.getTotalPlayed();
    }

    /**
     *
     * @return audio source pause status
     */
    @Override
    public boolean isPaused() {
        return super.isPaused();
    }


    public SourcePlaylist() {
        this.repeat = "No Repeat";
    }

    /**
     *
     * @return audio source current playlist
     */
    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    /**
     *
     * @param currentPlaylist the playlist that I want to play
     */
    public void setCurrentPlaylist(final Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    /**
     *
     * @return repeat status of the playing playlist
     */
    @Override
    public String getRepeat() {
        return repeat;
    }

    /**
     *
     * @param repeat repeat status
     */
    @Override
    public void setRepeat(final String repeat) {
        this.repeat = repeat;
    }

    /**
     *
     * @param indexRepeatedSong index in playlist of the repeated song
     */
    public void setIndexRepeatedSong(final int indexRepeatedSong) {
        this.indexRepeatedSong = indexRepeatedSong;
    }

    /**
     *
     * @return the timestamp at which the song started playing
     */
    public int getStartRepeatedSong() {
        return startRepeatedSong;
    }

    /**
     *
     * @param startRepeatedSong the timestamp at which the song started playing
     */
    public void setStartRepeatedSong(final int startRepeatedSong) {
        this.startRepeatedSong = startRepeatedSong;
    }

    /**
     *
     * @return the unshuffled configuration of the current playlist
     */
    public Playlist getUnshuffledPlaylist() {
        return unshuffledPlaylist;
    }

    /**
     *
     * @param unshuffledPlaylist the unshuffled configuration of the current playlist
     */
    public void setUnshuffledPlaylist(final Playlist unshuffledPlaylist) {
        this.unshuffledPlaylist = unshuffledPlaylist;
    }

    /**
     *
     * @return true if the playlist is shuffled, if not, false
     */
    public boolean isShuffled() {
        return isShuffled;
    }

    /**
     *
     * @param shuffled true if the playlist is shuffled, if not, false
     */
    public void setShuffled(final boolean shuffled) {
        isShuffled = shuffled;
    }

    /**
     *
     * @return true if the playlist is shuffled, if not, false
     */
    public boolean getIsShuffle() {
        return isShuffled;
    }

    /**
     *
     * @param shuffle true if the playlist is shuffled, if not, false
     */
    public void setIsShuffle(final boolean shuffle) {
        this.isShuffled = shuffle;
    }

    /**
     *
     * @param timestamp current timestamp
     * @return the song which is playing corresponding to the current timestamp
     */
    public SongInput getSongFromPlaylist(final int timestamp) {
        int totalPlayed = super.totalPlayed;
        if (!isPaused) {
            totalPlayed += timestamp - lastTimestamp;
        }
        for (SongInput currSong : currentPlaylist.getSongList()) {
            totalPlayed -= currSong.getDuration();
            if (totalPlayed < 0) {
                return currSong;
            }
        }
        return null;
    }

    /**
     *
     * @param timestamp current timestamp
     * @return remaining time for the current song from playlist
     */
    public int updatePlaylistSource(final int timestamp) {
        if (!super.isPaused) {
            int lastTimestamp = super.lastTimestamp;
            int addTime;
//            if (super.isPaused == false) {
            addTime = timestamp - lastTimestamp;
//            }
            int remainingTime = currentPlaylist.getDuration() - super.totalPlayed - addTime;
//            int difFromLastTimestamp = timestamp - super.lastTimestamp;
            if (remainingTime > 0) {
//                super.totalPlayed += addTime;
//                return currentSong.getDuration() - super.totalPlayed;
                return remainingTime;
            } else {
                super.isPaused = true;
                super.totalPlayed = currentPlaylist.getDuration();
                return 0;
            }

        }
        return currentPlaylist.getDuration() - super.totalPlayed;
    }

    /**
     *
     * @return current playing song
     */
    public String getCurrentSong() {
        int totalPlayed = this.totalPlayed;
        for (SongInput currSong : currentPlaylist.getSongList()) {
            totalPlayed -= currSong.getDuration();
            if (totalPlayed < 0) {
                return currSong.getName();
            }
        }
        return "";
    }

    /**
     *
     * @param songName name of the currently playing song
     * @return remaining time of the current song
     */
    public int getSongRemainingTime(final String songName) {
        int totalPlayedTime = super.totalPlayed;
        for (SongInput currSong : currentPlaylist.getSongList()) {
            if (currSong.getName().equals(songName)) {
                return currSong.getDuration() - totalPlayedTime;
            }
            totalPlayedTime -= currSong.getDuration();
        }
        return 0;
    }

    /**
     *
     * @return output message of the repeat command
     */
    @Override
    public String changeRepeat() {
        if (this.repeat.equals("No Repeat")) {
            this.repeat = "Repeat All";
            return "Repeat mode changed to repeat all.";
        } else if (this.repeat.equals("Repeat All")) {
            this.repeat = "Repeat Current Song";
            return "Repeat mode changed to repeat current song.";
        } else if (this.repeat.equals("Repeat Current Song")) {
            this.repeat = "No Repeat";
            this.indexRepeatedSong = -1;
            this.startRepeatedSong = -1;
            return "Repeat mode changed to no repeat.";
        }
        return "Problem to changeRepeat in SourceSong";
    }

    /**
     * Stores the start of the repeated song corresponding to total played
     * Stores the index in playlist of the repeated song
     */
    public void storeRepeatedSong() {
        int totalPlayed = this.totalPlayed;
        int startRepeated = 0;
        int indexRepeated = 0;
        for (SongInput currSong : currentPlaylist.getSongList()) {
            totalPlayed -= currSong.getDuration();
            if (totalPlayed < 0) {
                this.startRepeatedSong = startRepeated;
                this.indexRepeatedSong = indexRepeated;
                return;
            }
            startRepeated += currSong.getDuration();
            indexRepeated++;
        }
    }

    /**
     *
     * @return the total played time after shuffle command
     */
    public int getShuffledPlayedTime() {
//        aflu cat timp a trecut din song-ul curent
        int totalPlayedTime = super.totalPlayed;
        SongInput currentSong = null;
        for (SongInput currSong : unshuffledPlaylist.getSongList()) {
            if (currSong.getDuration() > totalPlayedTime) {
                currentSong = currSong;
                break;
            }
            totalPlayedTime -= currSong.getDuration();
        }
        for (SongInput currSong : currentPlaylist.getSongList()) {
            if (currSong.equals(currentSong)) {
                return totalPlayedTime;
            }
            totalPlayedTime += currSong.getDuration();
        }
        return 0;
    }
    /**
     *
     * @return the total played time after unshuffle command
     */
    public int getUnshuffledPlayedTime() {
        int totalPlayedTime = super.totalPlayed;
        SongInput currentSong = null;
        for (SongInput currSong : currentPlaylist.getSongList()) {
            if (currSong.getDuration() > totalPlayedTime) {
                currentSong = currSong;
                break;
            }
            totalPlayedTime -= currSong.getDuration();
        }
        for (SongInput currSong : unshuffledPlaylist.getSongList()) {
            if (currSong.equals(currentSong)) {
                return totalPlayedTime;
            }
            totalPlayedTime += currSong.getDuration();
        }
        return 0;
    }

    /**
     * Does the shuffle command
     * @param seed the seed used to randomise the playlist
     * @return output message of the shuffle command
     */
    public String doShuffle(final int seed) {

        if (isShuffled) {
            super.totalPlayed = getUnshuffledPlayedTime();
            currentPlaylist = unshuffledPlaylist;
            isShuffled = false;
            if (this.repeat.equals("Repeat Current Song")) {
                storeRepeatedSong();
            }
            return "Shuffle function deactivated successfully.";
        } else {
            Random random = new Random(seed);
            this.unshuffledPlaylist = currentPlaylist;
            currentPlaylist = new Playlist(unshuffledPlaylist);
            Collections.shuffle(currentPlaylist.getSongList(), random);
            currentPlaylist.setSongsNameBasedOnSongs();
            super.totalPlayed = getShuffledPlayedTime();
            isShuffled = true;
            if (this.repeat.equals("Repeat Current Song")) {
                storeRepeatedSong();
            }
            return "Shuffle function activated successfully.";
        }
    }

    /**
     *
     * @return the index in playlist of the current song
     */
    public int getCurrentSongIndex() {
        int totalPlayed = this.totalPlayed;
        int index = 0;
        for (SongInput currSong : currentPlaylist.getSongList()) {
            totalPlayed -= currSong.getDuration();
            if (totalPlayed < 0) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     *
     * @return the number of songs in current playlist
     */
    public int getPlaylistSize() {
        return currentPlaylist.getSongList().size();
    }
}
