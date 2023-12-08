package store.data;

import fileio.input.PodcastInput;
import fileio.input.SongInput;

public class UserSelected {
    private String type;
    private SongInput selectedSong;
    private PodcastInput selectedPodcast;
    private Playlist selectedPlaylist;
    private StoreArtist selectedArtist;

    public UserSelected() {
    }

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final SongInput getSelectedSong() {
        return selectedSong;
    }

    public final void setSelectedSong(final SongInput selectedSong) {
        this.selectedSong = selectedSong;
    }

    public final PodcastInput getSelectedPodcast() {
        return selectedPodcast;
    }

    public final void setSelectedPodcast(final PodcastInput selectedPodcast) {
        this.selectedPodcast = selectedPodcast;
    }

    public final Playlist getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public final void setSelectedPlaylist(final Playlist playlist) {
        this.selectedPlaylist = playlist;
    }

    public final StoreArtist getSelectedArtist() {
        return selectedArtist;
    }

    public void setSelectedArtist(StoreArtist selectedArtist) {
        this.selectedArtist = selectedArtist;
    }
}
