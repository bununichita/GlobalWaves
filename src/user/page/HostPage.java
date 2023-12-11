package user.page;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import store.data.Album;
import store.data.StoreArtist;
import store.data.StoreHost;
import store.data.StoreNormalUsers;

public class HostPage implements Page {

    @Override
    public String print(StoreNormalUsers user) {
        if (user.getArtistHost().getHost() != null) {
            StoreHost host = (StoreHost) user.getArtistHost();
            StringBuilder string = new StringBuilder();
            string.append("Podcasts:\n\t[");
            int num = 0;
            for (PodcastInput currPodcast : host.getHostPodcastList()) {
                if (num != 0) {
                    string.append("]\n, ");
                }
                string.append(currPodcast.getName());
                string.append(":\n\t[");
                for (EpisodeInput currEpisode : currPodcast.getEpisodes()) {
                    if (currPodcast.getEpisodes().indexOf(currEpisode) != 0) {
                        string.append(", ");
                    }
                    string.append(currEpisode.getName());
                    string.append(" - ");
                    string.append(currEpisode.getDescription());
                }
//                string.append("],");
                num++;
//                if (num == 5) {
//                    break;
//                }
            }
            string.append("]\n]\n\nAnnouncements:\n\t[");
//            num = 0;
            string.append(host.getPrintAnnouncement());
            return string.toString();
        } else {
            return user.getUsername() + " is trying to access a non-existent page.";
        }
    }
    @Override
    public String getType(StoreNormalUsers user) {
        return "HostPage";
    }
}
