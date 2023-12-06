package store.data;

import command.input.AdminCommand;
import command.input.Command;

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
}
