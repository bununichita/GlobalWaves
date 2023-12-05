package command.input;

public class PlaylistCommand extends Command {
    private String playlistName;
    private int playlistId;
    public PlaylistCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
        this.playlistName = source.getPlaylistName();
        this.playlistId = source.getPlaylistId();
    }

    /**
     *
     * @return playlist name
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     *
     * @param playlistName name of playlist to set
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /**
     *
     * @return playlist id in playlist list
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     *
     * @param playlistId id of playlist to set
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }
}
