package store.data;

import command.input.Command;
import command.input.DoCommand;
import command.input.UserCommand;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class StoreArtist extends StoreUsers{
    private List<Album> artistAlbumList = new ArrayList<>();
    @Getter
    private static class Merch {
        private String name;
        private String description;
        private Integer price;

        public Merch() {
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
    @Getter
    private List<Merch> merchList = new ArrayList<>();
    @Getter
    private static class Event {
        private String name;
        private String description;
        private String date;

        public Event() {
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
    @Getter
    private List<Event> eventList = new ArrayList<>();
    public StoreArtist() {
    }

    @Override
    public StoreUsers getArtist() {
        return this;
    }

    private String verifyValidAlbum(Command command) {
        for (Album currAlbum : artistAlbumList) {
            if (currAlbum.getName().equals(((UserCommand) command).getName())) {
                return super.username + " has another album with the same name.";
            }
        }
        Set<String> set = new HashSet<>();
        for (SongInput currSong : ((UserCommand) command).getSongs()) {
            if (!set.add(currSong.getName())) {
                return super.username + " has the same song at least twice in this album.";
            }
        }
        return null;
    }
    @Override
    public String addAlbum(Command command) {
        List<Album> allAlbums = DoCommand.getAllAlbums();
        if (verifyValidAlbum(command) == null) {
            Album newAlbum = new Album();
            newAlbum.initAlbumFromCommand((UserCommand) command);
            artistAlbumList.add(newAlbum);
            allAlbums.add(newAlbum);

            for (SongInput currSong : newAlbum.getSongList()) {
                int existingSong = 0;
                for (SongInput currLibrarySong : LibraryInput.getInstance().getSongs()) {
                    if (currSong.getName().equals(currLibrarySong.getName())) {
                        existingSong = 1;
                        break;
                    }
                }
                if (existingSong == 0) {
                    LibraryInput.getInstance().getSongs().add(currSong);
                }
            }
            return super.username + " has added new album successfully.";
        } else {
            return verifyValidAlbum(command);
        }
    }
    private boolean validDeleteAlbum(Album album) {
        for (StoreUsers currUser : DoCommand.getAllUsers()) {
            if (!currUser.validAlbumDelete(album)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public String removeAlbum(Command command) {
        String albumName = ((UserCommand) command).getName();
        Album album = null;
        for (Album currAlbum : artistAlbumList) {
            if (currAlbum.getName().equals(albumName)) {
                album = currAlbum;
                break;
            }
        }
        if (album != null) {
            if (validDeleteAlbum(album)) {
                return this.username + " deleted the album successfully.";
            } else {
                return this.username + " can't delete this album.";
            }
        } else {
            return this.username + " doesn't have an album with the given name.";
        }
    }
    @Override
    public List<Album> getAlbums() {
//        List<String> albumNames = new ArrayList<>();
//        for (Album currAlbum : artistAlbumList) {
//            albumNames.add(currAlbum.getName());
//        }
//        return  albumNames;
        List<Album> currAlbums = new ArrayList<>(artistAlbumList);
        return currAlbums;
    }
    private boolean validDate(final String date) {
        String[] parsed = date.split("-");
        if (Arrays.stream(parsed).count() != 3) {
            return false;
        }
        List<Integer> nums = new ArrayList<>();
        for (String currStr : parsed) {
            nums.add(Integer.parseInt(currStr));
        }
        // Verificare zile
        if (nums.get(1) == 2) {
            // Februarie
            if (nums.get(0) > 28) {
                return false;
            }
        } else {
            if (nums.get(0) > 31) {
                return false;
            }
        }
        // Verificare luna
        if (nums.get(1) > 12) {
            return false;
        }
        // Verificare an
        if (nums.get(2) < 1900 || nums.get(2) > 2023) {
            return false;
        }
        return true;
    }
    @Override
    public String addEvent(final Command command) {
        UserCommand currCommand = (UserCommand) command;
        for (Event currEvent : eventList) {
            if (currCommand.getName().equals(currEvent.getName())) {
                return super.username + " has another event with the same name.";
            }
        }
        if (!validDate(currCommand.getDate())) {
            return "Event for " + super.username + " does not have a valid date.";
        }
        Event newEvent = new Event();
        newEvent.setName(currCommand.getName());
        newEvent.setDescription(currCommand.getDescription());
        newEvent.setDate(currCommand.getDate());
        eventList.add(newEvent);
        return super.username + " has added new event successfully.";
    }
    @Override
    public String addMerch(Command command) {
        UserCommand currCommand = (UserCommand) command;
        for (Merch currMerch : merchList) {
            if (currCommand.getName().equals(currMerch.getName())) {
                return super.username + " has merchandise with the same name.";
            }
        }
        if (currCommand.getPrice() < 0) {
            return "Price for merchandise can not be negative.";
        }
        Merch newMerch = new Merch();
        newMerch.setName(currCommand.getName());
        newMerch.setPrice(currCommand.getPrice());
        newMerch.setDescription(currCommand.getDescription());
        merchList.add(newMerch);
        return super.username + " has added new merchandise successfully.";
    }
    public String getPrintMerch() {
        if (!merchList.isEmpty()) {
            StringBuilder string = new StringBuilder();
            int num = 0;
            for (Merch currMerch : merchList) {
                if (num != 0) {
                    string.append(", ");
                }
                string.append(currMerch.getName());
                string.append(" - ");
                string.append(currMerch.getPrice().toString());
                string.append(":\n\t");
                string.append(currMerch.getDescription());
                num++;
                if (num == 5) {
                    break;
                }
            }
            return string.toString();

        } else {
            return "";
        }
    }
    public String getPrintEvent() {
        if (!eventList.isEmpty()) {
            StringBuilder string = new StringBuilder();
            int num = 0;
            for (Event currEvent : eventList) {
                if (num != 0) {
                    string.append(", ");
                }
                string.append(currEvent.getName());
                string.append(" - ");
                string.append(currEvent.getDate());
                string.append(":\n\t");
                string.append(currEvent.getDescription());
                num++;
                if (num == 5) {
                    break;
                }
            }
            return string.toString();
        } else {
            return "";
        }
    }
    @Override
    public void deleteAllFiles() {
        for (Album currAlbum : artistAlbumList) {
            for (SongInput currSong : currAlbum.getSongList()) {
                LibraryInput.getInstance().getSongs().remove(currSong);
                for (StoreUsers currUser : DoCommand.getAllUsers()) {
                    if (currUser.getNormal() != null) {
                        currUser.getLikedSongs().remove(currSong);
                    }
                }
            }
            DoCommand.getAllAlbums().remove(currAlbum);
        }
    }
}
