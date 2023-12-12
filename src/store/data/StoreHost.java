package store.data;

import audio.source.SourcePodcast;
import command.input.Command;
import command.input.DoCommand;
import command.input.UserCommand;
import fileio.input.EpisodeInput;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreHost extends StoreUsers{
    @Getter
    private List<PodcastInput> hostPodcastList = new ArrayList<>();
    public class PodcastResult {
        private String name;
        private List<String> episodes = new ArrayList<>();

        public PodcastResult() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getEpisodes() {
            return episodes;
        }

        public void setEpisodes(List<String> episodes) {
            this.episodes = episodes;
        }
    }
    private class Announcement {
        private String name;
        private String description;

        public Announcement() {

        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(final String description) {
            this.description = description;
        }
    }
    private List<Announcement> announcementList = new ArrayList<>();
    public StoreHost() {
    }

    public void setHostPodcastList(List<PodcastInput> hostPodcastList) {
        this.hostPodcastList = hostPodcastList;
    }

    @Override
    public StoreUsers getHost() {
        return this;
    }
    private String verifyValidPodcast(PodcastInput podcast) {
        for (PodcastInput currPodcast : hostPodcastList) {
            if (currPodcast.getName().equals(podcast.getName())) {
                return this.username + " has another podcast with the same name.";
            }
        }
        Set<String> set = new HashSet<>();
        for (EpisodeInput currEpisode : podcast.getEpisodes()) {
            if (!set.add(currEpisode.getName())) {
                return super.username + " has the same episode in this podcast.";
            }
        }
        return null;
    }
    @Override
    public String addPodcast(final Command command) {
        UserCommand hostCommand = (UserCommand) command;
        PodcastInput newPodcast = new PodcastInput();
        newPodcast.setName(hostCommand.getName());
        newPodcast.setEpisodes((ArrayList<EpisodeInput>) hostCommand.getEpisodes());
        newPodcast.setOwner(this.username);
        if (verifyValidPodcast(newPodcast) == null) {
            LibraryInput.getInstance().getPodcasts().add(newPodcast);
            hostPodcastList.add(newPodcast);
            return super.username + " has added new podcast successfully.";
        } else {
            return verifyValidPodcast(newPodcast);
        }

    }
    @Override
    public String removePodcast(Command command) {
        UserCommand hostCommand = (UserCommand) command;
        PodcastInput podcast = null;
        for (PodcastInput currPodcast : hostPodcastList) {
            if (currPodcast.getName().equals(hostCommand.getName())) {
                podcast = currPodcast;
            }
        }
        if (podcast != null) {
            for (StoreUsers currUser : StatisticsData.getInstance().getAllUsers()) {
                if (currUser.getNormal() != null) {
                    StoreNormalUsers normalUser = (StoreNormalUsers) currUser;
                    if (normalUser.getUserAudioSource() != null) {
                        if (normalUser.getUserAudioSource().getAudioType().equals("podcast")) {
                            SourcePodcast podcastSource = (SourcePodcast) normalUser.getUserAudioSource();
                            if (podcastSource.getCurrentPodcast().getName().equals(podcast.getName())) {
                                return username + " can't delete this podcast.";
                            }
                        }
                    }
                }
            }
            hostPodcastList.remove(podcast);
            return username + " deleted the podcast successfully.";
        } else {
            return this.username + " doesn't have a podcast with the given name.";
        }
    }
    @Override
    public void deleteAllFiles() {
        int sed = 9;
    }
    @Override
    public String addAnnouncement(Command command) {
        UserCommand currCommand = (UserCommand) command;
        for (Announcement currAnnouncement : announcementList) {
            if (currAnnouncement.getName().equals(currCommand.getName())) {
                return super.username + " has already added an announcement with this name.";
            }
        }
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setName(currCommand.getName());
        newAnnouncement.setDescription(currCommand.getDescription());
        announcementList.add(newAnnouncement);
        return super.username + " has successfully added new announcement.";
    }
    @Override
    public String removeAnnouncement(Command command) {
        for (Announcement currAnnouncement : announcementList) {
            if (currAnnouncement.getName().equals(((UserCommand) command).getName())) {
                announcementList.remove(currAnnouncement);
                return this.username + " has successfully deleted the announcement.";
            }
        }
        return this.username + " has no announcement with the given name.";
    }
    public String getPrintAnnouncement() {
        StringBuilder string = new StringBuilder();
        for (Announcement currAnnouncement : announcementList) {
            if (announcementList.indexOf(currAnnouncement) != 0) {
                string.append("\n");
            }
            string.append(currAnnouncement.getName());
            string.append(":\n\t");
            string.append(currAnnouncement.getDescription());
        }
        string.append("\n]");
        return string.toString();
    }
    @Override
    public List<PodcastResult> getPodcasts() {
        List<PodcastResult> newResult = new ArrayList<>();
        for (PodcastInput currPodcast : hostPodcastList) {
            PodcastResult newNode = new PodcastResult();
            newNode.setName(currPodcast.getName());
            for (EpisodeInput currEpisode : currPodcast.getEpisodes()) {
                newNode.getEpisodes().add(currEpisode.getName());
            }
            newResult.add(newNode);
        }
        return newResult;
    }
}
