package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by thisum_kankanamge on 18/9/18.
 */
public class ImageMapper extends Application implements EventHandler<MouseEvent>, SerialDataListener
{
    private static int DOG_IMG1 = 100;
    private static int KEY_IMG2 = 200;
    private List<String> dogImages = new ArrayList<>();
    private List<String> keyboardImages = new ArrayList<>();
    private List<SoundPlayable> fluteSounds = new ArrayList<>();
    private List<SoundPlayable> pianoSounds = new ArrayList<>();
    private List<CalibPoint> leftCalibPoints = new ArrayList<>();
    private List<CalibPoint> rightCalibPoints = new ArrayList<>();

    private ImageView dogImg;
    private ImageView keyboardImg;
    private ImageView calibrateImg;
    private Label statusLabel;
    private Label outputLabel;
    private Button nextCalibBtn;
    private TextArea resultArea;
    private Pane basePane;
    private Button closeBtn;

    private int selectedImgType = 0;
    private int currentCalIndex = 0;
    private boolean listenSerial = false;
    private boolean startThread = true;
    private Object lock = new Object();
    private int requiredCount = 0;
    private int countedPoints = 0;
    private int leftPressure, rightPressure = 0;
    private int[] releasePosition = {1000,1000};
    private MidiChannel selectedLeftChannel;
    private MidiChannel selectedRightChannel;
    private byte hand, right = 113, left = 112;
    private DataRead dataRead;

    public void setImgFiles()
    {
        dogImages.add("file:img/dog_cal.png");
        dogImages.add("file:img/dog_cal_1.png");
        dogImages.add("file:img/dog_cal.png");

        keyboardImages.add("file:img/keyboard_cal.png");
        keyboardImages.add("file:img/keyboard_cal_1.png");
        keyboardImages.add("file:img/keyboard_cal_2.png");
        keyboardImages.add("file:img/keyboard_cal_3.png");
        keyboardImages.add("file:img/keyboard_cal_4.png");
        keyboardImages.add("file:img/keyboard_cal_5.png");
        keyboardImages.add("file:img/keyboard_cal_6.png");
        keyboardImages.add("file:img/keyboard_cal_7.png");
        keyboardImages.add("file:img/keyboard_cal.png");
        keyboardImages.add("file:img/keyboard_cal_1.png");
        keyboardImages.add("file:img/keyboard_cal_2.png");
        keyboardImages.add("file:img/keyboard_cal_3.png");
        keyboardImages.add("file:img/keyboard_cal_4.png");
        keyboardImages.add("file:img/keyboard_cal_5.png");
        keyboardImages.add("file:img/keyboard_cal_6.png");
        keyboardImages.add("file:img/keyboard_cal_7.png");
        keyboardImages.add("file:img/keyboard_cal.png");

    }

    private void initMidiFiles()
    {
        try
        {
            Synthesizer midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();

            //get and load default instrument and channel lists
            Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
            MidiChannel[] mChannels = midiSynth.getChannels();


            midiSynth.loadInstrument(instr[73]);
            selectedRightChannel = mChannels[1];

            midiSynth.loadInstrument(instr[0]);
            selectedLeftChannel = mChannels[0];
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    private void setSoundFiles()
    {
        pianoSounds.add(new PianoSound(72, 60, selectedLeftChannel));
        pianoSounds.add(new PianoSound(74, 62, selectedLeftChannel));
        pianoSounds.add(new PianoSound(76, 64, selectedLeftChannel));
        pianoSounds.add(new PianoSound(77, 65, selectedLeftChannel));
        pianoSounds.add(new PianoSound(79, 67, selectedLeftChannel));
        pianoSounds.add(new PianoSound(81, 69, selectedLeftChannel));
        pianoSounds.add(new PianoSound(83, 71, selectedLeftChannel));

        fluteSounds.add(new PianoSound(72, 60, selectedRightChannel));
        fluteSounds.add(new PianoSound(74, 62, selectedRightChannel));
        fluteSounds.add(new PianoSound(76, 64, selectedRightChannel));
        fluteSounds.add(new PianoSound(77, 65, selectedRightChannel));
        fluteSounds.add(new PianoSound(79, 67, selectedRightChannel));
        fluteSounds.add(new PianoSound(81, 69, selectedRightChannel));
        fluteSounds.add(new PianoSound(83, 71, selectedRightChannel));

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        basePane = new Pane();
        setImgFiles();
        initMidiFiles();
        setSoundFiles();
        setInitialScreen();
        Scene scene = new Scene(basePane, 1200, 900);

        //Setting camera
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        scene.setCamera(camera);

        //Setting title to the Stage
        primaryStage.setTitle("Object Mapper");

        //Adding scene to the stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();

        dataRead = new DataRead( this);
    }

    public void setInitialScreen()
    {
        if(basePane.getChildren().size() > 0)
        {
            basePane.getChildren().remove(0);
        }
        GridPane imagesPane = new GridPane();
        dogImg = new ImageView(new Image("file:img/dog_grid.jpg"));
        dogImg.addEventFilter(MouseEvent.MOUSE_CLICKED, this);

        keyboardImg = new ImageView(new Image("file:img/keyboard_grid.jpg"));
        keyboardImg.addEventFilter(MouseEvent.MOUSE_CLICKED, this);

        ImageView img3 = new ImageView(new Image("file:img/car_grid.jpeg"));
        ImageView img4 = new ImageView(new Image("file:img/guitar.jpeg"));
        ImageView img5 = new ImageView(new Image("file:img/horse_grid.jpeg"));
        ImageView img6 = new ImageView(new Image("file:img/human_grid.jpeg"));
        ImageView img7 = new ImageView(new Image("file:img/plane_grid.jpeg"));
        ImageView img8 = new ImageView(new Image("file:img/singer_grid.jpeg"));
        ImageView img9 = new ImageView(new Image("file:img/tree_grid.jpeg"));

        imagesPane.add(dogImg, 0, 0);
        imagesPane.add(keyboardImg, 1, 0);
        imagesPane.add(img3, 2, 0);
        imagesPane.add(img4, 0, 1);
        imagesPane.add(img5, 1, 1);
        imagesPane.add(img6, 2, 1);
        imagesPane.add(img7, 0, 2);
        imagesPane.add(img8, 1, 2);
        imagesPane.add(img9, 2, 2);
        imagesPane.setPadding(new Insets(15, 0 ,0, 30));

        basePane.getChildren().add(imagesPane);
    }

    private void setImagePanel()
    {
        basePane.getChildren().remove(0);

        BorderPane borderPane = new BorderPane();
        closeBtn = new Button("Close");
        statusLabel = new Label("Calibrating...");
        outputLabel = new Label("");

        nextCalibBtn = new Button("Calibrate >");

        resultArea = new TextArea();
        resultArea.setStyle("-fx-background-color: #000000;");
        resultArea.setEditable(false);
        resultArea.appendText("Calibrate the left hand\n\n");
        calibrateImg = new ImageView();
        GridPane gridPane = new GridPane();
        gridPane.add(calibrateImg, 0 , 0, 1, 1);
        gridPane.add(resultArea, 1, 0, 1, 1);

        gridPane.add(nextCalibBtn, 0, 3, 1, 1);
        gridPane.setPadding(new Insets(100, 50 ,0, 100));
        gridPane.setVgap(50);
        gridPane.setHgap(50);

        nextCalibBtn.setPrefSize(100, 20);
        HBox bottomHBox = new HBox();
        bottomHBox.setPadding(new Insets(15, 12, 15, 12));
        bottomHBox.setSpacing(10);

        HBox rightButtons = new HBox(closeBtn);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);
        bottomHBox.setPadding(new Insets(150, 50, 0 , 50));
        bottomHBox.getChildren().addAll(nextCalibBtn, rightButtons);

        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));

        hboxTop.getChildren().addAll(statusLabel);

        hand = left;

        nextCalibBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                currentCalIndex++;
                if(currentCalIndex > 0)
                {
                    nextCalibBtn.setText("Next >");
                }

                if(currentCalIndex == 8)
                {
                    resultArea.appendText("\nCalibrate the right hand\n\n");
                    String imgFile = getNextImage(currentCalIndex, selectedImgType);
                    calibrateImg.setImage(new Image(imgFile));
                    hand = right;
                    return;
                }

                if(selectedImgType == KEY_IMG2 && currentCalIndex < (keyboardImages.size() -1))
                {
                    String imgFile = getNextImage(currentCalIndex, selectedImgType);
                    calibrateImg.setImage(new Image(imgFile));
                    resultArea.appendText("Calibrating the " + getPosition(currentCalIndex) + " point\n");
                    setCalibrationValues(getNextSound(currentCalIndex, selectedImgType, hand), imgFile, hand);
                }
                else
                {
                    String imgFile = getNextImage(currentCalIndex, selectedImgType);
                    calibrateImg.setImage(new Image(imgFile));
                    resultArea.appendText("\nCalibration done\n");
                    statusLabel.setText("Calibration done! Let's play...");
                    nextCalibBtn.setText("Done!");
                    nextCalibBtn.setDisable(true);
                    startDataListenThread();
                }
            }
        });

        closeBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                startThread = false;
                currentCalIndex = 0;
                setInitialScreen();
            }
        });

        borderPane.setTop(hboxTop);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(bottomHBox);

        basePane.getChildren().add(borderPane);
    }


    private String getNextImage(int i, int type)
    {
        if(type == DOG_IMG1 )
        {
            return dogImages.get(i);

        }
        else
        {
            return keyboardImages.get(i);
        }
    }

    private SoundPlayable getNextSound(int i, int type, byte hand)
    {
        if( hand == left )
        {
            return pianoSounds.get(i-1);

        }
        else
        {
            return fluteSounds.get((i-1)%8);
        }
    }

    @Override
    public void handle(MouseEvent event)
    {
        if(event.getSource() == dogImg )
        {
            selectedImgType = DOG_IMG1;
            setImagePanel();
            calibrateImg.setImage(new Image(getNextImage(currentCalIndex, selectedImgType)));
        }
        else if(event.getSource() == keyboardImg )
        {
            selectedImgType = KEY_IMG2;
            setImagePanel();
            calibrateImg.setImage(new Image(getNextImage(currentCalIndex, selectedImgType)));
        }
    }

    private void setCalibrationValues( SoundPlayable soundFile, String imgFile, final byte hand )
    {
        requiredCount = 50;
        nextCalibBtn.setDisable(true);
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                List<Integer> xvals = new ArrayList<>(requiredCount);
                List<Integer> yvals = new ArrayList<>(requiredCount);
                int []coordinates;

                while(requiredCount > 0)
                {
                    try
                    {
                        coordinates = dataRead.findPosition(hand);
                        if(coordinates[2] <= 0)
                        {
                            continue;
                        }
                        if(requiredCount == 40 || requiredCount == 30 || requiredCount == 20 || requiredCount ==10)
                        {
                            try { Thread.sleep(50); // wait time in milliseconds to control duration
                            } catch( InterruptedException e ) { }
                        }
                        leftPressure = coordinates[2];
                        if(!Arrays.equals(releasePosition, coordinates))
                        {
                            System.out.println(requiredCount);
                            xvals.add(coordinates[0]);
                            yvals.add(coordinates[1]);
                            requiredCount--;
                        }
                    }
                    catch( Exception e )
                    {
                        e.printStackTrace();
                    }
                }
                int x = (int)xvals.stream().mapToInt(Integer::intValue).average().getAsDouble();
                int y = (int)yvals.stream().mapToInt(Integer::intValue).average().getAsDouble();

                CalibPoint calibPoint = new CalibPoint(x,y, soundFile, imgFile);
                if(hand == left)
                    leftCalibPoints.add(calibPoint);
                else
                    rightCalibPoints.add(calibPoint);

                resultArea.appendText("calibration done... \n");
                nextCalibBtn.setDisable(false);

            }
        });
        t.start();
    }

    private void startDataListenThread()
    {
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
//                try
//                {
//                    int k = 30;
//                    while (k>0)
//                    {
//                        DataRead.find_final_position();
//                        k--;
//                    }
//                    Thread.sleep(1000);
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
                CalibPoint leftTouch = new CalibPoint(0, 0, 0);
                CalibPoint rightTouch = new CalibPoint(0, 0, 0);
                int lastX = 0, lastY = 0;
                int leftCoordinates[] = {};
                int rightCoordinates[] = {};
                List<Integer> xValues = new ArrayList<>(10);
                List<Integer> yValues = new ArrayList<>(10);
                List<Integer> pressureVals = new ArrayList<>(10);
                try
                {
                    while( startThread )
                    {
                        leftCoordinates = dataRead.findPosition(left);
                        rightCoordinates = dataRead.findPosition(right);
                        if(leftCoordinates[2] <= 0)
                        {
                            leftTouch.setX(1000);
                            leftTouch.setY(1000);
                            leftCoordinates[0] = 1000;
                            leftCoordinates[1] = 1000;
                        }

                        if(rightCoordinates[2] <= 0)
                        {
                            rightTouch.setX(1000);
                            rightTouch.setY(1000);
                            rightCoordinates[0] = 1000;
                            rightCoordinates[1] = 1000;
                        }

                        leftPressure = leftCoordinates[2];
                        rightPressure = rightCoordinates[2];


//                        System.out.println(leftPressure);
                        if( leftCoordinates[0] < 1000 && leftCoordinates[1] < 1000 )
                        {
                            System.out.println("point: x - " + leftCoordinates[0] + "    y - " + leftCoordinates[1] );
//                            xValues.add(leftCoordinates[0]);
//                            yValues.add(leftCoordinates[1]);
//                            pressureVals.add(leftPressure);
//                            if(xValues.size() == 5)
//                            {
//                                int x = (int)xValues.stream().mapToInt(Integer::intValue).average().getAsDouble();
//                                int y = (int)yValues.stream().mapToInt(Integer::intValue).average().getAsDouble();
//                                if(lastX == x && lastY == y)
//                                {
//                                    continue;
//                                }
//                                double pr =  pressureVals.stream().mapToInt(Integer::intValue).average().getAsDouble();
//                                CalibPoint newPoint = new CalibPoint(x ,y, pr);
//                                CalibPoint matchPoint = calibPoints.stream()
//                                        .filter(p -> newPoint.equals(p))
//                                        .findAny()
//                                        .orElse(null);
//
//                                if(matchPoint != null)
//                                {
//                                    System.out.println("point: x - " + x + "    y - " + y );
//                                    matchPoint.setPressure(newPoint.getPressure());
//                                    updateLeftUI(matchPoint);
//                                }
//
//                                xValues.clear();
//                                yValues.clear();
//                                pressureVals.clear();
//                            }

                            CalibPoint newPoint = new CalibPoint(leftCoordinates[0] , leftCoordinates[1], leftPressure);
                            if(newPoint.equals(leftTouch))
                            {
                                continue;
                            }
                            CalibPoint matchPoint = leftCalibPoints.stream()
                                    .filter(p -> newPoint.equals(p))
                                    .findAny()
                                    .orElse(null);

                            if(matchPoint != null)
                            {
                                System.out.println("point matched");
                                leftTouch.setX(leftCoordinates[0]);
                                leftTouch.setY(leftCoordinates[1]);
                                newPoint.setSoundFile(matchPoint.getSoundFile());
                                newPoint.setImgFile(matchPoint.getImgFile());
                                updateLeftUI(newPoint);
                            }

                        }



//                        System.out.println(leftPressure);
                        if( rightCoordinates[0] < 1000 && rightCoordinates[1] < 1000 )
                        {
                            System.out.println("point: x - " + rightCoordinates[0] + "    y - " + rightCoordinates[1] );

                            CalibPoint newPoint = new CalibPoint(rightCoordinates[0] , rightCoordinates[1], rightPressure);
                            if(newPoint.equals(rightTouch))
                            {
                                continue;
                            }
                            CalibPoint matchPoint = rightCalibPoints.stream()
                                    .filter(p -> newPoint.equals(p))
                                    .findAny()
                                    .orElse(null);

                            if(matchPoint != null)
                            {
                                System.out.println("point matched");
                                rightTouch.setX(rightCoordinates[0]);
                                rightTouch.setY(rightCoordinates[1]);
                                newPoint.setSoundFile(matchPoint.getSoundFile());
                                newPoint.setImgFile(matchPoint.getImgFile());
                                updateRightUI(newPoint);
                            }

                        }

                    }
                }
                catch( Exception e )
                {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private void updateLeftUI(CalibPoint matchPoint)
    {
//        calibrateImg.setImage(new Image(matchPoint.getImgFile()));
        if(matchPoint.isHighPressure())
        {
            matchPoint.getSoundFile().playHighSound();
        }
        else
        {
            matchPoint.getSoundFile().playLowSound();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(400);
//                    calibrateImg.setImage(new Image(keyboardImages.get(0)));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateRightUI(CalibPoint matchPoint)
    {
//        calibrateImg.setImage(new Image(matchPoint.getImgFile()));
        if(matchPoint.isHighPressure())
        {
            matchPoint.getSoundFile().playHighSound();
        }
        else
        {
            matchPoint.getSoundFile().playLowSound();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(400);
//                    calibrateImg.setImage(new Image(keyboardImages.get(0)));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getPosition(int count)
    {
        switch( count%8 )
        {
            case 1:
                return "first";
            case 2:
                return "second";
            case 3:
                return "third";
            case 4:
                return "fourth";
            case 5:
                return "fifth";
            case 6:
                return "sixth";
            case 7:
                return "seventh";
            case 8:
                return "done calibration";
            default:
                return "";
        }
    }

    @Override
    public void plotRawData(int[][] datas, int[] peaks, int finger)
    {

    }

    @Override
    public void plotTrack()
    {

    }
}
