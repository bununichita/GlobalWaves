package fileio.input;

import java.util.ArrayList;

public final class PodcastInput {
    private String name;
    private String owner;
    private ArrayList<EpisodeInput> episodes;
    private int listenedTime = 0;

    public PodcastInput() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    public int getListenedTime() {
        return listenedTime;
    }

    public void setListenedTime(final int listenedTime) {
        this.listenedTime = listenedTime;
    }

    /**
     *
     * @return sum of every episode duration
     */
    public int getDuration() {
        int duration = 0;
        for (EpisodeInput currEpisode : episodes) {
            duration += currEpisode.getDuration();
        }
        return duration;
    }
}
