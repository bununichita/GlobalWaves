package command.input;

import audio.source.SourcePlaylist;
import fileio.input.LibraryInput;
import lombok.Getter;
import output.*;
import store.data.*;

import java.util.ArrayList;
import java.util.List;

public class DoCommand {



    private static void doSearch(final StoreUsers currUser, final Command currCommand,
                                 final List<Output> outputList) {
        SearchOutput newSearchNode = new SearchOutput();
        newSearchNode.initSearchFromCommand((SearchCommand) currCommand);
        if (currUser.isStatusOffline()) {
            newSearchNode.setMessage(currUser.getUsername() + " is offline.");
            outputList.add(newSearchNode);
            return;
        }
        currUser.setLastUserSelection(null);
        if (currUser.getUserAudioSource() != null) {
            currUser.updateAudioSource(currCommand);
            currUser.storePreviousSource();
            currUser.setUserAudioSource(null);
        }


        newSearchNode.decideSearchType((SearchCommand) currCommand);
        newSearchNode.doMessage();
        outputList.add(newSearchNode);

        currUser.setTimestamp(currCommand.getTimestamp());
        currUser.setUserLastOutput(newSearchNode);
        currUser.setUserLastCommand(currCommand);
        currUser.setLastSearch(newSearchNode);
        currUser.setLastSearchType(((SearchCommand) currCommand).getType());
    }
    private static void doSelect(final Command currCommand, final StoreUsers currUser,
                          final List<Output> outputList) {
        SelectOutput newSelectNode = new SelectOutput();
        newSelectNode.setCommand(currCommand.getCommand());
        newSelectNode.setUser((currCommand.getUsername()));
        newSelectNode.setTimestamp(currCommand.getTimestamp());
        newSelectNode.doSelect((StoreNormalUsers) currUser, ((SelectCommand) currCommand).getItemNumber() - 1);
        outputList.add(newSelectNode);
        currUser.setUserLastOutput(newSelectNode);
        currUser.setTimestamp(currCommand.getTimestamp());
        currUser.setUserLastCommand(currCommand);
    }

    private static void doLoad(final Command currCommand, final StoreUsers currUser,
                        final List<Output> outputList) {
        LoadOutput newLoadNode = new LoadOutput();
        newLoadNode.initLoadFromCommand((LoadCommand) currCommand);
        String message = currUser.userLoad((LoadCommand) currCommand);
        newLoadNode.setMessage(message);
        outputList.add(newLoadNode);
        currUser.setUserLastOutput(newLoadNode);
        if (newLoadNode.getMessage().equals("Playback loaded successfully.")) {
            currUser.setLastUserSelection(null);
            currUser.setLastSearch(null);
        }
    }

    private static void doStatus(final Command currCommand, final StoreUsers currUser,
                          final List<Output> outputList) {
        currUser.updateAudioSource(currCommand);
        StatusOutput newStatusNode = new StatusOutput();
        newStatusNode.initStatusFromCommand(currCommand);
        newStatusNode.setStats(currUser.userStatus((StatusCommand) currCommand));
        outputList.add(newStatusNode);
        currUser.setUserLastOutput(newStatusNode);
    }

    private static void doPlayPause(final Command currCommand, final StoreUsers currUser,
                             final List<Output> outputList) {
        currUser.updateAudioSource(currCommand);
        currUser.updateTimestamp(currCommand.getTimestamp());
        String playPauseMessage = currUser.userPlayPause(currCommand);
        PlayPauseOutput newPPNode = new PlayPauseOutput();
        newPPNode.initPPFromCommand(currCommand);
        newPPNode.setMessage(playPauseMessage);
        outputList.add(newPPNode);
    }

    private static void doCreatePlaylist(final Command currCommand, final StoreUsers currUser,
                                  final List<Output> outputList) {
        currUser.updateAudioSource(currCommand);
        currUser.updateTimestamp(currCommand.getTimestamp());
        PlaylistOutput createPlaylistOutput = new PlaylistOutput();
        createPlaylistOutput.initPlaylistFromCommand(currCommand);
        String createPlaylistMessage =
                currUser.createPlaylist((PlaylistCommand) currCommand);
        createPlaylistOutput.setMessage(createPlaylistMessage);
        outputList.add(createPlaylistOutput);
    }

    private static void doAddRemoveInPlaylist(final Command currCommand, final StoreUsers currUser,
                                       final List<Output> outputList) {
        currUser.updateAudioSource(currCommand);
        currUser.updateTimestamp(currCommand.getTimestamp());
        PlaylistOutput addRemoveOutput = new PlaylistOutput();
        addRemoveOutput.initPlaylistFromCommand(currCommand);
        String addRemoveMessage =
                currUser.addRemoveInPlaylist((PlaylistCommand) currCommand);
        addRemoveOutput.setMessage(addRemoveMessage);
        outputList.add(addRemoveOutput);
    }

    private static Object getOnlineUsers() {
        List<String> usersOnlineName = new ArrayList<>();
        for (StoreUsers currUser: StatisticsData.getInstance().getAllUsers()) {
            String userToAdd = currUser.retUserNormalOnline();
            if (userToAdd != null) {
                usersOnlineName.add(currUser.retUserNormalOnline());
            }
        }
        return usersOnlineName;
    }

    /**
     * Verifies command type, then does the corresponding code
     * @param commands command list
     * @param outputList output list
     */
    public static void makeAllCommands(final List<Command> commands, final List<Output> outputList) {

        LibraryInput library = LibraryInput.getInstance();

        List<Output> ignoredOuts = new ArrayList<>();
        for (Command currCommand : commands) {
            StoreUsers currUser = new StoreNormalUsers();
            currUser = currUser.findUserByName(currCommand.getUsername());
            switch (currCommand.getCommand()) {
                case "search":
                    doSearch(currUser, currCommand, outputList);
                    break;
                case "select":
                    doSelect(currCommand, currUser, outputList);
                    break;
                case "load":
                    doLoad(currCommand, currUser, outputList);
                    break;
                case "status":
                    doStatus(currCommand, currUser, outputList);
                    break;
                case "playPause":
                    doPlayPause(currCommand, currUser, outputList);
                    break;
                case "createPlaylist":
                    doCreatePlaylist(currCommand, currUser, outputList);
                    break;
                case "addRemoveInPlaylist":
                    doAddRemoveInPlaylist(currCommand, currUser, outputList);
                    break;
                case "like":
                    currUser.updateAudioSource(currCommand);
                    currUser.updateTimestamp(currCommand.getTimestamp());
                    PlaylistOutput likeOutput = new PlaylistOutput();
                    likeOutput.initPlaylistFromCommand(currCommand);
                    String likeMessage;
                    if (currUser.isStatusOffline()) {
                        likeMessage = currUser.getUsername() + " is offline.";
                    } else {
                        likeMessage =
                                currUser.userLikeUnlike((PlaylistCommand) currCommand);
                    }
                    likeOutput.setMessage(likeMessage);
                    outputList.add(likeOutput);
                    break;
                case "showPlaylists":
                    currUser.updateAudioSource(currCommand);
                    currUser.updateTimestamp(currCommand.getTimestamp());
                    PlaylistOutput showPlaylistsOutput = new PlaylistOutput();
                    showPlaylistsOutput.initPlaylistFromCommand(currCommand);
                    showPlaylistsOutput.setResult(currUser.getCurrentUserPlaylistList());
                    outputList.add(showPlaylistsOutput);
                    break;
                case "showPreferredSongs":
                    currUser.updateAudioSource(currCommand);
                    currUser.updateTimestamp(currCommand.getTimestamp());
                    PlaylistOutput showPreferredSongsOutput = new PlaylistOutput();
                    showPreferredSongsOutput.initPlaylistFromCommand(currCommand);
                    showPreferredSongsOutput.
                            setResult(new ArrayList<>(currUser.userShowLikedSongs()));
                    outputList.add(showPreferredSongsOutput);
                    break;
                case "repeat":
                    currUser.updateAudioSource(currCommand);
                    PlayerOutput repeatOutput = new PlayerOutput();
                    repeatOutput.initPlayerFromCommand(currCommand);
                    repeatOutput.setMessage(currUser.userRepeat());
                    if (currUser.getUserAudioSource() != null) {
                        if (currUser.getUserAudioSource().getAudioType().equals("playlist")) {
                            if (currUser.getUserAudioSource().
                                    getRepeat().equals("Repeat Current Song")) {
                                ((SourcePlaylist) currUser.getUserAudioSource()).
                                        storeRepeatedSong();
                            }
                        }
                    }
                    outputList.add(repeatOutput);
                    break;
                case "shuffle":
                    currUser.updateAudioSource(currCommand);
                    PlayerOutput shuffleOutput = new PlayerOutput();
                    shuffleOutput.initPlayerFromCommand(currCommand);
                    int seed = ((PlayerCommand) currCommand).getSeed();
                    shuffleOutput.setMessage(currUser.userShuffle(seed));
                    outputList.add(shuffleOutput);
                    break;
                case "next":
                    currUser.updateAudioSource(currCommand);
                    PlayerOutput nextOutput = new PlayerOutput();
                    nextOutput.initPlayerFromCommand(currCommand);
                    String nextMessage = currUser.userNext();
                    nextOutput.setMessage(nextMessage);
                    outputList.add(nextOutput);
                    break;
                case "prev":
                    currUser.updateAudioSource(currCommand);
                    PlayerOutput prevOutput = new PlayerOutput();
                    prevOutput.initPlayerFromCommand(currCommand);
                    String prevMessage = currUser.userPrev();
                    prevOutput.setMessage(prevMessage);
                    outputList.add(prevOutput);
                    break;
                case "forward":
                    currUser.updateAudioSource(currCommand);
                    PlayerOutput forwardOutput = new PlayerOutput();
                    forwardOutput.initPlayerFromCommand(currCommand);
                    String forwardMessage = currUser.userForward();
                    forwardOutput.setMessage(forwardMessage);
                    outputList.add(forwardOutput);
                    break;
                case "backward":
                    currUser.updateAudioSource(currCommand);
                    PlayerOutput backwardOutput = new PlayerOutput();
                    backwardOutput.initPlayerFromCommand(currCommand);
                    String backwardMessage = currUser.userBackward();
                    backwardOutput.setMessage(backwardMessage);
                    outputList.add(backwardOutput);
                    break;
                case "follow":
                    currUser.updateAudioSource(currCommand);
                    PlayerOutput followOutput = new PlayerOutput();
                    followOutput.initPlayerFromCommand(currCommand);
                    String followMessage = currUser.userFollow();
                    followOutput.setMessage(followMessage);
                    outputList.add(followOutput);
                    break;
                case "switchVisibility":
                    currUser.updateAudioSource(currCommand);
                    PlayerOutput switchVisibilityOutput = new PlayerOutput();
                    switchVisibilityOutput.initPlayerFromCommand(currCommand);
                    String switchVisibilityMessage = currUser.
                            userSwitchVisibility(((PlayerCommand) currCommand).getPlaylistId());
                    switchVisibilityOutput.setMessage(switchVisibilityMessage);
                    outputList.add(switchVisibilityOutput);
                    break;
                case "getTop5Playlists":
                    StatisticsOutput top5PlaylistsOutput = new StatisticsOutput();
                    top5PlaylistsOutput.initStatisticsFromCommand(currCommand);
                    top5PlaylistsOutput.setPlaylistResult();
                    outputList.add(top5PlaylistsOutput);
                    break;
                case"getTop5Songs":
                    StatisticsOutput top5SongsOutput = new StatisticsOutput();
                    top5SongsOutput.initStatisticsFromCommand(currCommand);
                    top5SongsOutput.setSongResult();
                    outputList.add(top5SongsOutput);
                    break;
                case "getTop5Albums":
                    StatisticsOutput top5AlbumsOutput = new StatisticsOutput();
                    top5AlbumsOutput.initStatisticsFromCommand(currCommand);
                    top5AlbumsOutput.setAlbumResult();
                    outputList.add(top5AlbumsOutput);
                    break;
                case "getAllUsers":
                    StatisticsOutput allUsersOutput = new StatisticsOutput();
                    allUsersOutput.initStatisticsFromCommand(currCommand);
                    allUsersOutput.setAllPlayersResult();
                    outputList.add(allUsersOutput);
                    break;
                case "switchConnectionStatus":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.userSwitchConStat(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }

                    outputList.add(currCommand.getOutput());

                    break;
                case "getOnlineUsers":
                    currCommand.setResult(getOnlineUsers());
                    outputList.add(currCommand.getOutput());
                    break;
                case "addUser":
                    StoreAdmin adminAdd = new StoreAdmin();
                    currCommand.setOutputMessage(adminAdd.addUser(currCommand));
                    outputList.add(currCommand.getOutput());
                    break;
                case "deleteUser":
                    StoreAdmin adminDelete = new StoreAdmin();
                    currCommand.setOutputMessage(adminDelete.deleteUser(currCommand));
                    outputList.add(currCommand.getOutput());
                    break;
                case "addAlbum":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.addAlbum(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }

                    outputList.add(currCommand.getOutput());
                    break;
                case "removeAlbum":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.removeAlbum(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }
                    outputList.add(currCommand.getOutput());
                    break;
                case "addPodcast":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.addPodcast(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }

                    outputList.add(currCommand.getOutput());
                    break;
                case "removePodcast":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.removePodcast(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }

                    outputList.add(currCommand.getOutput());
                    break;
                case "showAlbums":
                    currCommand.setResult(currUser.getAlbums());
                    outputList.add(currCommand.getOutput());
                    break;
                case "showPodcasts":
                    currCommand.setResult(currUser.getPodcasts());
                    outputList.add(currCommand.getOutput());
                    break;
                case "printCurrentPage":
                    if (currUser.isStatusOffline()) {
                        currCommand.setOutputMessage(currUser.getUsername() + " is offline.");
                    } else {
                        currCommand.setOutputMessage(currUser.printCurrentPage());
                    }
                    outputList.add(currCommand.getOutput());
                    break;
                case "changePage":
                    if (currUser.isStatusOffline()) {
                        currCommand.setOutputMessage(currUser.getUsername() + " is offline.");
                    } else {
                        currCommand.setOutputMessage(currUser.changePage(currCommand));
                    }
                    outputList.add(currCommand.getOutput());
                    break;
                case "addEvent":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.addEvent(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }
                    outputList.add(currCommand.getOutput());
                    break;
                case "removeEvent":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.removeEvent(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }
                    outputList.add(currCommand.getOutput());
                    break;
                case "addMerch":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.addMerch(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }
                    outputList.add(currCommand.getOutput());
                    break;
                case "addAnnouncement":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.addAnnouncement(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }
                    outputList.add(currCommand.getOutput());
                    break;
                case "removeAnnouncement":
                    if (currUser != null) {
                        currCommand.setOutputMessage(currUser.removeAnnouncement(currCommand));
                    } else {
                        currCommand.setOutputMessage("The username " + currCommand.getUsername() + " doesn't exist.");
                    }
                    outputList.add(currCommand.getOutput());
                    break;
                default:
                    break;

            }
        }
    }
}
