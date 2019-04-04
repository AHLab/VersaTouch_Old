package sample;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application
{

    @Override
    public void start(Stage stage) {

         String srcFile = "/Users/thisum/Desktop/test.txt";
         String desFile = "/Users/thisum/Dropbox/DEMO/test.txt";

//    FileTransfer fileTransfer = new FileTransfer();
//    fileTransfer.copyPaste(srcFile, desFile);
//    fileTransfer.cutPaste("/Users/thisum/Desktop/des_1.jpg", "/Users/thisum/Desktop/temp/des_1.jpg");


//        try
//        {
//
//
//
//        }
//            Synthesizer midiSynth = MidiSystem.getSynthesizer();
//            midiSynth.open();
//
//            //get and load default instrument and channel lists
//            Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
//            MidiChannel[] mChannels = midiSynth.getChannels();
//
//            midiSynth.loadInstrument(instr[0]);
//            mChannels[0].noteOn(60, 100);
//            try { Thread.sleep(700); // wait time in milliseconds to control duration
//            } catch( InterruptedException e ) { }
//            mChannels[0].noteOn(62, 100);
//            try { Thread.sleep(700); // wait time in milliseconds to control duration
//            } catch( InterruptedException e ) { }
//            mChannels[0].noteOn(64, 100);
//            try { Thread.sleep(700); // wait time in milliseconds to control duration
//            } catch( InterruptedException e ) { }
//            mChannels[0].noteOn(65, 100);
//            try { Thread.sleep(700); // wait time in milliseconds to control duration
//            } catch( InterruptedException e ) { }
//            mChannels[0].noteOn(67, 100);
//            try { Thread.sleep(700); // wait time in milliseconds to control duration
//            } catch( InterruptedException e ) { }
//            mChannels[0].noteOn(69, 100);
//            try { Thread.sleep(700); // wait time in milliseconds to control duration
//            } catch( InterruptedException e ) { }
//            mChannels[0].noteOn(71, 100);
//            try { Thread.sleep(700); // wait time in milliseconds to control duration
//            } catch( InterruptedException e ) { }
//            mChannels[0].noteOn(72, 100);
//
//        }
//        catch( Exception e )
//        {
//
//        }


//        List<Integer> xvals = new ArrayList<>(10);
//        List<Integer> yvals = new ArrayList<>();
//        xvals.add(1);
//        xvals.add(1);
//        xvals.add(1);
//        xvals.add(1);
//
//        yvals.add(2);
//        yvals.add(2);
//        yvals.add(2);
//        yvals.add(2);
//
//        int x = (int)xvals.stream().mapToInt(Integer::intValue).average().getAsDouble();
//        int y = (int)yvals.stream().mapToInt(Integer::intValue).average().getAsDouble();


//        SimpleRotations root = new SimpleRotations();
        ImageMapper root = new ImageMapper();
//        root.start();
////
//        Scanner scanner = new Scanner(System.in);
//        int input = 0;
//        while(true)
//        {
//            input = scanner.nextInt();
//            root.playNote(input);
//        }

//        Scene scene = new Scene(pane, 1700, 900);
//
//        //Setting camera
//        PerspectiveCamera camera = new PerspectiveCamera(false);
//        camera.setTranslateX(0);
//        camera.setTranslateY(0);
//        camera.setTranslateZ(0);
//        scene.setCamera(camera);
//
//        //Setting title to the Stage
//        stage.setTitle("Object Rotation");
//
//        //Adding scene to the stage
//        stage.setScene(scene);
//
//        //Displaying the contents of the stage
//        stage.show();

    }


    public static void main(String args[]){
        launch(args);
    }

}
