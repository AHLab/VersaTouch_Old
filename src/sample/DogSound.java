package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Created by thisum_kankanamge on 19/9/18.
 */
public class DogSound implements SoundPlayable
{
    private Media highNote;
    private Media lowNote;

    public DogSound(String highNote, String lowNote)
    {
        this.highNote = new Media(new File(highNote).toURI().toString());
        this.lowNote = new Media(new File(lowNote).toURI().toString());
    }


    @Override
    public void playHighSound()
    {
        MediaPlayer mediaPlayer = new MediaPlayer(highNote);
        mediaPlayer.play();
    }

    @Override
    public void playLowSound()
    {
        MediaPlayer mediaPlayer = new MediaPlayer(lowNote);
        mediaPlayer.play();
    }
}
