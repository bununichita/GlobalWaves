package audio.source;
import fileio.input.SongInput;

public class SourceSong extends SourceAudio {

    private SongInput currentSong;
    private int startTimestamp;
    private String repeat;

    public SourceSong() {
        this.repeat = "No Repeat";
    }

    /**
     *
     * @return currently playing song
     */
    public SongInput getCurrentSong() {
        return currentSong;
    }

    /**
     *
     * @param currentSong the song that I want to start
     */
    public void setCurrentSong(final SongInput currentSong) {
        this.currentSong = currentSong;
    }

    /**
     *
     * @return the timestamp at which the song was loaded
     */
    public int getStartTimestamp() {
        return startTimestamp;
    }

    /**
     *
     * @param startTimestamp set the timestamp at which the song was loaded
     */
    public void setStartTimestamp(final int startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    /**
     *
     * @return repeat status for current song
     */
    @Override
    public String getRepeat() {
        return this.repeat;
    }

    /**
     * Update the repeat status of the current song
     * @param repeat repeat status
     */
    @Override
    public void setRepeat(final String repeat) {
        this.repeat = repeat;
    }

    /**
     * Updates the audio source corresponding to timestamp
     * @param timestamp current timestamp
     * @return remaining time of current song
     */
    public int updateSongSource(final int timestamp) {

        if (!super.isPaused) {
            int lastTimestamp = super.lastTimestamp;
            int addTime;
            addTime = timestamp - lastTimestamp;
            int remainingTime = currentSong.getDuration() - super.totalPlayed - addTime;
            if (remainingTime > 0) {
                return remainingTime;
            } else {
                super.isPaused = true;
                super.totalPlayed = currentSong.getDuration();
                return 0;
            }

        }
        return currentSong.getDuration() - super.totalPlayed;
    }

    /**
     * Change the repeat status of the current song
     * @return the output message for the repeat command
     */
    @Override
    public String changeRepeat() {
        if (this.repeat.equals("No Repeat")) {
            this.repeat = "Repeat Once";
            return "Repeat mode changed to repeat once.";
        } else if (this.repeat.equals("Repeat Once")) {
            this.repeat = "Repeat Infinite";
            return "Repeat mode changed to repeat infinite.";
        } else if (this.repeat.equals("Repeat Infinite")) {
            this.repeat = "No Repeat";
            return "Repeat mode changed to no repeat.";
        }
        return "Problem to changeRepeat in SourceSong";
    }
    @Override
    public String getOwner() {
        return currentSong.getArtist();
    }
    @Override
    public String getName() {
        return currentSong.getName();
    }
}
