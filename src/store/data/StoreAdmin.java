package store.data;

import audio.source.SourcePlaylist;
import command.input.AdminCommand;
import command.input.Command;
import command.input.DoCommand;
import fileio.input.SongInput;

import java.util.List;

public class StoreAdmin extends StoreUsers {
    public StoreAdmin() {
    }
    @Override
    public String addUser(Command command) {
        List<StoreUsers> users = StatisticsData.getInstance().getAllUsers();
        String username = command.getUsername();
        for (StoreUsers currUser : users) {
            if (currUser.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }
        switch (((AdminCommand)command).getType()) {
            case "user":
                StoreNormalUsers user = new StoreNormalUsers();
                user.setUsername(username);
                user.setAge(((AdminCommand) command).getAge());
                user.setCity(((AdminCommand) command).getCity());
                users.add(user);
                break;
            case "artist":
                StoreArtist artist = new StoreArtist();
                artist.setUsername(username);
                artist.setAge(((AdminCommand) command).getAge());
                artist.setCity(((AdminCommand) command).getCity());
                users.add(artist);
                break;
            case "host":
                StoreHost host = new StoreHost();
                host.setUsername(username);
                host.setAge(((AdminCommand) command).getAge());
                host.setCity(((AdminCommand) command).getCity());
                users.add(host);
                break;
            default:
                break;
        }
        return "The username " + username + " has been added successfully.";
    }
    public String deleteUser(Command command) {
        boolean exists = false;
        int index = 0;
        StoreUsers deletedUser = null;
        for (StoreUsers currUser : StatisticsData.getInstance().getAllUsers()) {
            if (currUser.getUsername().equals(command.getUsername())) {
                exists = true;
                index = StatisticsData.getInstance().getAllUsers().indexOf(currUser);
                deletedUser = currUser;
                break;
            }
        }
        if (!exists) {
            return "The username " + command.getUsername() + " doesn't exist.";
        }
        for (StoreUsers currUser : StatisticsData.getInstance().getAllUsers()) {
            currUser.updateAudioSource(command);
            currUser.updateTimestamp(command.getTimestamp());
        }
        for (StoreUsers currUser : StatisticsData.getInstance().getAllUsers()) {
            if (currUser.getUserAudioSource() != null) {
                if (currUser.getUserAudioSource().getOwner() != null) {
                    if (currUser.getUserAudioSource().getOwner().equals(command.getUsername())) {
                        return command.getUsername() + " can't be deleted.";
                    }
                    if (currUser.getUserAudioSource().getAudioType().equals("playlist")) {
                        Playlist currPlaylist = ((SourcePlaylist) currUser.getUserAudioSource()).getCurrentPlaylist();
                        SongInput currentSong = ((SourcePlaylist) currUser.getUserAudioSource()).getSongFromPlaylist(command.getTimestamp());
                        if (currentSong.getArtist().equals(command.getUsername())) {
                            return command.getUsername() + " can't be deleted.";
                        }
                    }
                }
            }
        }
        for (StoreUsers currUser : StatisticsData.getInstance().getAllUsers()) {
            if (currUser.getNormal() != null) {
                if (currUser.getPageType() != null) {
                    if (currUser.getPageType().equals("ArtistPage")) {
                        if (((StoreNormalUsers) currUser).getArtistHost().equals(deletedUser)) {
                            return command.getUsername() + " can't be deleted.";
                        }
                    } else if (currUser.getPageType().equals("HostPage")) {
                        if (((StoreNormalUsers) currUser).getArtistHost().equals(deletedUser)) {
                            return command.getUsername() + " can't be deleted.";
                        }
                    }
                }
            }
        }
        deletedUser.deleteAllFiles();
        StatisticsData.getInstance().getAllUsers().remove(index);
        return command.getUsername() + " was successfully deleted.";
    }
}
