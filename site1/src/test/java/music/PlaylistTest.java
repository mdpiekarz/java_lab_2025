package music;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    @Test
    void isEmpty() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }

    @Test
    void hasSingle() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Czesław Niemen", "Wspomnienie", 220));
        assertEquals(1, playlist.size());
    }

    @Test
    void hasExact() {
        Playlist playlist = new Playlist();
        Song inputSong = new Song("Czesław Niemen", "Wspomnienie", 220);
        Song validationSong = new Song("Czesław Niemen", "Wspomnienie", 220);
        playlist.add(inputSong);
        assertEquals(validationSong, playlist.get(0));
    }

    @Test
    void atSecond() {
        Playlist playlist = new Playlist();
        playlist.addAll(List.of(
                new Song("Czesław Niemen", "Wspomnienie", 220),
                new Song("Jerzy Połomski", "Jesienne róże", 205),
                new Song("Marek Grechuta", "Korowód", 230)
        ));
        assertEquals(playlist.get(1), playlist.atSecond(300));
    }

    @Test
    void timeExceeded() {
        Playlist playlist = new Playlist();
        playlist.addAll(List.of(
                new Song("Czesław Niemen", "Wspomnienie", 220),
                new Song("Jerzy Połomski", "Jesienne róże", 205),
                new Song("Marek Grechuta", "Korowód", 230)
        ));
        assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(1000));
    }

    String outOfBoundsCommon(int time) {
        Playlist playlist = new Playlist();
        playlist.addAll(List.of(
                new Song("Czesław Niemen", "Wspomnienie", 220),
                new Song("Jerzy Połomski", "Jesienne róże", 205),
                new Song("Marek Grechuta", "Korowód", 230)
        ));
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(time));
        return exception.getMessage();
    }

    @Test
    void timeExceeded2() {
        assertEquals("Playlist exceeded!", outOfBoundsCommon(1000));
    }

    @Test
    void timeNegative() {
        assertEquals("Negative time!", outOfBoundsCommon(-1000));
    }
}
