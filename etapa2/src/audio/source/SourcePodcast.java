package audio.source;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;

public class SourcePodcast extends SourceAudio {
    private PodcastInput currentPodcast;
    private String repeat;

    public SourcePodcast() {
        this.repeat = "No Repeat";
    }

    /**
     *
     * @return the currently playing podcast
     */
    public PodcastInput getCurrentPodcast() {
        return currentPodcast;
    }

    /**
     *
     * @param currentPodcast the podcast that I want to start
     */
    public void setCurrentPodcast(final PodcastInput currentPodcast) {
        this.currentPodcast = currentPodcast;
    }

    /**
     *
     * @return repeat status
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
     * @return the number of episodes in current podcast
     */
    public int getPodcastSize() {
        return currentPodcast.getEpisodes().size();
    }

    /**
     *
     * @return the index in podcast of the currently playing episode
     */
    public int getCurrentEpisodeIndex() {
        int totalPlayed = this.totalPlayed;
        int index = 0;
        for (EpisodeInput currEpisode : currentPodcast.getEpisodes()) {
            totalPlayed -= currEpisode.getDuration();
            if (totalPlayed < 0) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     *
     * @return the name of the currently playing episode
     */
    public String getCurrentEpisode() {
        int totalPlayed = this.totalPlayed;
        for (EpisodeInput currEpisode : currentPodcast.getEpisodes()) {
            totalPlayed -= currEpisode.getDuration();
            if (totalPlayed < 0) {
                return currEpisode.getName();
            }
        }
        return "";
    }

    /**
     *
     * @param timestamp current timestamp
     * @return remaining time of the current episode
     */
    public int updatePodcastSource(final int timestamp) {
        if (!super.isPaused) {
            int lastTimestamp = super.lastTimestamp;
            int addTime;
//            if (super.isPaused == false) {
            addTime = timestamp - lastTimestamp;
//            }
            int remainingTime = currentPodcast.getDuration() - super.totalPlayed - addTime;
//            int difFromLastTimestamp = timestamp - super.lastTimestamp;
            if (remainingTime > 0) {
//                super.totalPlayed += addTime;
//                return currentSong.getDuration() - super.totalPlayed;
                return remainingTime;
            } else {
                super.isPaused = true;
                super.totalPlayed = currentPodcast.getDuration();
                return 0;
            }

        }
        return currentPodcast.getDuration() - super.totalPlayed;
    }

    /**
     *
     * @param totalPlayed time played from the start of the podcast
     * @return the name of the episode corresponding to totalPlayed
     */
    public String getCurrentEpisode(final int totalPlayed) {
        int newTotalPlayed = totalPlayed;
        for (EpisodeInput currEpisode : currentPodcast.getEpisodes()) {
            newTotalPlayed -= currEpisode.getDuration();
            if (newTotalPlayed < 0) {
                return currEpisode.getName();
            }
        }
        return "";
    }

    /**
     *
     * @param episodeName name of the current episode
     * @return the remaining time of the current episode
     */
    public int getEpisodeRemainingTime(final String episodeName) {
        int totalPlayedTime = super.totalPlayed;
        for (EpisodeInput currEpisode : currentPodcast.getEpisodes()) {
            if (currEpisode.getName().equals(episodeName)) {
                return currEpisode.getDuration() - totalPlayedTime;
            }
            totalPlayedTime -= currEpisode.getDuration();
        }
        return 0;
    }

    /**
     * Change the repeat status of the podcast
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
            this.repeat = "No repeat";
            return "Repeat mode changed to no repeat.";
        }
        return "Problem to changeRepeat in SourceSong";
    }

    /**
     *
     * @return the played time of the current episode
     */
    public int getCurrentEpisodePlayedTime() {
        int episodePlayedTime = totalPlayed;
        for (EpisodeInput currEpisode : currentPodcast.getEpisodes()) {
            if (episodePlayedTime < currEpisode.getDuration()) {
                return episodePlayedTime;
            }
            episodePlayedTime -= currEpisode.getDuration();
        }
        return -1;
    }

    /**
     *
     * @return the remaining time of the current episode
     */
    public int getEpisodeRemainingTime() {
        int playedTime = totalPlayed;
        for (EpisodeInput currEpisode : currentPodcast.getEpisodes()) {
            playedTime -= currEpisode.getDuration();
            if (playedTime < 0) {
                return -playedTime;
            }
        }
        return -1;
    }
    @Override
    public String getOwner() {
        return currentPodcast.getOwner();
    }
    @Override
    public String getName() {
        return currentPodcast.getName();
    }
}
