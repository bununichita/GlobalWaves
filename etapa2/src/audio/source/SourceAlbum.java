package audio.source;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.input.SongInput;
import store.data.Album;
//import store.data.Album;

import java.util.Collections;
import java.util.Random;

public class SourceAlbum extends SourceAudio {
    private Album currentAlbum;
    private String repeat;
    @JsonIgnore
    private int indexRepeatedSong = -1;
    @JsonIgnore
    private int startRepeatedSong = -1;
    @JsonIgnore
    private Album unshuffledAlbum;
    private boolean isShuffled = false;

    public SourceAlbum() {
    }

    public Album getCurrentAlbum() {
        return currentAlbum;
    }

    public void setCurrentAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }

    @Override
    public String getRepeat() {
        return repeat;
    }

    @Override
    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public int getIndexRepeatedSong() {
        return indexRepeatedSong;
    }

    public void setIndexRepeatedSong(int indexRepeatedSong) {
        this.indexRepeatedSong = indexRepeatedSong;
    }

    public int getStartRepeatedSong() {
        return startRepeatedSong;
    }

    public void setStartRepeatedSong(int startRepeatedSong) {
        this.startRepeatedSong = startRepeatedSong;
    }

    public Album getUnshuffledAlbum() {
        return unshuffledAlbum;
    }

    public void setUnshuffledAlbum(Album unshuffledAlbum) {
        this.unshuffledAlbum = unshuffledAlbum;
    }

    public boolean isShuffled() {
        return isShuffled;
    }

    public void setShuffled(boolean shuffled) {
        isShuffled = shuffled;
    }
    public SongInput getSongFromAlbum(final int timestamp) {
        int totalPlayed = super.totalPlayed;
        if (!isPaused) {
            totalPlayed += timestamp - lastTimestamp;
        }
        for (SongInput currSong : currentAlbum.getSongList()) {
            totalPlayed -= currSong.getDuration();
            if (totalPlayed < 0) {
                return currSong;
            }
        }
        return null;
    }
    public int updateAlbumSource(final int timestamp) {
        if (!super.isPaused) {
            int lastTimestamp = super.lastTimestamp;
            int addTime;
            addTime = timestamp - lastTimestamp;
            int remainingTime = currentAlbum.getDuration() - super.totalPlayed - addTime;
            if (remainingTime > 0) {
                return remainingTime;
            } else {
                super.isPaused = true;
                super.totalPlayed = currentAlbum.getDuration();
                return 0;
            }

        }
        return currentAlbum.getDuration() - super.totalPlayed;
    }
    public String getCurrentSong() {
        int totalPlayed = this.totalPlayed;
        for (SongInput currSong : currentAlbum.getSongList()) {
            totalPlayed -= currSong.getDuration();
            if (totalPlayed < 0) {
                return currSong.getName();
            }
        }
        return "";
    }
    public int getSongRemainingTime(final String songName) {
        int totalPlayedTime = super.totalPlayed;
        for (SongInput currSong : currentAlbum.getSongList()) {
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
     * Stores the index in album of the repeated song
     */
    public void storeRepeatedSong() {
        int totalPlayed = this.totalPlayed;
        int startRepeated = 0;
        int indexRepeated = 0;
        for (SongInput currSong : currentAlbum.getSongList()) {
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
        for (SongInput currSong : unshuffledAlbum.getSongList()) {
            if (currSong.getDuration() > totalPlayedTime) {
                currentSong = currSong;
                break;
            }
            totalPlayedTime -= currSong.getDuration();
        }
        for (SongInput currSong : currentAlbum.getSongList()) {
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
        for (SongInput currSong : currentAlbum.getSongList()) {
            if (currSong.getDuration() > totalPlayedTime) {
                currentSong = currSong;
                break;
            }
            totalPlayedTime -= currSong.getDuration();
        }
        for (SongInput currSong : unshuffledAlbum.getSongList()) {
            if (currSong.equals(currentSong)) {
                return totalPlayedTime;
            }
            totalPlayedTime += currSong.getDuration();
        }
        return 0;
    }
    /**
     * Does the shuffle command
     * @param seed the seed used to randomise the Album
     * @return output message of the shuffle command
     */
    public String doShuffle(final int seed) {

        if (isShuffled) {
            super.totalPlayed = getUnshuffledPlayedTime();
            currentAlbum = unshuffledAlbum;
            isShuffled = false;
            if (this.repeat.equals("Repeat Current Song")) {
                storeRepeatedSong();
            }
            return "Shuffle function deactivated successfully.";
        } else {
            Random random = new Random(seed);
            this.unshuffledAlbum = currentAlbum;
            currentAlbum = new Album(unshuffledAlbum);
            Collections.shuffle(currentAlbum.getSongList(), random);
            currentAlbum.setSongsNameBasedOnSongs();
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
     * @return the index in album of the current song
     */
    public int getCurrentSongIndex() {
        int totalPlayed = this.totalPlayed;
        int index = 0;
        for (SongInput currSong : currentAlbum.getSongList()) {
            totalPlayed -= currSong.getDuration();
            if (totalPlayed < 0) {
                return index;
            }
            index++;
        }
        return -1;
    }
    public int getAlbumSize() {
        return currentAlbum.getSongList().size();
    }
    @Override
    public String getOwner() {
        return currentAlbum.getOwner();
    }
    @Override
    public String getName() {
        return currentAlbum.getName();
    }
}
