package output;

import command.input.Command;
import command.input.DoCommand;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import store.data.*;
import user.page.ArtistPage;

public class SelectOutput extends Output {
    public SelectOutput() {

    }

    /**
     * Initialize particular type of output from generic command
     * @param commands of type Command
     */
    public SelectOutput(final Command commands) {
        super.command = commands.getCommand();
        super.timestamp = commands.getTimestamp();
        super.user = commands.getUsername();
    }

    /**
     *
     * @param user user that selects
     * @param index index of selected item
     */
    public void doSelect(final StoreNormalUsers user, final int index) {
        if (user.getLastSearch() != null) {
            if (index < user.getLastSearch().getResults().size()) {
                UserSelected userSelection = new UserSelected();
                if (user.getLastSearchType().equals("song")) {
                    userSelection.setType("song");
                    String songName = user.getLastSearch().getResults().get(index);
                    SongInput selectedSong = LibraryInput.getInstance().findSongByName(songName);
                    userSelection.setSelectedSong(selectedSong);
                    user.setLastUserSelection(userSelection);
                    super.message = "Successfully selected "
                            + user.getLastUserSelection().getSelectedSong().getName() + ".";
                } else if (user.getLastSearchType().equals("podcast")) {
                    userSelection.setType("podcast");
                    String podcastName = user.getLastSearch().getResults().get(index);
                    PodcastInput selectedPodcast =
                            LibraryInput.getInstance().findPodcastByName(podcastName);
                    userSelection.setSelectedPodcast(selectedPodcast);
                    user.setLastUserSelection(userSelection);
                    super.message = "Successfully selected "
                            + user.getLastUserSelection().getSelectedPodcast().getName() + ".";
                } else if (user.getLastSearchType().equals("playlist")) {
                    userSelection.setType("playlist");
                    String playlistName = user.getLastSearch().getResults().get(index);
                    Playlist selectedPlaylist = user.findPlaylistByName(playlistName);
                    userSelection.setSelectedPlaylist(selectedPlaylist);
                    user.setLastUserSelection(userSelection);
                    super.message = "Successfully selected "
                            + user.getLastUserSelection().getSelectedPlaylist().getName() + ".";
                } else if (user.getLastSearchType().equals("artist")) {
                    String artistName = user.getLastSearch().getResults().get(index);
                    StoreUsers selectedArtist = (user.findUserByName(artistName));
                    if (selectedArtist.getArtist() != null) {
                        userSelection.setType("artist");
                        userSelection.setSelectedArtist((StoreArtist) selectedArtist);
                        user.setLastUserSelection(userSelection);
                        super.message = "Successfully selected " + artistName + "'s page.";
                        ArtistPage artistPage = new ArtistPage();
                        user.setCurrentPage(artistPage);
                    } else {
                        super.message = "Selection error";
                    }

                }
            } else {
                super.message = "The selected ID is too high.";
            }
        } else {
            super.message = "Please conduct a search before making a selection.";
        }
    }

}
