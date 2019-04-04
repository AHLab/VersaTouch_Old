package sample;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by Shi Yilei
 */
public class GUIPlot
{

    private final int FRAME_SIZE_X = 1300;
    private final int FRAME_SIZE_Y = 700;
    private final int CANVAS_SIZE_X = 600;
    private final int CANVAS_SIZE_Y = 500;

    private Frame test_frame, study_frame, study_setup_frame;
    private plotCanvas ps;
    private trackPosition tp;
    private Button b_calibrate, start, stop, test, experiment, localization_study_start, force_study_start;
    public Label instruction, name_label, age_label, gender_label, material, layout;
    public TextField name_tf, age_tf, gender_tf, material_tf, layout_tf;
    private int[] POSITION;
    private int[] sensor1, sensor2, sensor3;
    private int[] PEAK1, PEAK2, PEAK3;
    private int[] DEV1, DEV2, DEV3;
    private int[] ARR_POINT;
    private boolean USE_ARR = false, USE_PEAK = false, USE_DEV = false;

    private int offset = 50;

    public Label command;
    public Button back, next,demo1, demo2, demo3, demo4;
    public boolean startloop = false;
    public Color color = Color.RED;
    public int pressForce = 0;
    public boolean study2 = false;


    public GUIPlot() throws Exception{
        init();
    }

    private void init() throws Exception{

        /*** Frame for study UI ***/
        study_frame = new Frame("Study");
        study_frame.setLocation(50,50);
        study_frame.setSize(800,800);
        study_frame.setLayout(null);

        back = new Button("back");
        next = new Button("next");
        command = new Label("Position");
        back.setSize(100,30);
        next.setSize(100,30);
        command.setSize(300,300);
        back.setLocation(200, 735);
        next.setLocation(500,735);
        command.setLocation(250,250);
        command.setFont(new Font("Default",Font.BOLD,20));

        study_frame.add(back);
        study_frame.add(next);
        study_frame.add(command);

        /*** Frame for study set up ***/
        study_setup_frame = new Frame("Subject Info");
        study_setup_frame.setSize(300,420);
        study_setup_frame.setLocation(300,300);
        study_setup_frame.setLayout(null);

        name_label = new Label("Name:");
        age_label = new Label("Age");
        gender_label = new Label("Gender");
        material = new Label("Material");
        layout = new Label("Layout");
        name_tf = new TextField();
        age_tf = new TextField();
        gender_tf = new TextField();
        material_tf = new TextField();
        layout_tf = new TextField();
        localization_study_start = new Button("Localization");
        force_study_start = new Button("Force touch");

        name_label.setSize(100, 30);
        age_label.setSize(100,30);
        gender_label.setSize(100,30);
        material.setSize(100,30);
        layout.setSize(100,30);
        name_tf.setSize(100,30);
        age_tf.setSize(100,30);
        gender_tf.setSize(100,30);
        material_tf.setSize(100,30);
        layout_tf.setSize(100,30);
        localization_study_start.setSize(100, 30);
        force_study_start.setSize(100, 30);

        name_label.setLocation(50,60);
        age_label.setLocation(50, 120);
        gender_label.setLocation(50, 180);
        material.setLocation(50, 240);
        layout.setLocation(50, 300);
        name_tf.setLocation(175,60);
        age_tf.setLocation(175,120);
        gender_tf.setLocation(175,180);
        material_tf.setLocation(175,240);
        layout_tf.setLocation(175,300);
        localization_study_start.setLocation(25, 360);
        force_study_start.setLocation(175, 360);

        study_setup_frame.add(name_label);
        study_setup_frame.add(name_tf);
        study_setup_frame.add(age_label);
        study_setup_frame.add(age_tf);
        study_setup_frame.add(gender_label);
        study_setup_frame.add(gender_tf);
        study_setup_frame.add(material);
        study_setup_frame.add(material_tf);
        study_setup_frame.add(layout);
        study_setup_frame.add(layout_tf);
        study_setup_frame.add(localization_study_start);
        study_setup_frame.add(force_study_start);

        /*** Frame for developing UI ***/
        test_frame = new Frame("UniTouch_GUI");
        test_frame.setLocation(0, 0);
        test_frame.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
        test_frame.setLayout(null);

        b_calibrate = new Button("Calibrate");
        b_calibrate.setSize(100, 30);
        b_calibrate.setLocation(50,580);

        instruction = new Label("Calibration off");
        instruction.setSize(100,30);
        instruction.setLocation(50, 630);

        start = new Button("Start");
        start.setSize(100,30);
        start.setLocation(180, 580);

        stop = new Button("Stop");
        stop.setSize(100,30);
        stop.setLocation(280, 580);

        test = new Button("test");
        test.setSize(100,30);
        test.setLocation(380, 580);

        experiment = new Button("Experiment");
        experiment.setSize(100,30);
        experiment.setLocation(480,580);

        //Demo
        demo1 = new Button("Demo1");
        demo1.setSize(100,30);
        demo1.setLocation(180,620);

        demo2 = new Button("Demo2");
        demo2.setSize(100,30);
        demo2.setLocation(280,620);

        demo3 = new Button("Demo3");
        demo3.setSize(100,30);
        demo3.setLocation(380,620);

        demo4 = new Button("Demo4");
        demo4.setSize(100,30);
        demo4.setLocation(480,620);

        ps = new plotCanvas();
        ps.setLocation(0,25);

        tp = new trackPosition();
        tp.setLocation(650, 25);

        test_frame.add(ps);
        test_frame.add(tp);
        test_frame.add(b_calibrate);
        test_frame.add(start);
        test_frame.add(stop);
        test_frame.add(test);
        test_frame.add(experiment);
        test_frame.add(instruction);
        test_frame.add(demo1);
        test_frame.add(demo2);
        test_frame.add(demo3);
        test_frame.add(demo4);
        Event();
        test_frame.setVisible(true);
    }

    public Frame getBaseFrame()
    {
        return test_frame;
    }

    /*** Set up the canvas ***/
    class plotCanvas extends Canvas{
        public plotCanvas(){
            setBackground(Color.GRAY);
            setSize(CANVAS_SIZE_X, CANVAS_SIZE_Y);
        }

        /*** Plot the data ***/
        public void paint(Graphics graphics) {

            /*** Set up the coordinates ***/
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawLine(0, CANVAS_SIZE_Y / 2, CANVAS_SIZE_X, CANVAS_SIZE_Y / 2);
            graphics2D.drawLine(CANVAS_SIZE_X / 2, 0, CANVAS_SIZE_X / 2, CANVAS_SIZE_Y);

            if (sensor1 != null && sensor2 != null && sensor3 != null) {

                /*** Plot sensor1 ***/
                graphics2D.setColor(Color.RED);
                sensor1 = mapping(sensor1);
                for (int i = 0; i < sensor1.length - 1; i++) {
                    graphics2D.drawLine(i+offset, sensor1[i], i + offset + 1, sensor1[i+1]);
                }

                /*** Plot sensor2 ***/
                graphics2D.setColor(Color.GREEN);
                sensor2 = mapping(sensor2);
                for (int i = 0; i < sensor2.length - 1; i++) {
                    graphics2D.drawLine(i+offset, sensor2[i], i + offset + 1, sensor2[i+1]);
                }

                /*** Plot sensor3 ***/
                graphics2D.setColor(Color.BLUE);
                sensor3 = mapping(sensor3);
                for (int i = 0; i < sensor3.length - 1; i++) {
                    graphics2D.drawLine(i+offset, sensor3[i], i + offset + 1, sensor3[i+1]);
                }

                /*** Check whether to plot the peaks ***/
                if(USE_PEAK){
                    graphics2D.setColor(Color.RED);
                    for (int i = 0; i < PEAK1.length; i++){
                        int x = PEAK1[i] + offset;
                        graphics2D.fillOval(x-5,sensor1[PEAK1[i]]-5,10,10);
                    }

                    graphics2D.setColor(Color.GREEN);
                    for (int i = 0; i < PEAK2.length; i++){
                        int x = PEAK2[i] + offset;
                        graphics2D.fillOval(x-5,sensor2[PEAK2[i]]-5,10,10);
                    }

                    graphics2D.setColor(Color.BLUE);
                    for (int i = 0; i < PEAK3.length; i++){
                        int x = PEAK3[i] + offset;
                        graphics2D.fillOval(x-5,sensor3[PEAK3[i]]-5,10,10);
                    }

                    USE_PEAK = false;
                }

                /*** Check whether to plot the arrival time ***/
                if(USE_ARR){
                    graphics2D.setColor(Color.RED);
                    graphics2D.fillOval(ARR_POINT[0],250,10,10);

                    graphics2D.setColor(Color.GREEN);
                    graphics2D.fillOval(ARR_POINT[1],250,10,10);

                    graphics2D.setColor(Color.BLUE);
                    graphics2D.fillOval(ARR_POINT[2],250,10,10);

                    USE_ARR = false;
                }

                if(USE_DEV){
                    graphics2D.setColor(Color.YELLOW);
                    DEV1 = mapping(DEV1);
                    for (int i = 0; i < DEV1.length - 1; i++) {
                        graphics2D.drawLine(2*i+offset, DEV1[i], 2*i + offset + 2, DEV1[i+1]);
                    }

                    graphics2D.setColor(Color.ORANGE);
                    DEV2 = mapping(DEV2);
                    for (int i = 0; i < DEV2.length - 1; i++) {
                        graphics2D.drawLine(2*i+offset, DEV2[i], 2*i + offset + 2, DEV2[i+1]);
                    }

                    graphics2D.setColor(Color.BLACK);
                    DEV3 = mapping(DEV3);
                    for (int i = 0; i < DEV3.length - 1; i++) {
                        graphics2D.drawLine(2*i+offset, DEV3[i], 2*i + offset + 2, DEV3[i+1]);
                    }
                }
            }
        }
    }



    class trackPosition extends Canvas{
        public trackPosition(){
            setBackground(Color.GRAY);
            setSize(650, 650);
        }

        public void paint(Graphics graphics) {

            /*** Set up the coordinates ***/
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillRect(25,25,600,600);
//            graphics2D.setColor(Color.RED);
//            graphics2D.fillOval(320 + DataRead.var_list.s1[0], 320 - DataRead.var_list.s1[1], 10,10 );
//            graphics2D.setColor(Color.GREEN);
//            graphics2D.fillOval(320 + DataRead.var_list.s2[0], 320 - DataRead.var_list.s2[1], 10,10 );
//            graphics2D.setColor(Color.BLUE);
//            graphics2D.fillOval(320 + DataRead.var_list.s3[0], 320 - DataRead.var_list.s3[1], 10,10 );
//            graphics2D.setColor(Color.WHITE);
//            graphics2D.fillOval(320, 320, 10,10);


//            graphics2D.setColor(Color.WHITE);
//            for(int i = 50; i <= 600; i = i + 25 ){
//                graphics2D.drawLine(i,25,i,625);
//            }
//            for(int i = 50; i <= 600; i = i + 25 ){
//                graphics2D.drawLine(25,i,625,i);
//            }

            graphics2D.setColor(color);
//            if (POSITION != null) {
//                int x = 320 + 2*POSITION[0] - DataRead.var_list.force_level/16;
//                int y = 320 - 2*POSITION[1] - DataRead.var_list.force_level/16;
//                graphics2D.fillOval(x, y , DataRead.var_list.force_level/8, DataRead.var_list.force_level/8);
////                graphics2D.fillRect(0,0,10,pressForce);
//            }
        }
    }

    /*** Methods used in the main thread to plot data***/
    public void plotData(int[] data1, int[] data2, int[] data3){
        sensor1 = data1;
        sensor2 = data2;
        sensor3 = data3;
        ps.repaint();
    }


    public void plotData_with_peak(int[] data1, int[] data2, int[] data3, int[] peak1, int[] peak2, int[] peak3){
        USE_PEAK = true;

        sensor1 = data1;
        sensor2 = data2;
        sensor3 = data3;
        PEAK1 = peak1;
        PEAK2 = peak2;
        PEAK3 = peak3;
        ps.repaint();
    }

    public void plot_track(int[] position){
        if(position != null) {
            POSITION = position;
            tp.repaint();
        }
    }

    public void clear_paint(){
        POSITION = null;
        tp.repaint();
    }

    /*** Mapping the coordinates to the canvas ***/
    public int[] mapping(int[] data){

        int max = data[0], min = data[0];

        for(int i : data){
            max = Math.max(max, i);
            min = Math.min(min, i);
        }

        for(int i = 0; i < data.length; i++){
            data[i] = 500 - (data[i]/2 - 700);
        }
        return data;
    }

    /*** Initialise the listeners ***/
    private void Event() throws Exception{
        //Listener of the window
        test_frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                //super.windowClosing(e);
                System.exit(0);
            }
        });

        study_setup_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataRead.var_list.study_started = false;
//                DataRead.var_list.TASK = DataRead.var_list.DEVELOPMENT;
                study_setup_frame.setVisible(false);
                test_frame.setVisible(true);
            }
        });

        study_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataRead.var_list.study_started = false;
                study_frame.setVisible(false);
                study_setup_frame.setVisible(true);
            }
        });

        b_calibrate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                // TODO Auto-generated method stub
//                try {
//                    DataRead.var_list.calli_con = true;
//                }catch (Exception exception){
//                    System.out.println(e.toString());
//                }
            }
        });


        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startloop = true;
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startloop = false;
            }
        });

        experiment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test_frame.setVisible(false);
                study_setup_frame.setVisible(true);
            }
        });

        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                int[] peaks = null;
//                try {
//                    peaks = DataRead.find_arrival_time(1);
//                }catch (Exception exception){
//                    System.out.println(exception.toString());
//                }
//
//                if(peaks != null) {
//                    DataRead.checkLocation(peaks);
//                }
            }
        });

        localization_study_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


//                DataRead.var_list.NAME = name_tf.getText();
//                DataRead.var_list.AGE = age_tf.getText();
//                DataRead.var_list.GENDER = gender_tf.getText();
//
//                DataRead.var_list.TASK = DataRead.var_list.study1;
//                DataRead.var_list.study_started = true;
//                DataRead.var_list.next_frame = false;
//
//                study_setup_frame.setVisible(false);
//                study_frame.setVisible(true);
            }
        });

        force_study_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                DataRead.var_list.NAME = name_tf.getText();
//                DataRead.var_list.AGE = age_tf.getText();
//                DataRead.var_list.GENDER = gender_tf.getText();
//
//                DataRead.var_list.TASK = DataRead.var_list.study2;
//                DataRead.var_list.study_started = true;
//                DataRead.var_list.next_frame = false;
//
//                study_setup_frame.setVisible(false);
//                study_frame.setVisible(true);
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(DataRead.var_list.study_data.size());
//                DataRead.var_list.study_i -= 1;
//                DataRead.var_list.study_data.remove(DataRead.var_list.study_data.size()-1);
//                DataRead.var_list.next_frame = true;
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                study2 = !study2;
//                DataRead.var_list.frame_count++;
//                if(DataRead.var_list.next_frame){
//                    DataRead.var_list.next_frame = false;
//                }else {
//                    DataRead.var_list.next_frame = true;
//                }
            }
        });

        demo1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                DataRead.var_list.TASK = "Demo1";
            }
        });
        demo2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                DataRead.var_list.TASK = "Demo2";
            }
        });
        demo3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                DataRead.var_list.TASK = "Demo3";
            }
        });
        demo4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                DataRead.var_list.TASK = "Demo4";
            }
        });

    }

}
