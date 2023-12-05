package fileio.input;

import java.util.ArrayList;

public final class LibraryInput {
    private ArrayList<SongInput> songs;
    private ArrayList<PodcastInput> podcasts;
    private ArrayList<UserInput> users;

    private static LibraryInput instance;

    /**
     *
     * @return instance of library
     */
    public static LibraryInput getInstance() {
        if (instance == null) {
            instance = new LibraryInput();
        }
        return instance;
    }

    /**
     *
     * @param instance LibraryInput
     */
    public static void setInstance(final LibraryInput instance) {
        LibraryInput.instance = instance;
    }

    public LibraryInput() {
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    public ArrayList<PodcastInput> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final ArrayList<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    public ArrayList<UserInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    /**
     *
     * @param songName name of searched song
     * @return the song with name songName
     */
    public SongInput findSongByName(final String songName) {
        for (SongInput currSong : songs) {
            if (currSong.getName().equals(songName)) {
                return currSong;
            }
        }
        return null;
    }

    /**
     *
     * @param podcastName name of searched podcast
     * @return podcast with name podcastName
     */
    public PodcastInput findPodcastByName(final String podcastName) {
        for (PodcastInput currPodcast : podcasts) {
            if (currPodcast.getName().equals(podcastName)) {
                return currPodcast;
            }
        }
        return null;
    }
}
