package command.input;

import java.util.List;

public class ParseCommand {
    private List<InitCommand> nonParsedCommands;

    private List<Command> parsedCommands;

    public ParseCommand(final List<InitCommand> nonParsedCommands,
                        final List<Command> parsedCommands) {
        this.nonParsedCommands = nonParsedCommands;
        this.parsedCommands = parsedCommands;
    }

    /**
     *
     * @return command list with null fields
     */
    public List<InitCommand> getNonParsedCommands() {
        return nonParsedCommands;
    }

    /**
     *
     * @param nonParsedCommands command list with null fields
     */
    public void setNonParsedCommands(final List<InitCommand> nonParsedCommands) {
        this.nonParsedCommands = nonParsedCommands;
    }
    /**
     *
     * @return command list without null fields
     */
    public List<Command> getParsedCommands() {
        return parsedCommands;
    }

    /**
     * Populates the non-null field command list
     */
    public void action() {
        for (InitCommand nonParsedCommand : nonParsedCommands) {

            switch (nonParsedCommand.getCommand()) {
                case "search":
                    parsedCommands.add(new SearchCommand(nonParsedCommand));
                    break;
                case "select":
                    parsedCommands.add(new SelectCommand(nonParsedCommand));
                    break;
                case "load":
                    parsedCommands.add(new LoadCommand(nonParsedCommand));
                    break;
                case "status":
                    parsedCommands.add(new StatusCommand(nonParsedCommand));
                    break;
                case "playPause":
                    parsedCommands.add(new PlayPauseCommand(nonParsedCommand));
                    break;
                case "createPlaylist":
                    parsedCommands.add(new PlaylistCommand(nonParsedCommand));
                    break;
                case "addRemoveInPlaylist":
                    parsedCommands.add(new PlaylistCommand(nonParsedCommand));
                    break;
                case "like":
                    parsedCommands.add(new PlaylistCommand(nonParsedCommand));
                    break;
                case "showPlaylists":
                    parsedCommands.add(new PlaylistCommand(nonParsedCommand));
                    break;
                case "showPreferredSongs":
                    parsedCommands.add(new PlaylistCommand(nonParsedCommand));
                    break;
                case "repeat":
                    parsedCommands.add(new PlayerCommand(nonParsedCommand));
                    break;
                case "shuffle":
                    parsedCommands.add(new PlayerCommand(nonParsedCommand));
                    break;
                case "next":
                    parsedCommands.add(new PlayerCommand(nonParsedCommand));
                    break;
                case "prev":
                    parsedCommands.add(new PlayerCommand(nonParsedCommand));
                    break;
                case "forward":
                    parsedCommands.add(new PlayerCommand(nonParsedCommand));
                    break;
                case "backward":
                    parsedCommands.add(new PlayerCommand(nonParsedCommand));
                    break;
                case "follow":
                    parsedCommands.add(new PlayerCommand(nonParsedCommand));
                    break;
                case "switchVisibility":
                    parsedCommands.add(new PlayerCommand(nonParsedCommand));
                    break;
                case "getTop5Playlists":
                    parsedCommands.add(new StatisticsCommand(nonParsedCommand));
                    break;
                case "getTop5Songs":
                    parsedCommands.add(new StatisticsCommand(nonParsedCommand));
                    break;
                case "getAllUsers":
                    parsedCommands.add(new StatisticsCommand(nonParsedCommand));
                    break;
                case "switchConnectionStatus":
                    parsedCommands.add(new UserCommand(nonParsedCommand));
                    break;
                case "getOnlineUsers":
                    parsedCommands.add(new UserCommand(nonParsedCommand));
                    break;
                case "addUser":
                    parsedCommands.add(new AdminCommand(nonParsedCommand));
                    break;
                case "deleteUser":
                    parsedCommands.add(new AdminCommand(nonParsedCommand));
                    break;
                case "addAlbum":
                    parsedCommands.add(new UserCommand(nonParsedCommand));
                    break;
                case "showAlbums":
                    parsedCommands.add(new UserCommand(nonParsedCommand));
                    break;
                case "printCurrentPage":
                    parsedCommands.add(new UserCommand(nonParsedCommand));
                    break;
                case "addEvent":
                    parsedCommands.add(new UserCommand(nonParsedCommand));
                    break;
                case "addMerch":
                    parsedCommands.add(new UserCommand(nonParsedCommand));
                    break;
                default:
                    break;
            }
        }

    }
}
