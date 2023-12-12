package audio.source;

import command.input.Command;
import lombok.Getter;

public class SourceAudio {
    /**
     * -- GETTER --
     *
     * @return Returns a string that consists of the audio type of the current source
     */
    @Getter
    protected String audioType;
    protected boolean isPaused;
    /**
     * -- GETTER --
     *
     * @return audioSource timestamp of the last interaction
     */
    @Getter
    protected int lastTimestamp;
    /**
     * -- GETTER --
     *  song: number of seconds played from the song
     *  player: sum of the totalPlayed for every song in playlist
     *  podcast: sum of the totalPlayed for every episode in podcast
     */
    @Getter
    protected int totalPlayed;

    /**
     * Sets the current audio source type to @param
     * @param audioType Audio type to set
     */
    public void setAudioType(final String audioType) {
        this.audioType = audioType;
    }


    /**
     *
     * @return true = audio source is on pause / false = audio source is playing
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Sets the isPaused parameter to @param
     * @param paused true = audio source is on pause / false = audio source is playing
     */
    public void setPaused(final boolean paused) {
        isPaused = paused;
    }

    /**
     *
     * @param lastTimestamp audioSource timestamp of the last interaction
     */
    public void setLastTimestamp(final int lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    /**
     *
     * @param totalPlayed
     * song: number of seconds played from the song
     * player: sum of the totalPlayed for every song in playlist
     * podcast: sum of the totalPlayed for every episode in podcast
     */
    public void setTotalPlayed(final int totalPlayed) {
        this.totalPlayed = totalPlayed;
    }


    /**
     *
     * @return repeat status
     */
    public String getRepeat() {
        return null;
    }

    /**
     *
     * @param repeat repeat status
     */
    public void setRepeat(final String repeat) {
    }

    /**
     * Changes the isPaused status
     * @param command using the timestamp from command to set the totalPlayed
     * @return
     */
    public String changePlayPause(final Command command) {
        if (this.isPaused) {
            isPaused = false;
            return "Playback resumed successfully.";
        } else {
            this.isPaused = true;
            totalPlayed += command.getTimestamp() - lastTimestamp;
            return "Playback paused successfully.";
        }
    }

    /**
     * Changes the repeatStatus
     * @return the output message of the repeat command
     */
    public String changeRepeat() {
        return null;
    }

    /**
     * Updates the totalPlayed of source corresponding to current timestamp
     * @param timestamp current timestamp
     */
    public void updateTotalPlayed(final int timestamp) {
        if (!this.isPaused) {
            totalPlayed += timestamp - lastTimestamp;
        }
    }
    public String getOwner() {
        return "default Return";
    }
    public String getName() {
        return "defaultReturn";
    }
}
