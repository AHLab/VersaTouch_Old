package sample;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.InputStream;

public class Piano
{
    private String sound = "";

    public Piano(String sound)
    {
        this.sound = sound;
    }

    public void play()
    {
        InputStream inputStream;
        try {
            inputStream = getClass().getResourceAsStream(this.sound);
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
