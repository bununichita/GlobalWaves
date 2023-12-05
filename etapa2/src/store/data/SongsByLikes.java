package store.data;

import fileio.input.SongInput;

import java.util.List;

public class SongsByLikes {
    private int likeCount;
    private SongInput song;

    public SongsByLikes(final SongInput song) {
        this.song = song;
        this.likeCount = 0;
    }

    public SongsByLikes() {
    }

    public final int getLikeCount() {
        return likeCount;
    }

    public final void setLikeCount(final int likeCount) {
        this.likeCount = likeCount;
    }

    public final SongInput getSong() {
        return song;
    }

    public final void setSong(final SongInput song) {
        this.song = song;
    }

    /**
     * Populate the global list of songs, initialize every song with 0 likes
     * @param newList list to be populated
     * @param songList list of songs
     */
    public void initSongByLikeList(final List<SongsByLikes> newList,
                                   final List<SongInput> songList) {
        for (SongInput currSong : songList) {
            newList.add(new SongsByLikes(currSong));
        }
    }
}
