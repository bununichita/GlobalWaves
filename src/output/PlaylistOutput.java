package output;

import com.fasterxml.jackson.annotation.JsonInclude;
import command.input.Command;

import java.util.List;

public class PlaylistOutput extends Output {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Object> result;
    public PlaylistOutput() {
    }

    /**
     *
     * @return playlist result
     */
    public List<Object> getResult() {
        return result;
    }

    /**
     *
     * @param result playlist result to set
     */
    public void setResult(final List<Object> result) {
        this.result = result;
    }

    /**
     * Initialize particular type of output from generic command
     * @param command source
     */
    public void initPlaylistFromCommand(final Command command) {
        super.command = command.getCommand();
        super.user = command.getUsername();
        super.timestamp = command.getTimestamp();
    }
}
