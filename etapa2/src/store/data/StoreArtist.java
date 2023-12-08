package store.data;

import command.input.Command;
import command.input.DoCommand;
import command.input.UserCommand;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreArtist extends StoreUsers{
    private List<Album> artistAlbumList = new ArrayList<>();
    @Getter
    private static class Merch {
        private String name;
        private String description;
        private int price;

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
    @Override
    public Object getAlbums() {
//        List<String> albumNames = new ArrayList<>();
//        for (Album currAlbum : artistAlbumList) {
//            albumNames.add(currAlbum.getName());
//        }
//        return  albumNames;
        List<Album> currAlbums = new ArrayList<>(artistAlbumList);
        return currAlbums;
    }
}
