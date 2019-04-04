package sample;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.io.InputStream;

public class Piano
{
    private String sound = "";

    public Piano(String sound)
    {
        this.sound = sound;
    }

    public void playLow()
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sound));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = ( FloatControl ) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-4.0f); // Reduce volume by 10 decibels.
            clip.start();
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }
    }

    public void playHigh()
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sound));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = ( FloatControl ) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(6.0f); // Reduce volume by 10 decibels.
            clip.start();
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }
    }
}
