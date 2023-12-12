package store.data;

import audio.source.*;
import command.input.*;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import lombok.Getter;
import output.Output;
import output.SearchOutput;
import output.Stats;
import user.page.HomePage;
import user.page.LikedContentPage;
import user.page.Page;

import java.util.ArrayList;
import java.util.List;

public class StoreNormalUsers extends StoreUsers{

    private int timestamp;

    private List<Playlist> userPlaylistList = new ArrayList<>();
    private Command userLastCommand;

    private Output userLastOutput;
    private UserSelected lastUserSelection;
    private SearchOutput lastSearch;
    private String lastSearchType;
    private SourceAudio userAudioSource;
    private List<SongInput> likedSongs = new ArrayList<>();
    private List<SourcePodcast> previousListenedPodcasts = new ArrayList<>();
    private List<Playlist> followedPlaylists = new ArrayList<>();
    private final int forwardBackwardTime = 90;
    private boolean statusOffline;
    private boolean isPausedOffline;
    private Page currentPage = new HomePage();
    @Getter
    private StoreUsers artistHost;

    public void setArtistHost(StoreUsers artistHost) {
        this.artistHost = artistHost;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public boolean getIsPausedOffline() {
        return isPausedOffline;
    }

    public void setIsPausedOffline(final boolean isPausedOffline) {
        this.isPausedOffline = isPausedOffline;
    }

    public StoreNormalUsers() {
    }

    public final int getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final List<Playlist> getUserPlaylistList() {
        return userPlaylistList;
    }

    public final void setUserPlaylistList(final List<Playlist> userPlaylistList) {
        this.userPlaylistList = userPlaylistList;
    }

    public final Command getUserLastCommand() {
        return userLastCommand;
    }

    public final void setUserLastCommand(final Command userLastCommand) {
        this.userLastCommand = userLastCommand;
    }

    public final Output getUserLastOutput() {
        return userLastOutput;
    }

    public final void setUserLastOutput(final Output userLastOutput) {
        this.userLastOutput = userLastOutput;
    }

    public final UserSelected getLastUserSelection() {
        return lastUserSelection;
    }

    public final void setLastUserSelection(final UserSelected lastUserSelection) {
        this.lastUserSelection = lastUserSelection;
    }

    public final SearchOutput getLastSearch() {
        return lastSearch;
    }

    public final void setLastSearch(final SearchOutput lastSearch) {
        this.lastSearch = lastSearch;
    }

    public final String getLastSearchType() {
        return lastSearchType;
    }

    public final void setLastSearchType(final String lastSearchType) {
        this.lastSearchType = lastSearchType;
    }

    public final SourceAudio getUserAudioSource() {
        return userAudioSource;
    }

    public final void setUserAudioSource(final SourceAudio userAudioSource) {
        this.userAudioSource = userAudioSource;
    }

    public final List<SongInput> getLikedSongs() {
        return likedSongs;
    }

    public final void setLikedSongs(final List<SongInput> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public final List<SourcePodcast> getPreviousListenedPodcasts() {
        return previousListenedPodcasts;
    }

    public final void setPreviousListenedPodcasts(
            final List<SourcePodcast> previousListenedPodcasts) {
        this.previousListenedPodcasts = previousListenedPodcasts;
    }

    public final List<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    public final void setFollowedPlaylists(final List<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    public boolean isStatusOffline() {
        return statusOffline;
    }

    public void setStatusOffline(final boolean statusOffline) {
        this.statusOffline = statusOffline;
    }

    /**
     * Method updates the current audio source to this timestamp
     * @param command current command
     */
    public void updateAudioSource(final Command command) {
        if (userAudioSource != null) {
            timestamp = command.getTimestamp();
            userAudioSource.updateTotalPlayed(command.getTimestamp());
            userAudioSource.setLastTimestamp(command.getTimestamp());
            if (userAudioSource.getAudioType().equals("song")) {
                SourceSong songSource = (SourceSong) userAudioSource;
                if (songSource.getRepeat().equals("Repeat Once")) {
                    if (songSource.getCurrentSong().getDuration() <= songSource.getTotalPlayed()) {
                        int newTotalPlayed = songSource.getTotalPlayed()
                                - songSource.getCurrentSong().getDuration();
                        songSource.setTotalPlayed(newTotalPlayed);
                        songSource.setRepeat("No Repeat");
                    }
                } else if (songSource.getRepeat().equals("Repeat Infinite")) {
                    if (songSource.getCurrentSong().getDuration() <= songSource.getTotalPlayed()) {
                        int newTotalPlayed = songSource.getTotalPlayed()
                                % songSource.getCurrentSong().getDuration();
                        songSource.setTotalPlayed(newTotalPlayed);
                    }
                } else {
                    if (songSource.getTotalPlayed()
                            >= songSource.getCurrentSong().getDuration()) {
                        userAudioSource = null;
                    }
                }
            } else if (userAudioSource.getAudioType().equals("playlist")) {
                SourcePlaylist playlistSource = (SourcePlaylist) userAudioSource;
                if (playlistSource.getRepeat().equals("Repeat All")) {
                    if (playlistSource.getCurrentPlaylist().getDuration()
                            <= playlistSource.getTotalPlayed()) {
                        int newTotalPlayed = playlistSource.getTotalPlayed()
                                % playlistSource.getCurrentPlaylist().getDuration();
                        playlistSource.setTotalPlayed(newTotalPlayed);
                    }
                } else if (playlistSource.getRepeat().equals("Repeat Current Song")) {
                    int startRepeatedSong = playlistSource.getStartRepeatedSong();
                    int indexRepeatedSong = playlistSource.getIndexRepeatedSong();
                    int totalPlayed = playlistSource.getTotalPlayed();
                    int repeatedSongDuration = playlistSource.getCurrentPlaylist().
                            getSongList().get(indexRepeatedSong).getDuration();
                    totalPlayed = totalPlayed - startRepeatedSong;
                    totalPlayed = totalPlayed % repeatedSongDuration;
                    totalPlayed = startRepeatedSong + totalPlayed;
                    playlistSource.setTotalPlayed(totalPlayed);
                } else {
                    if (playlistSource.getTotalPlayed()
                            >= playlistSource.getCurrentPlaylist().getDuration()) {
                        userAudioSource = null;
                    }
                }

            } else if (userAudioSource.getAudioType().equals("album")) {
                SourceAlbum albumSource = (SourceAlbum) userAudioSource;
                if (albumSource.getRepeat().equals("Repeat All")) {
                    if (albumSource.getCurrentAlbum().getDuration()
                    <= albumSource.getTotalPlayed()) {
                        int newTotalPlayed = albumSource.getTotalPlayed()
                                % albumSource.getCurrentAlbum().getDuration();
                        albumSource.setTotalPlayed(newTotalPlayed);
                    }
                } else if (albumSource.getRepeat().equals("Repeat Current Song")) {
                    int startRepeatedSong = albumSource.getStartRepeatedSong();
                    int indexRepeatedSong = albumSource.getIndexRepeatedSong();
                    int totalPlayed = albumSource.getTotalPlayed();
                    int repeatedSongDuration = albumSource.getCurrentAlbum().
                            getSongList().get(indexRepeatedSong).getDuration();
                    totalPlayed = totalPlayed - startRepeatedSong;
                    totalPlayed = totalPlayed % repeatedSongDuration;
                    totalPlayed = startRepeatedSong + totalPlayed;
                    albumSource.setTotalPlayed(totalPlayed);
                } else {
                    if (albumSource.getTotalPlayed()
                            >= albumSource.getCurrentAlbum().getDuration()) {
                        userAudioSource = null;
                    }
                }
            }
        }
        if (command.getCommand().equals("search")) {
            if (userAudioSource != null) {
                if (!userAudioSource.isPaused()) {
                    userAudioSource.changePlayPause(command);
                }
            }
        }

    }

    /**
     * Stores the current status of the podcast, for future listening
     */
    public void storePreviousSource() {
        if (userAudioSource != null) {
            if (userAudioSource.getAudioType().equals("podcast")) {
                previousListenedPodcasts.add((SourcePodcast) userAudioSource);
            }
        }

    }

    /**
     * Method that checks if this user has started this podcast before
     * @param podcastName name of the podcast that I want to listen
     * @return the state of previously listened podcast
     */
    public SourcePodcast verifyPreviouslyListened(final String podcastName) {
        for (SourcePodcast currentAudioSource : previousListenedPodcasts) {
            if (currentAudioSource.getCurrentPodcast().getName().equals(podcastName)) {
                return currentAudioSource;
            }
        }
        return null;
    }

    /**
     * Method loads the selected audio source
     * @param command current command
     * @return output message for Load command
     */
    public String userLoad(final LoadCommand command) {
        this.setUserLastCommand(command);
        this.timestamp = command.getTimestamp();
        String message;
        if (this.lastUserSelection != null) {
            switch (this.lastUserSelection.getType()) {
                case "song":
                    SourceSong newSongSource = new SourceSong();
                    newSongSource.setAudioType("song");
                    newSongSource.setLastTimestamp(this.timestamp);
                    newSongSource.setPaused(false);
                    newSongSource.setTotalPlayed(0);
                    SongInput currSong = this.lastUserSelection.getSelectedSong();
                    newSongSource.setCurrentSong(currSong);
                    newSongSource.setStartTimestamp(command.getTimestamp());
                    setUserAudioSource(newSongSource);
                    message = "Playback loaded successfully.";
                    break;
                case "podcast":
                    SourcePodcast previouslyListened;
                    previouslyListened = verifyPreviouslyListened(lastUserSelection.
                            getSelectedPodcast().getName());
                    if (previouslyListened != null) {
                        setUserAudioSource(previouslyListened);
                        userAudioSource.setLastTimestamp(command.getTimestamp());
                        userAudioSource.changePlayPause(command);
                        previousListenedPodcasts.remove(previouslyListened);
                    } else {
                        SourcePodcast newPodcastSource = new SourcePodcast();
                        newPodcastSource.setAudioType("podcast");
                        newPodcastSource.setLastTimestamp(this.timestamp);
                        newPodcastSource.setPaused(false);
                        newPodcastSource.setTotalPlayed(0);
                        PodcastInput currPodcast = this.lastUserSelection.getSelectedPodcast();
                        newPodcastSource.setCurrentPodcast(currPodcast);
                        setUserAudioSource(newPodcastSource);
                    }
                    message = "Playback loaded successfully.";
                    break;
                case "playlist":
                    SourcePlaylist newPlaylistSource = new SourcePlaylist();
                    newPlaylistSource.setAudioType("playlist");
                    newPlaylistSource.setLastTimestamp(this.timestamp);
                    newPlaylistSource.setPaused(false);
                    newPlaylistSource.setTotalPlayed(0);
                    Playlist currPlaylist = this.lastUserSelection.getSelectedPlaylist();
                    newPlaylistSource.setCurrentPlaylist(currPlaylist);
                    setUserAudioSource(newPlaylistSource);
                    message = "Playback loaded successfully.";
                    break;
                case "album":
                    SourceAlbum newAlbumSource = new SourceAlbum();
                    newAlbumSource.setAudioType("album");
                    newAlbumSource.setLastTimestamp(this.timestamp);
                    newAlbumSource.setPaused(false);
                    newAlbumSource.setTotalPlayed(0);
                    newAlbumSource.setRepeat("No Repeat");
                    Album currAlbum = this.lastUserSelection.getSelectedAlbum();
                    newAlbumSource.setCurrentAlbum(currAlbum);
                    setUserAudioSource(newAlbumSource);
                    message = "Playback loaded successfully.";
                    break;
                default:
                    message = "";
                    break;
            }

        } else {
            message = "Please select a source before attempting to load.";
        }
        return message;
    }

    /**
     * Verifies the current status of the audio source
     * @param command current command
     * @return the stats for the current audio source
     */
    public Stats userStatus(final StatusCommand command) {
        this.setUserLastCommand(command);
        this.timestamp = command.getTimestamp();
        Stats newAudioStats = new Stats();
        int audioRemainedTime;
        String audioRepeat;
        boolean audioPaused;
        String audioName;
        if (this.userAudioSource != null) {
            switch (this.userAudioSource.getAudioType()) {
                case "song":
                    audioRemainedTime = ((SourceSong) this.userAudioSource).
                            updateSongSource(this.timestamp);
                    newAudioStats.setRemainedTime(audioRemainedTime);
                    if (audioRemainedTime == 0) {
                        audioName = "";
                    } else {
                        audioName = ((SourceSong) this.userAudioSource).getCurrentSong().getName();
                    }
                    newAudioStats.setName(audioName);
                    audioRepeat = ((SourceSong) this.userAudioSource).getRepeat();
                    newAudioStats.setRepeat(audioRepeat);
                    newAudioStats.setShuffle(false);
                    audioPaused = ((SourceSong) this.userAudioSource).isPaused();
                    if (isStatusOffline()) {
                        newAudioStats.setPaused(isPausedOffline);
                    } else {
                        newAudioStats.setPaused(audioPaused);
                    }

                    break;
                case "podcast":
                    audioRemainedTime = ((SourcePodcast) this.userAudioSource).
                            updatePodcastSource(this.timestamp);
                    if (audioRemainedTime == 0) {
                        audioName = "";
                    } else {
                        audioName = ((SourcePodcast) this.userAudioSource).
                                getCurrentEpisode(userAudioSource.getTotalPlayed());
                    }
                    audioRemainedTime = ((SourcePodcast) this.userAudioSource).
                            getEpisodeRemainingTime(audioName);
                    newAudioStats.setRemainedTime(audioRemainedTime);
                    newAudioStats.setName(audioName);
                    audioRepeat = ((SourcePodcast) this.userAudioSource).getRepeat();
                    newAudioStats.setRepeat(audioRepeat);
                    newAudioStats.setShuffle(false);
                    audioPaused = ((SourcePodcast) this.userAudioSource).isPaused();
                    if (isStatusOffline()) {
                        newAudioStats.setPaused(isPausedOffline);
                    } else {
                        newAudioStats.setPaused(audioPaused);
                    }
                    break;
                case "playlist":
                    audioRemainedTime = ((SourcePlaylist) this.userAudioSource).
                            updatePlaylistSource(this.timestamp);
                    if (audioRemainedTime == 0) {
                        audioName = "";
                    } else {
                        audioName = ((SourcePlaylist) this.userAudioSource).getCurrentSong();

                    }
                    audioRemainedTime = ((SourcePlaylist) this.userAudioSource).
                            getSongRemainingTime(audioName);
                    newAudioStats.setRemainedTime(audioRemainedTime);
                    newAudioStats.setName(audioName);
                    audioRepeat = ((SourcePlaylist) this.userAudioSource).getRepeat();
                    newAudioStats.setRepeat(audioRepeat);
                    newAudioStats.setShuffle(((SourcePlaylist) this.userAudioSource).
                            getIsShuffle());
                    audioPaused = ((SourcePlaylist) this.userAudioSource).isPaused();
                    if (isStatusOffline()) {
                        newAudioStats.setPaused(isPausedOffline);
                    } else {
                        newAudioStats.setPaused(audioPaused);
                    }
                    break;
                case "album":
                    audioRemainedTime = ((SourceAlbum) this.userAudioSource).updateAlbumSource(timestamp);
                    if (audioRemainedTime == 0) {
                        audioName = "";
                    } else {
                        audioName = ((SourceAlbum) userAudioSource).getCurrentSong();
                    }
                    audioRemainedTime = ((SourceAlbum) userAudioSource).getSongRemainingTime(audioName);
                    newAudioStats.setRemainedTime(audioRemainedTime);
                    newAudioStats.setName(audioName);
                    audioRepeat = ((SourceAlbum) userAudioSource).getRepeat();
                    newAudioStats.setRepeat(audioRepeat);
                    newAudioStats.setShuffle(((SourceAlbum) userAudioSource).isShuffled());
                    audioPaused = ((SourceAlbum) userAudioSource).isPaused();
                    if (isStatusOffline()) {
                        newAudioStats.setPaused(isPausedOffline);
                    } else {
                        newAudioStats.setPaused(audioPaused);
                    }
                    break;
                default:
                    return null;
            }
        }
        return newAudioStats;
    }

    /**
     * Changes the play / pause status of the audio source
     * @param command current command
     * @return output message for the Like command
     */
    public String userPlayPause(final Command command) {
        if (this.userAudioSource != null) {
            return this.userAudioSource.changePlayPause(command);
        } else {
            return "Please load a source before attempting to pause or resume playback.";
        }

    }

    /**
     * Method that updates the user timestamp
     * @param localTimestamp current timestamp
     */
    public void updateTimestamp(final int localTimestamp) {
        this.timestamp = localTimestamp;
    }

    /**
     * Method that creates a new playlist for this user
     * @param command current command
     * @return output message for createPlaylist command
     */
    public String createPlaylist(final PlaylistCommand command) {
        StatisticsData statisticsData = StatisticsData.getInstance();
        for (Playlist currPlaylist : userPlaylistList) {
            if (currPlaylist.getName().equals(command.getPlaylistName())) {
                return "A playlist with the same name already exists.";
            }
        }
        Playlist newPlaylist = new Playlist();
        newPlaylist.setName(command.getPlaylistName());
        newPlaylist.setOwner(this.getUsername());
        userPlaylistList.add(newPlaylist);
        statisticsData.getAllPlaylists().add(newPlaylist);
        return "Playlist created successfully.";
    }

    /**
     *
     * @param command current command
     * @return output message for addRemoveInPlaylist command
     */
    public String addRemoveInPlaylist(final PlaylistCommand command) {
        String message;
        if (this.userAudioSource != null) {
            if (this.userAudioSource.getAudioType().equals("song")) {
                if (command.getPlaylistId() - 1 < this.userPlaylistList.size()) {
                    int id = command.getPlaylistId() - 1;
                    Playlist selectedPlaylist = this.userPlaylistList.get(id);
                    if (selectedPlaylist.
                            getSongList().contains(((SourceSong) this.userAudioSource).
                                    getCurrentSong())) {
                        selectedPlaylist.getSongList().
                                remove(((SourceSong) this.userAudioSource).
                                        getCurrentSong());
                        selectedPlaylist.getSongs().remove(((SourceSong) this.userAudioSource).
                                getCurrentSong().getName());
                        message = "Successfully removed from playlist.";
                    } else {
                        selectedPlaylist.getSongList().add(((SourceSong) this.userAudioSource).
                                getCurrentSong());
                        selectedPlaylist.getSongs().add(((SourceSong) this.userAudioSource).
                                getCurrentSong().getName());
                        message = "Successfully added to playlist.";
                    }
                } else {
                    message = "The specified playlist does not exist.";
                }
            } else if (this.userAudioSource.getAudioType().equals("album")) {
                if (command.getPlaylistId() - 1 < this.userPlaylistList.size()) {
                    int id = command.getPlaylistId() - 1;
                    Playlist selectedPlaylist = this.userPlaylistList.get(id);
                    String currentSongName = ((SourceAlbum) this.userAudioSource).getCurrentSong();
                    SongInput currentSong = ((SourceAlbum) this.userAudioSource).getSongFromAlbum(command.getTimestamp());
                    if (selectedPlaylist.getSongs().contains(currentSongName)) {
                        selectedPlaylist.getSongs().remove(currentSongName);
                        selectedPlaylist.getSongList().remove(currentSong);
                        message = "Successfully removed from playlist.";
                    } else {
                        selectedPlaylist.getSongList().add(currentSong);
                        selectedPlaylist.getSongs().add(currentSongName);
                        message = "Successfully added to playlist.";
                    }
                } else {
                    message = "The specified playlist does not exist.";
                }
            } else {
                message = "The loaded source is not a song.";
            }
        } else {
            message = "Please load a source before adding to or removing from the playlist.";
        }
        return message;
    }

    /**
     * Method that updates the user likedSongs list of the player and global list of songs
     * @param command current command
     * @return output message for Like command
     */
    public String userLikeUnlike(final PlaylistCommand command) {
        StatisticsData statisticsData = StatisticsData.getInstance();
        List<SongsByLikes> globalLikedSongs = statisticsData.getAllSongsByLikes();
        String message;
        if (this.userAudioSource != null) {
            if (this.userAudioSource.getAudioType().equals("song")) {
                if (this.likedSongs.contains(((SourceSong) this.userAudioSource).
                        getCurrentSong())) {
                    this.likedSongs.remove(((SourceSong) this.userAudioSource).getCurrentSong());
                    for (SongsByLikes currSong : globalLikedSongs) {
                        if (currSong.getSong().equals(((SourceSong) this.userAudioSource).
                                getCurrentSong())) {
                            currSong.setLikeCount(currSong.getLikeCount() - 1);
                        }
                    }
                    message = "Unlike registered successfully.";
                } else {
                    this.likedSongs.add(((SourceSong) this.userAudioSource).getCurrentSong());
                    for (SongsByLikes currSong : globalLikedSongs) {
                        if (currSong.getSong().equals(((SourceSong) this.userAudioSource).
                                getCurrentSong())) {
                            currSong.setLikeCount(currSong.getLikeCount() + 1);
                        }
                    }
                    message = "Like registered successfully.";
                }
            } else if (this.userAudioSource.getAudioType().equals("playlist")) {
                SongInput currentlyPlayedSong = ((SourcePlaylist) userAudioSource).
                        getSongFromPlaylist(command.getTimestamp());
                if (likedSongs.contains(currentlyPlayedSong)) {
                    likedSongs.remove(currentlyPlayedSong);
                    for (SongsByLikes currSong : globalLikedSongs) {
                        if (currSong.getSong().equals(currentlyPlayedSong)) {
                            currSong.setLikeCount(currSong.getLikeCount() - 1);
                        }
                    }
                    message = "Unlike registered successfully.";
                } else {
                    likedSongs.add(currentlyPlayedSong);
                    for (SongsByLikes currSong : globalLikedSongs) {
                        if (currSong.getSong().equals(currentlyPlayedSong)) {
                            currSong.setLikeCount(currSong.getLikeCount() + 1);
                        }
                    }
                    message = "Like registered successfully.";
                }

            } else if (this.userAudioSource.getAudioType().equals("album")) {
                SongInput currentlyPlayedSong = ((SourceAlbum) userAudioSource).getSongFromAlbum(command.getTimestamp());
                if (likedSongs.contains(currentlyPlayedSong)) {
                    likedSongs.remove(currentlyPlayedSong);
                    for (SongsByLikes currSong : globalLikedSongs) {
                        if (currSong.getSong().equals(currentlyPlayedSong)) {
                            currSong.setLikeCount(currSong.getLikeCount() - 1);
                        }
                    }
                    for (Album currAlbum : statisticsData.getAllAlbums()) {
                        if (((SourceAlbum) userAudioSource).getCurrentAlbum().getName().equals(currAlbum.getName())) {
                            currAlbum.setTotalLikes(currAlbum.getTotalLikes() - 1);
                        }
                    }
                    message = "Unlike registered successfully.";
                } else {
                    likedSongs.add(currentlyPlayedSong);
                    for (SongsByLikes currSong : globalLikedSongs) {
                        if (currSong.getSong().equals(currentlyPlayedSong)) {
                            currSong.setLikeCount(currSong.getLikeCount() + 1);
                        }
                    }
                    for (Album currAlbum : statisticsData.getAllAlbums()) {
                        if (((SourceAlbum) userAudioSource).getCurrentAlbum().getName().equals(currAlbum.getName())) {
                            currAlbum.setTotalLikes(currAlbum.getTotalLikes() + 1);
                        }
                    }
                    message = "Like registered successfully.";
                }
            } else {
                message = "Loaded source is not a song.";
            }
        } else {
            message = "Please load a source before liking or unliking.";
        }
        return message;
    }

    /**
     *
     * @return the list of user liked songs
     */
    public List<String> userShowLikedSongs() {
        List<String> likedSongsName = new ArrayList<>();

        for (SongInput currSong : likedSongs) {
            if (currSong != null) {
                likedSongsName.add(currSong.getName());
            }

        }
        return likedSongsName;
    }

    /**
     *
     * @param name of the searched playlist
     * @return the playlist with the searched name
     */
    public Playlist findPlaylistByName(final String name) {
        StatisticsData statisticsData = StatisticsData.getInstance();
        for (Playlist currPlaylist : statisticsData.getAllPlaylists()) {
            if (currPlaylist.getName().equals(name)) {
                return currPlaylist;
            }
        }
        return null;
    }

    /**
     * Method that changes the repeat status of the current audio source
     * @return output message for Repeat command
     */
    public String userRepeat() {
        if (userAudioSource != null) {
            return userAudioSource.changeRepeat();
        } else {
            return "Please load a source before setting the repeat status.";
        }

    }
    /**
     * Method that shuffles the current playlist and stores the non-shuffled version
     * @return output message for Shuffle command
     */
    public String userShuffle(final int seed) {

        if (userAudioSource != null) {
            if (userAudioSource.getAudioType().equals("playlist")) {
                SourcePlaylist playlistSource = (SourcePlaylist) userAudioSource;
                return playlistSource.doShuffle(seed);
            } else if (userAudioSource.getAudioType().equals("album")) {
                SourceAlbum albumSource = (SourceAlbum) userAudioSource;
                return albumSource.doShuffle(seed);
            } else {
                return "The loaded source is not a playlist or an album.";
            }
        } else {
            return "Please load a source before using the shuffle function.";
        }
    }

    /**
     * Changes the audio source of the user to next song / episode
     * @return the output message for the Next command
     */
    public String userNext() {
        if (userAudioSource != null) {
            if (userAudioSource.getAudioType().equals("playlist")) {
                SourcePlaylist playlistSource = (SourcePlaylist) userAudioSource;
                if (playlistSource.getPlaylistSize()
                        - playlistSource.getCurrentSongIndex() >= 2) {
                    String currSongName = playlistSource.getCurrentSong();
                    int remainingCurrSongTime =
                            playlistSource.getSongRemainingTime(currSongName);
                    playlistSource.setTotalPlayed(playlistSource.getTotalPlayed()
                            + remainingCurrSongTime);
                    String newCurrSongName = playlistSource.getCurrentSong();
                    playlistSource.setPaused(false);
                    if (playlistSource.getRepeat().equals("Repeat Current Song")) {
                        return "Skipped to next track successfully. The current track is "
                                + currSongName + ".";
                    }
                    return "Skipped to next track successfully. The current track is "
                            + newCurrSongName + ".";
                } else {
                    String currSongName;
                    if (playlistSource.getRepeat().equals("Repeat All")) {
                        playlistSource.setTotalPlayed(0);
                        currSongName = playlistSource.getCurrentSong();
                        return "Skipped to next track successfully. The current track is "
                                + currSongName + ".";
                    } else if (playlistSource.getRepeat().equals("Repeat Current Song")) {
                        currSongName = playlistSource.getCurrentSong();
                        int newTotalPlayed = playlistSource.getStartRepeatedSong();
                        playlistSource.setTotalPlayed(newTotalPlayed);
                        return "Skipped to next track successfully. The current track is "
                                + currSongName + ".";
                    }
                    playlistSource.setTotalPlayed(playlistSource.getCurrentPlaylist().
                            getDuration());
                    return "Please load a source before skipping to the next track.";
                }
            } else if (userAudioSource.getAudioType().equals("podcast")) {
                SourcePodcast podcastSource = (SourcePodcast) userAudioSource;
                if (podcastSource.getPodcastSize()
                        - podcastSource.getCurrentEpisodeIndex() >= 2) {
                    String currEpisodeName = podcastSource.getCurrentEpisode();
                    int remainingCurrEpisodeTime = podcastSource.getEpisodeRemainingTime();
                    podcastSource.setTotalPlayed(podcastSource.getTotalPlayed()
                            + remainingCurrEpisodeTime);
                    currEpisodeName = podcastSource.getCurrentEpisode();
                    return "Skipped to next track successfully. The current track is "
                            + currEpisodeName + ".";
                } else {
                    return "Please load a source before skipping to the next track.";
                }
            } else if (userAudioSource.getAudioType().equals("album")) {
                SourceAlbum albumSource = (SourceAlbum) userAudioSource;
                if (albumSource.getAlbumSize() - albumSource.getCurrentSongIndex() >= 2) {
                    String currSongName = albumSource.getCurrentSong();
                    int remainingCurrSongTime = albumSource.getSongRemainingTime(currSongName);
                    albumSource.setTotalPlayed(albumSource.getTotalPlayed() + remainingCurrSongTime);
                    String newCurrSongName = albumSource.getCurrentSong();
                    albumSource.setPaused(false);
                    if (albumSource.getRepeat().equals("Repeat Current Song")) {
                        return "Skipped to next track successfully. The current track is "
                                + currSongName + ".";
                    }
                    return "Skipped to next track successfully. The current track is "
                            + newCurrSongName + ".";
                } else {
                    String currSongName;
                    if (albumSource.getRepeat().equals("Repeat All")) {
                        albumSource.setTotalPlayed(0);
                        currSongName = albumSource.getCurrentSong();
                        return "Skipped to next track successfully. The current track is "
                                + currSongName + ".";
                    } else if (albumSource.getRepeat().equals("Repeat Current Song")) {
                        currSongName = albumSource.getCurrentSong();
                        int newTotalPlayed = albumSource.getStartRepeatedSong();
                        albumSource.setTotalPlayed(newTotalPlayed);
                        return "Skipped to next track successfully. The current track is "
                                + currSongName + ".";
                    }
                    albumSource.setTotalPlayed(albumSource.getCurrentAlbum().getDuration());
                    return "Please load a source before skipping to the next track.";
                }
            }
        } else {
            return "Please load a source before skipping to the next track.";
        }
        return null;
    }
    /**
     * Changes the audio source of the user to previous song / episode
     * @return the output message for the Prev command
     */
    public String userPrev() {
        if (userAudioSource != null) {
            if (userAudioSource.getAudioType().equals("playlist")) {
                SourcePlaylist playlistSource = (SourcePlaylist) userAudioSource;
                SongInput currSong = playlistSource.getSongFromPlaylist(timestamp);
                String currSongName = playlistSource.getCurrentSong();
                int remainingCurrSongTime = playlistSource.getSongRemainingTime(currSongName);
                if (currSong.getDuration() == remainingCurrSongTime) {
                    if (playlistSource.getCurrentSongIndex() >= 1) {
                        int newIndex = playlistSource.getCurrentSongIndex() - 1;
                        int newTotalPlayed = playlistSource.getTotalPlayed();
                        newTotalPlayed -= playlistSource.getCurrentPlaylist().
                                getSongList().get(newIndex).getDuration();
                        playlistSource.setTotalPlayed(newTotalPlayed);
                        currSongName = playlistSource.getCurrentSong();
                        playlistSource.setPaused(false);
                        return "Returned to previous track successfully. The current track is "
                                + currSongName + ".";
                    } else {
                        playlistSource.setTotalPlayed(0);
                        currSongName = playlistSource.getCurrentSong();
                        playlistSource.setPaused(false);
                        return "Returned to previous track successfully. The current track is "
                                + currSongName + ".";
                    }
                } else {
                    int remainingSongTime = playlistSource.getSongRemainingTime(currSongName);
                    int newTotalPlayed = playlistSource.getTotalPlayed();
                    newTotalPlayed += remainingSongTime;
                    newTotalPlayed -= currSong.getDuration();
                    playlistSource.setTotalPlayed(newTotalPlayed);
                    currSongName = playlistSource.getCurrentSong();
                    playlistSource.setPaused(false);
                    return "Returned to previous track successfully. The current track is "
                            + currSongName + ".";
                }
            } else if (userAudioSource.getAudioType().equals("album")) {
                SourceAlbum albumSource = (SourceAlbum) userAudioSource;
                SongInput currSong = albumSource.getSongFromAlbum(timestamp);
                String currSongName = albumSource.getCurrentSong();
                int remainingCurrSongTime = albumSource.getSongRemainingTime(currSongName);
                if (currSong.getDuration() == remainingCurrSongTime) {
                    if (albumSource.getCurrentSongIndex() >= 1) {
                        int newIndex = albumSource.getCurrentSongIndex() - 1;
                        int newTotalPlayed = albumSource.getTotalPlayed();
                        newTotalPlayed -= albumSource.getCurrentAlbum().
                                getSongList().get(newIndex).getDuration();
                        albumSource.setTotalPlayed(newTotalPlayed);
                        currSongName = albumSource.getCurrentSong();
                        albumSource.setPaused(false);
                        return "Returned to previous track successfully. The current track is "
                                + currSongName + ".";
                    } else {
                        albumSource.setTotalPlayed(0);
                        currSongName = albumSource.getCurrentSong();
                        albumSource.setPaused(false);
                        return "Returned to previous track successfully. The current track is "
                                + currSongName + ".";
                    }
                } else {
                    int remainingSongTime = albumSource.getSongRemainingTime(currSongName);
                    int newTotalPlayed = albumSource.getTotalPlayed();
                    newTotalPlayed += remainingSongTime;
                    newTotalPlayed -= currSong.getDuration();
                    albumSource.setTotalPlayed(newTotalPlayed);
                    currSongName = albumSource.getCurrentSong();
                    albumSource.setPaused(false);
                    return "Returned to previous track successfully. The current track is "
                            + currSongName + ".";
                }

            }
        } else {
            return "Please load a source before returning to the previous track.";
        }
        return null;
    }

    /**
     * Skips to the next 90 seconds
     * @return output message for the Forward command
     */
    public String userForward() {
        if (userAudioSource != null) {
            if (userAudioSource.getAudioType().equals("podcast")) {
                SourcePodcast podcastSource = (SourcePodcast) userAudioSource;
                int remainingTimeCurrentEpisode = podcastSource.getEpisodeRemainingTime();

                if (remainingTimeCurrentEpisode < forwardBackwardTime) {
                    podcastSource.setTotalPlayed(podcastSource.getTotalPlayed()
                            + remainingTimeCurrentEpisode);
                } else {
                    podcastSource.setTotalPlayed(podcastSource.getTotalPlayed()
                            + forwardBackwardTime);
                }
                return "Skipped forward successfully.";
            } else {
                return "The loaded source is not a podcast.";
            }
        } else {
            return "Please load a source before attempting to forward.";
        }
    }
    /**
     * Goes to the previous 90 seconds
     * @return output message for the Backward command
     */
    public String userBackward() {
        if (userAudioSource != null) {
            if (userAudioSource.getAudioType().equals("podcast")) {
                SourcePodcast podcastSource = (SourcePodcast) userAudioSource;
                int playedTimeCurrentEpisode = podcastSource.getCurrentEpisodePlayedTime();
                if (playedTimeCurrentEpisode < forwardBackwardTime) {
                    podcastSource.setTotalPlayed(podcastSource.getTotalPlayed()
                            - playedTimeCurrentEpisode);
                } else {
                    podcastSource.setTotalPlayed(podcastSource.getTotalPlayed()
                            - forwardBackwardTime);
                }
                return "Rewound successfully.";
            } else {
                return "The loaded source is not a podcast.";
            }
        } else {
            return "Please select a source before rewinding.";
        }
    }

    /**
     * Method that adds the selected playlist to the users followed playlists list
     * @return the output message for the Follow command
     */
    public String userFollow() {
        if (lastUserSelection != null) {
            if (lastUserSelection.getType().equals("playlist")) {
                Playlist selectedPlaylist = lastUserSelection.getSelectedPlaylist();
                if (selectedPlaylist.getOwner().equals(username)) {
                    return "You cannot follow or unfollow your own playlist.";
                } else {
                    if (followedPlaylists.contains(selectedPlaylist)) {
                        selectedPlaylist.setFollowers(selectedPlaylist.getFollowers() - 1);
                        followedPlaylists.remove(selectedPlaylist);
                        return "Playlist unfollowed successfully.";
                    } else {
                        selectedPlaylist.setFollowers(selectedPlaylist.getFollowers() + 1);
                        followedPlaylists.add(selectedPlaylist);
                        return "Playlist followed successfully.";
                    }
                }
            } else {
                return "The selected source is not a playlist.";
            }
        } else {
            return "Please select a source before following or unfollowing.";
        }
    }

    /**
     *
     * @return the current user playlist list
     */
    public List<Object> getCurrentUserPlaylistList() {
        List<Object> currentUserPlaylistList = new ArrayList<>();
        for (Playlist currPlaylist : userPlaylistList) {
            currentUserPlaylistList.add(new Playlist(currPlaylist));
        }
        return currentUserPlaylistList;
    }

    /**
     * Change the visibility of the playlist with playlistId id
     * @param playlistId id of the playlist in user playlist list
     * @return output message for the SwitchVisibility command
     */
    public String userSwitchVisibility(final int playlistId) {
        int newPlaylistId = playlistId;
        if (newPlaylistId > userPlaylistList.size()) {
            return "The specified playlist ID is too high.";
        } else {
            newPlaylistId--;
            Playlist selectedPlaylist = userPlaylistList.get(newPlaylistId);
            if (selectedPlaylist.getVisibility().equals("private")) {
                selectedPlaylist.setVisibility("public");
                return "Visibility status updated successfully to public.";
            } else {
                selectedPlaylist.setVisibility("private");
                return "Visibility status updated successfully to private.";
            }

        }
    }

    public String userSwitchConStat(Command command) {
        updateAudioSource(command);
        updateTimestamp(command.getTimestamp());
        if (statusOffline) {

            if (userAudioSource != null) {
                userPlayPause(command);
            }

            statusOffline = false;
        } else {
            if (userAudioSource != null) {
                isPausedOffline = userAudioSource.isPaused();
                if (!userAudioSource.isPaused()) {
                    userPlayPause(command);
                }
            }
            statusOffline = true;
        }
        return username + " has changed status successfully.";
    }
    @Override
    public boolean validAlbumDelete(Album album) {

        for (SongInput currSong : album.getSongList()) {
//            verificare playlist-uri user
            for (Playlist currPlaylist : userPlaylistList) {
                if (currPlaylist.getSongList().contains(currSong)) {
                    return false;
                }
            }
//            verificare sursa audio
            if (userAudioSource != null) {
                switch (userAudioSource.getAudioType()) {
                    case "album" -> {
                        SourceAlbum albumSource = (SourceAlbum) userAudioSource;
                        if (albumSource.getCurrentAlbum().getName().equals(album.getName())) {
                            return false;
                        }
                    }
                    case "playlist" -> {
                        SourcePlaylist playlistSource = (SourcePlaylist) userAudioSource;
                        for (SongInput currPlaylistSong : playlistSource.getCurrentPlaylist().getSongList()) {
                            if (currSong.equals(currPlaylistSong)) {
                                return false;
                            }
                        }
                    }
                    case "song" -> {
                        SourceSong songSource = (SourceSong) userAudioSource;
                        if (currSong.equals(songSource.getCurrentSong())) {
                            return false;
                        }
                    } default -> {

                    }
                }
            }
        }
        return true;
    }

    public String retUserNormalOnline() {
        if (!isStatusOffline()) {
            return this.username;
        } else {
            return null;
        }
    }
    @Override
    public String printCurrentPage() {
        return currentPage.print(this);
    }
    @Override
    public StoreUsers getNormal() {
        return this;
    }
    @Override
    public Album findAlbumByName(String albumName) {
        for (Album currAlbum : StatisticsData.getInstance().getAllAlbums()) {
            if (currAlbum.getName().equals(albumName)) {
                return currAlbum;
            }
        }
        return null;
    }
    @Override
    public void deleteAllFiles() {
        for (Playlist currPlaylist : userPlaylistList) {
            for (StoreUsers currUser : StatisticsData.getInstance().getAllUsers()) {
                if (currUser.getNormal() != null) {
                    StoreNormalUsers normalUser = (StoreNormalUsers) currUser;
                    if (normalUser.getFollowedPlaylists().contains(currPlaylist)) {
                        normalUser.getFollowedPlaylists().remove(currPlaylist);
                    }
                }
            }
            StatisticsData.getInstance().getAllPlaylists().remove(currPlaylist);
        }
        for (Playlist currPlaylist : followedPlaylists) {
            currPlaylist.setFollowers(currPlaylist.getFollowers() - 1);
        }
    }
    @Override
    public String changePage(Command command) {
        UserCommand currCommand = (UserCommand) command;
        if (currCommand.getNextPage().equals("Home")) {
            currentPage = new HomePage();
            return this.username + " accessed Home successfully.";
        } else if (currCommand.getNextPage().equals("LikedContent")) {
            currentPage = new LikedContentPage();
            return this.username + " accessed LikedContent successfully.";
        }
        return null;
    }
    @Override
    public String getPageType() {
        return currentPage.getType(this);
    }
}
