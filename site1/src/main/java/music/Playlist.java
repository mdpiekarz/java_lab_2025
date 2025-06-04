package music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int second) {
        if(second < 0)
            throw new IndexOutOfBoundsException("Negative time!");

        int totalSeconds = 0;
        for (Song song : this) {
            int songLength = song.length();
            if (second >= totalSeconds && second < totalSeconds + songLength) {
                return song;
            }
            totalSeconds += songLength;
        }
        //return null;
        throw new IndexOutOfBoundsException("Playlist exceeded!");
    }
}
