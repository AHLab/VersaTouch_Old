package sample;

import javax.sound.midi.MidiChannel;

/**
 * Created by thisum_kankanamge on 19/9/18.
 */
public class PianoSound implements SoundPlayable
{
    private final MidiChannel midiChannel;
    private int highNote;
    private int lowNote;

    public PianoSound(int highNote, int lowNote, MidiChannel midiChannel)
    {
        this.highNote = highNote;
        this.lowNote = lowNote;
        this.midiChannel = midiChannel;
    }

    @Override
    public void playHighSound()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                midiChannel.setMono(false);
                midiChannel.noteOn(lowNote, 120);
                midiChannel.controlChange(7, 127);
                try { Thread.sleep(500); // wait time in milliseconds to control duration
                } catch( InterruptedException e ) { }
            }
        }).start();
    }

    @Override
    public void playLowSound()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                midiChannel.setMono(true);
                midiChannel.noteOn(lowNote, 100);
                midiChannel.controlChange(7, 80);
                try { Thread.sleep(100); // wait time in milliseconds to control duration
                } catch( InterruptedException e ) { }
            }
        }).start();
    }
}
