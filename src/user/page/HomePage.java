package user.page;

import fileio.input.SongInput;
import store.data.Playlist;
import store.data.StoreNormalUsers;

public class HomePage implements Page {

    @Override
    public String print(StoreNormalUsers user) {
        StringBuilder string = new StringBuilder();
        string.append("Liked songs:\n\t[");
        int num = 0;
        for (SongInput currSong : user.getLikedSongs()) {
            if (num != 0) {
                string.append(", ");
            }
            string.append(currSong.getName());
            num++;
            if (num == 5) {
                break;
            }

        }
        string.append("]\n\nFollowed playlists:\n\t[");
        num = 0;
        for (Playlist currPlaylist : user.getFollowedPlaylists()) {
            if (num != 0) {
                string.append(", ");
            }
            string.append(currPlaylist.getName());
            num++;
            if (num == 5) {
                break;
            }

        }
        string.append("]");
        return string.toString();
    }
    @Override
    public String getType(StoreNormalUsers user) {
        return "HomePage";
    }
}
