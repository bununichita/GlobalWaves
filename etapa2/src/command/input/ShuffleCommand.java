package command.input;

public class ShuffleCommand extends Command {
    private int seed;

    public ShuffleCommand(final int seed) {
        this.seed = seed;
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
     * @param seed seed for randomize
     */
    public void setSeed(final int seed) {
        this.seed = seed;
    }
}
