package store.data;

import audio.source.SourceAudio;
import audio.source.SourcePodcast;
import command.input.*;
import fileio.input.SongInput;
import output.Output;
import output.SearchOutput;
import output.Stats;

import java.util.List;

public abstract class StoreUsers {
    protected String username;
    protected int age;
    protected String city;

    public StoreUsers() {
    }

    public boolean getIsPausedOffline() {
        return false;
    }

    public void setIsPausedOffline(final boolean isPausedOffline) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public int getTimestamp() {
        return -1;
    }

    public void setTimestamp(final int timestamp) {

    }

    public List<Playlist> getUserPlaylistList() {
        return null;
    }

    public void setUserPlaylistList(final List<Playlist> userPlaylistList) {
    }

    public Command getUserLastCommand() {
        return null;
    }

    public void setUserLastCommand(final Command userLastCommand) {

    }

    public Output getUserLastOutput() {
        return null;
    }

    public void setUserLastOutput(final Output userLastOutput) {

    }

    public UserSelected getLastUserSelection() {
        return null;
    }

    public void setLastUserSelection(final UserSelected lastUserSelection) {
    }

    public SearchOutput getLastSearch() {
        return null;
    }

    public void setLastSearch(final SearchOutput lastSearch) {

    }

    public String getLastSearchType() {
        return null;
    }

    public void setLastSearchType(final String lastSearchType) {

    }

    public SourceAudio getUserAudioSource() {
        return null;
    }

    public void setUserAudioSource(final SourceAudio userAudioSource) {

    }

    public List<SongInput> getLikedSongs() {
        return null;
    }

    public void setLikedSongs(final List<SongInput> likedSongs) {

    }

    public List<SourcePodcast> getPreviousListenedPodcasts() {
        return null;
    }

    public void setPreviousListenedPodcasts(
            final List<SourcePodcast> previousListenedPodcasts) {
    }

    public List<Playlist> getFollowedPlaylists() {
        return null;
    }

    public void setFollowedPlaylists(final List<Playlist> followedPlaylists) {
    }

    public boolean isStatusOffline() {
        return false;
    }

    public void setStatusOffline(final boolean statusOffline) {

    }

    /**
     * Method searches the StoreUsers list to find the current user
     * @param localUsername name of the user
     * @return current user of type "storeUsers"
     */
    public StoreUsers findUserByName(final String localUsername) {
        StatisticsData statisticsData = StatisticsData.getInstance();
        for (StoreUsers currUser : statisticsData.getAllUsers()) {
            if (currUser.getUsername().equals(localUsername)) {
                return currUser;
            }
        }
        return null;
    }

    /**
     * Method updates the current audio source to this timestamp
     * @param command current command
     */
    public void updateAudioSource(final Command command) {

    }

    /**
     * Stores the current status of the podcast, for future listening
     */
    public void storePreviousSource() {

    }

    /**
     * Method that checks if this user has started this podcast before
     * @param podcastName name of the podcast that I want to listen
     * @return the state of previously listened podcast
     */
    public SourcePodcast verifyPreviouslyListened(final String podcastName) {

        return null;
    }

    /**
     * Method loads the selected audio source
     * @param command current command
     * @return output message for Load command
     */
    public String userLoad(final LoadCommand command) {
        return null;
    }

    /**
     * Verifies the current status of the audio source
     * @param command current command
     * @return the stats for the current audio source
     */
    public Stats userStatus(final StatusCommand command) {
        return null;
    }

    /**
     * Changes the play / pause status of the audio source
     * @param command current command
     * @return output message for the Like command
     */
    public String userPlayPause(final Command command) {
        return username + " is not a normal user.";

    }

    /**
     * Method that updates the user timestamp
     * @param localTimestamp current timestamp
     */
    public void updateTimestamp(final int localTimestamp) {
    }

    /**
     * Method that creates a new playlist for this user
     * @param command current command
     * @return output message for createPlaylist command
     */
    public String createPlaylist(final PlaylistCommand command) {
        return username + " is not a normal user.";
    }

    /**
     * @param command current command
     * @return output message for addRemoveInPlaylist command
     */
    public String addRemoveInPlaylist(final PlaylistCommand command) {
        return username + " is not a normal user.";
    }

    /**
     * Method that updates the user likedSongs list of the player and global list of songs
     *
     * @param command current command
     * @return output message for Like command
     */
    public String userLikeUnlike(final PlaylistCommand command) {
        return username + " is not a normal user.";
    }

    /**
     *
     * @return the list of user liked songs
     */
    public List<String> userShowLikedSongs() {
        return null;
    }

    /**
     *
     * @param name of the searched playlist
     * @return the playlist with the searched name
     */
    public Playlist findPlaylistByName(final String name) {
        return null;
    }

    /**
     * Method that changes the repeat status of the current audio source
     * @return output message for Repeat command
     */
    public String userRepeat() {
        return username + " is not a normal user.";
    }
    /**
     * Method that shuffles the current playlist and stores the non-shuffled version
     * @return output message for Shuffle command
     */
    public String userShuffle(final int seed) {

        return username + " is not a normal user.";
    }

    /**
     * Changes the audio source of the user to next song / episode
     * @return the output message for the Next command
     */
    public String userNext() {
        return username + " is not a normal user.";
    }
    /**
     * Changes the audio source of the user to previous song / episode
     * @return the output message for the Prev command
     */
    public String userPrev() {
        return username + " is not a normal user.";
    }

    /**
     * Skips to the next 90 seconds
     * @return output message for the Forward command
     */
    public String userForward() {
        return username + " is not a normal user.";
    }
    /**
     * Goes to the previous 90 seconds
     * @return output message for the Backward command
     */
    public String userBackward() {
        return username + " is not a normal user.";
    }

    /**
     * Method that adds the selected playlist to the users followed playlists list
     * @return the output message for the Follow command
     */
    public String userFollow() {
        return username + " is not a normal user.";
    }

    /**
     *
     * @return the current user playlist list
     */
    public List<Object> getCurrentUserPlaylistList() {
        return null;
    }

    /**
     * Change the visibility of the playlist with playlistId id
     * @param playlistId id of the playlist in user playlist list
     * @return output message for the SwitchVisibility command
     */
    public String userSwitchVisibility(final int playlistId) {
        return username + " is not a normal user.";
    }

    public String userSwitchConStat(Command command) {
        return username + " is not a normal user.";
    }
    public String retUserNormalOnline() {
        return null;
    }
    public String addUser(Command command) {
    return null;
    }
    public String addAlbum(Command command) {
        return this.username + " is not an artist.";
    }
    public String removeAlbum(Command command) {
        return this.username + " is not an artist.";
    }
    public boolean validAlbumDelete(Album album) {
        return false;
    }
    public String addPodcast(Command command) {
        return this.username + " is not a host.";
    }
    public String removePodcast(Command command) {
        return this.username + " is not a host.";
    }
    public List<Album> getAlbums() {
        return null;
    }
    public List<StoreHost.PodcastResult> getPodcasts() {
        return null;
    }
    public String printCurrentPage() {
        return this.username + " is trying to access a non-existent page.";
    }
    public String addEvent(Command command) {
        return this.username + " is not an artist.";
    }
    public String removeEvent(Command command) {
        return this.username + " is not an artist.";
    }
    public String addMerch(Command command) {
        return this.username + " is not an artist.";
    }
    public String addAnnouncement(Command command) {
        return this.username + " is not a host.";
    }
    public String removeAnnouncement(Command command) {
        return this.username + " is not a host.";
    }
    public StoreUsers getArtist() {
        return null;
    }
    public StoreUsers getNormal() {
        return null;
    }
    public StoreUsers getHost() {
        return null;
    }
    public Album findAlbumByName(String albumName) {
        return null;
    }
    public void deleteAllFiles() {
    }
    public String changePage(Command command) {
        return this.username + " is trying to access a non-existent page.";
    }
    public String getPageType() {
        return null;
    }
}
