package store.data;

import command.input.DoCommand;
import fileio.input.LibraryInput;
import fileio.input.SongInput;

public class StoreHost extends StoreUsers{
    public StoreHost() {
    }
    public StoreUsers getHost() {
        return this;
    }
    @Override
    public void deleteAllFiles() {
        int sed = 9;
    }
}
