package user.page;

import fileio.input.SongInput;
import store.data.Playlist;
import store.data.StoreNormalUsers;

public class LikedContentPage implements Page {

    @Override
    public String print(StoreNormalUsers user) {
        StringBuilder string = new StringBuilder();
        string.append("Liked Songs:\n\t[");
        for (SongInput currSong : user.getLikedSongs()) {
            if (user.getLikedSongs().indexOf(currSong) != 0) {
                string.append(", ");
            }
            string.append(currSong.getName());
            string.append(" - ");
            string.append(currSong.getArtist());
        }
        string.append("]\n\nFollowed Playlists:\n\t[");

        for (Playlist currPlaylist : user.getFollowedPlaylists()) {
            if (user.getFollowedPlaylists().indexOf(currPlaylist) != 0) {
                string.append(", ");
            }
            string.append(currPlaylist.getName());
            string.append(" - ");
            string.append(currPlaylist.getOwner());
        }
        string.append("]");
        return string.toString();
    }
}
