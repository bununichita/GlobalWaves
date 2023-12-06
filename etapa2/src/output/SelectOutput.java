package output;

import command.input.Command;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import store.data.Playlist;
import store.data.StoreNormalUsers;
import store.data.UserSelected;

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
                }
            } else {
                super.message = "The selected ID is too high.";
            }
        } else {
            super.message = "Please conduct a search before making a selection.";
        }
    }

}
