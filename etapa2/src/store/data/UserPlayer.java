package store.data;

public class UserPlayer {


    private boolean isPaused;

    public UserPlayer() {
    }


    public final boolean isPaused() {
        return isPaused;
    }

    public final void setPaused(final boolean paused) {
        isPaused = paused;
    }

}
