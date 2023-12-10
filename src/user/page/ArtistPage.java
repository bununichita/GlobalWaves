package user.page;

import store.data.Album;
import store.data.StoreArtist;
import store.data.StoreNormalUsers;

public class ArtistPage implements Page {

    @Override
    public String print(StoreNormalUsers user) {
        if (user.getArtistHost().getArtist() != null) {
            StoreArtist artist = (StoreArtist) user.getArtistHost();
            StringBuilder string = new StringBuilder();
            string.append("Albums:\n\t[");
            int num = 0;
            for (Album currAlbum : artist.getAlbums()) {
                if (num != 0) {
                    string.append(", ");
                }
                string.append(currAlbum.getName());
                num++;
                if (num == 5) {
                    break;
                }
            }
            string.append("]\n\nMerch:\n\t[");
            num = 0;
            string.append(artist.getPrintMerch());
            string.append("]\n\nEvents:\n\t[");
            string.append(artist.getPrintEvent());
            string.append("]");
            return string.toString();
        } else {
            return user.getUsername() + " is trying to access a non-existent page.";
        }

    }
}
