package command.input;

public class PlayerCommand extends Command {
    private int seed;
    private int playlistId;
    public PlayerCommand() {
    }
    public PlayerCommand(final InitCommand source) {
        super.command = source.getCommand();
        super.username = source.getUsername();
        super.timestamp = source.getTimestamp();
        this.seed = source.getSeed();
        this.playlistId = source.getPlaylistId();
    }

    /**
     *
     * @return seed
     */
    public int getSeed() {
        return seed;
    }

    /**
     *
     * @param seed used for randomize
     */
    public void setSeed(final int seed) {
        this.seed = seed;
    }
    /**
     *
     * @return playlistId
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     *
     * @param playlistId id of playlist in list
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }
}
