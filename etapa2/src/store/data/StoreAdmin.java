package store.data;

import command.input.AdminCommand;
import command.input.Command;
import command.input.DoCommand;

import java.util.List;

public class StoreAdmin extends StoreUsers {
    public StoreAdmin() {
    }
    @Override
    public String addUser(Command command, List<StoreUsers> users) {
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
    public String deleteUser(Command command, List<StoreUsers> users) {
        boolean exists = false;
        int index = 0;
        StoreUsers deletedUser = null;
        for (StoreUsers currUser : DoCommand.getAllUsers()) {
            if (currUser.getUsername().equals(command.getUsername())) {
                exists = true;
                index = DoCommand.getAllUsers().indexOf(currUser);
                deletedUser = currUser;
                break;
            }
        }
        if (!exists) {
            return "The username " + command.getUsername() + " doesn't exist.";
        }
        for (StoreUsers currUser : DoCommand.getAllUsers()) {
            currUser.updateAudioSource(command);
            currUser.updateTimestamp(command.getTimestamp());
        }
        for (StoreUsers currUser : DoCommand.getAllUsers()) {
            if (currUser.getUserAudioSource() != null) {
                if (currUser.getUserAudioSource().getOwner() != null) {
                    if (currUser.getUserAudioSource().getOwner().equals(command.getUsername())) {
                        return command.getUsername() + " can't be deleted.";
//                                + currUser.getUserAudioSource().getName() + " " + currUser.getUsername();
                    }
                }
            }
        }
        deletedUser.deleteAllFiles();
//        DoCommand.getAllAlbums().removeIf(currAlbum -> currAlbum.getOwner().equals(command.getUsername()));
//        DoCommand.getAllPlaylists().removeIf(currPlaylist -> currPlaylist.getOwner().equals(command.getUsername()));
        DoCommand.getAllUsers().remove(index);
        return command.getUsername() + " was successfully deleted.";
    }
}
