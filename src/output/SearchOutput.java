package output;

import command.input.Command;
import command.input.DoCommand;
import command.input.SearchCommand;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import store.data.Album;
import store.data.Playlist;
import store.data.StatisticsData;
import store.data.StoreUsers;

import java.util.ArrayList;
import java.util.List;

public class SearchOutput extends Output {

    private List<String> results = new ArrayList<>();
    private final int maxNumber = 5;

    /**
     * Initialize particular type of output from generic command
     * @param command SearchCommand
     */
    public void initSearchFromCommand(final SearchCommand command) {
        super.command = command.getCommand();
        super.user = command.getUsername();
        super.timestamp = command.getTimestamp();
    }

    /**
     *
     * @return output type
     */
    public String getCommand() {
        return super.command;
    }

    /**
     *
     * @return output username
     */
    public String getUser() {
        return super.user;
    }

    /**
     *
     * @return output timestamp
     */
    public int getTimestamp() {
        return super.timestamp;
    }

    /**
     *
     * @return search result
     */
    public List<String> getResults() {
        return results;
    }

    /**
     *
     * @param results to set
     */
    public void setResults(final List<String> results) {
        this.results = results;
    }

    /**
     * Searches the song list based on filters
     * @param command type SearchCommand
     */
    public void foundSongName(final SearchCommand command) {
        LibraryInput library = LibraryInput.getInstance();
        int songsFound = 0;
        for (SongInput songNode : library.getSongs()) {
            boolean validCandidate = true;
            if (command.getFilters().getName() != null) {
                if (!songNode.getName().startsWith(command.getFilters().getName())) {
                    validCandidate = false;
                }
            }
            if (command.getFilters().getAlbum() != null && validCandidate) {
                if (!songNode.getAlbum().equals(command.getFilters().getAlbum())) {
                    validCandidate = false;
                }
            }
            if (!command.getFilters().getTags().isEmpty() && validCandidate) {
                if (!songNode.getTags().containsAll(command.getFilters().getTags())) {
                    validCandidate = false;
                }

            }
            if (command.getFilters().getLyrics() != null && validCandidate) {
                if (!songNode.getLyrics().toLowerCase().
                        contains((command.getFilters().getLyrics()).toLowerCase())) {
                    validCandidate = false;
                }
            }
            if (command.getFilters().getGenre() != null && validCandidate) {
                if (!songNode.getGenre().equalsIgnoreCase(command.getFilters().getGenre())) {
                    validCandidate = false;
                }
            }
            if (command.getFilters().getReleaseYear() != null && validCandidate) {
                char operator = command.getFilters().getReleaseYear().charAt(0);
                int year = Integer.parseInt(command.getFilters().getReleaseYear().substring(1));
                if (operator == '<') {
                    if (!(songNode.getReleaseYear() < year)) {
                        validCandidate = false;
                    }
                } else if (operator == '>') {
                    if (!(songNode.getReleaseYear() > year)) {
                        validCandidate = false;
                    }
                }
            }
            if (command.getFilters().getArtist() != null && validCandidate) {
                if (!songNode.getArtist().equals(command.getFilters().getArtist())) {
                    validCandidate = false;
                }
            }

            if (validCandidate) {
                results.add(songNode.getName());
                songsFound++;
            }
            if (songsFound == maxNumber) {
                return;
            }
        }
    }
    /**
     * Searches the podcast list based on filters
     * @param command type SearchCommand
     */
    public void foundPodcastName(final SearchCommand command) {
        LibraryInput library = LibraryInput.getInstance();
        int podcastsFound = 0;
        for (PodcastInput songNode : library.getPodcasts()) {
            boolean validCandidate = true;
            if (command.getFilters().getName() != null) {
                if (!songNode.getName().startsWith(command.getFilters().getName())) {
                    validCandidate = false;
                }
            }
            if (command.getFilters().getOwner() != null && validCandidate) {
                if (!songNode.getOwner().equals(command.getFilters().getOwner())) {
                    validCandidate = false;
                }
            }

            if (validCandidate) {
                results.add(songNode.getName());
                podcastsFound++;
            }
            if (podcastsFound == maxNumber) {
                return;
            }
        }
    }
    /**
     * Searches the playlist list based on filters
     * @param command type SearchCommand
     */
    public void foundPlaylistName(final SearchCommand command) {
        StatisticsData statisticsData = StatisticsData.getInstance();
        boolean validCandidate;
        int playlistsFound = 0;
        for (Playlist currPlaylist : statisticsData.getAllPlaylists()) {
            if (currPlaylist.getVisibility().equals("public")) {
                validCandidate = true;
                if (command.getFilters().getName() != null) {
                    if (!currPlaylist.getName().startsWith(command.getFilters().getName())) {
                        validCandidate = false;
                    }
                }
                if (command.getFilters().getOwner() != null) {
                    if (!currPlaylist.getOwner().equals(command.getFilters().getOwner())) {
                        validCandidate = false;
                    }
                }
                if (validCandidate) {
                    results.add(currPlaylist.getName());
                    playlistsFound++;
                }
            } else if (currPlaylist.getOwner().equals(command.getUsername())) {
                validCandidate = true;
                if (command.getFilters().getName() != null) {
                    if (!currPlaylist.getName().equals(command.getFilters().getName())) {
                        validCandidate = false;
                    }
                }
                if (command.getFilters().getOwner() != null) {
                    if (!currPlaylist.getOwner().equals(command.getFilters().getOwner())) {
                        validCandidate = false;
                    }
                }
                if (validCandidate) {
                    results.add(currPlaylist.getName());
                    playlistsFound++;
                }
            }
        }

    }
    private void foundArtistName(final SearchCommand command) {
        StatisticsData statisticsData = StatisticsData.getInstance();
        boolean validCandidate;
        int artistsFound = 0;

        for (StoreUsers currUser : statisticsData.getAllUsers()) {
            validCandidate = true;
            if (command.getFilters().getName() != null) {
                if (currUser.getArtist() == null) {
                    validCandidate = false;
                }
                if (!currUser.getUsername().startsWith(command.getFilters().getName())) {
                    validCandidate = false;
                }
            }
            if (validCandidate) {
                results.add(currUser.getUsername());
                artistsFound++;
            }
        }

    }
    private void foundHostName(final SearchCommand command) {
        StatisticsData statisticsData = StatisticsData.getInstance();
        boolean validCandidate;
        int hostsFound = 0;
        for (StoreUsers currUser : statisticsData.getAllUsers()) {
            validCandidate = true;
            if (command.getFilters().getName() != null) {
                if (currUser.getHost() == null) {
                    validCandidate = false;
                }
                if (!currUser.getUsername().startsWith(command.getFilters().getName())) {
                    validCandidate = false;
                }
            }
            if (validCandidate) {
                results.add(currUser.getUsername());
                hostsFound++;
            }
        }
    }
    public void foundAlbumName(SearchCommand command) {
        StatisticsData statisticsData = StatisticsData.getInstance();
        int albumsFound = 0;
        for (Album currAlbum : statisticsData.getAllAlbums()) {

            boolean validCandidate = true;
            if (command.getFilters().getName() != null) {
                if (!currAlbum.getName().startsWith(command.getFilters().getName())) {
                    validCandidate = false;
                }
            }
            if (command.getFilters().getOwner() != null) {
                if (!currAlbum.getOwner().startsWith(command.getFilters().getOwner())) {
                    validCandidate = false;
                }
            }
            if (command.getFilters().getDescription() != null) {
                if (!currAlbum.getDescription().startsWith(command.getFilters().getDescription())) {
                    validCandidate = false;
                }
            }
            if (validCandidate) {
                results.add(currAlbum.getName());
                albumsFound++;
            }
            if (albumsFound == maxNumber) {
                return;
            }
        }
    }

    /**
     * Decides the search type
     * @param command of type SearchCommand
     */
    public void decideSearchType(final SearchCommand command) {

        switch (command.getType()) {
            case "song" -> foundSongName(command);
            case "podcast" -> foundPodcastName(command);
            case "playlist" -> foundPlaylistName(command);
            case "artist" -> foundArtistName(command);
            case "album" -> foundAlbumName(command);
            case "host" -> foundHostName(command);
        }
    }

    /**
     * Construct the search message
     */
    @Override
    public void doMessage() {
        String message = "Search returned " + results.size() + " results";
        super.message = message;
    }
}
