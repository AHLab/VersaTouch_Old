package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Shi Yilei
 */
public class Variables
{

    /*** Data during processing ***/
    public int finger = 1;
    public int[] data_f1, data_f2;
    public int check = 1000000, check1= 0;
    public int[][] rawData;


    /*** Status control ***/
    public final String DEVELOPMENT = "development";
    public boolean study_started = false;
    public boolean next_frame = false;
    public boolean f1 = false, f2 = false, touch1 = false, touch2 = false;
    public boolean[] f = new boolean[2];

    public int[] data_count = new int[2], bd_pre = new int[2], gd_pre = new int[2];

    /***System Configuration***/
//    public int L1 = 220, L2 = 220, L3 = 311;
    public int L1, L2, L3;
    public int D1 = 135, D2 = 135, D3 = 130;
    public int[] s1 = new int[2], s2 = new int[2], s3 = new int[2];
    public Variables() {
        int layout = 1;
        if(layout == 1) {
            L1 = 220;
            L2 = 220;
            L3 = 311; // Layout1 15cm 等腰直角
            s1[0] = -L1/2;
            s2[0] = L1/2;
            s3[0] = L1/2;
            s1[1] = L2/2;
            s2[1] = L2/2;
            s3[1] = -L2/2;
        }
        if(layout == 2) {
            L1 = 220;
            L2 = 220;
            L3 = 220; // Layout2 15cm 锐角
            s1[0] = -L1/2;
            s2[0] = L1/2;
            s3[0] = 0;
            s1[1] = 64;
            s2[1] = 64;
            s3[1] = -128;
        }
        if(layout == 3) {
            L1 = 220;
            L2 = 220;
            L3 = 311; // Layout3 15cm 钝角
            s1[0] = -L1/2;
            s2[0] = L1/2;
            s3[0] = 220;
            s1[1] = 191;
            s2[1] = 191;
            s3[1] = 0;
        }
        if(layout == 4) {
            L1 = 143;
            L2 = 143;
            L3 = 202; // Layout4 10cm 直角
            s1[0] = -L1/2;
            s2[0] = L1/2;
            s3[0] = L1/2;
            s1[1] = L2/2;
            s2[1] = L2/2;
            s3[1] = -L2/2;
        }

    }

    public void changeParameter(int layout) {
        if(layout == 1) {
            L1 = 220;
            L2 = 220;
            L3 = 311; // Layout1 15cm 等腰直角
            s1[0] = -L1/2;
            s2[0] = L1/2;
            s3[0] = L1/2;
            s1[1] = L2/2;
            s2[1] = L2/2;
            s3[1] = -L2/2;
        }
        if(layout == 2) {
            L1 = 220;
            L2 = 220;
            L3 = 220; // Layout2 15cm 锐角
            s1[0] = -L1/2;
            s2[0] = L1/2;
            s3[0] = 0;
            s1[1] = 64;
            s2[1] = 64;
            s3[1] = -128;
        }
        if(layout == 3) {
            L1 = 220;
            L2 = 220;
            L3 = 311; // Layout3 15cm 钝角
            s1[0] = -L1/2;
            s2[0] = L1/2;
            s3[0] = 220;
            s1[1] = 191;
            s2[1] = 191;
            s3[1] = 0;
        }
        if(layout == 4) {
            L1 = 143;
            L2 = 143;
            L3 = 202; // Layout4 10cm 直角
            s1[0] = -L1/2;
            s2[0] = L1/2;
            s3[0] = L1/2;
            s1[1] = L2/2;
            s2[1] = L2/2;
            s3[1] = -L2/2;
        }

    }

    public int force_level_1 = -1, force_level_2 = -1;
    public int[][][] positionArray = new int[2][10][2];
    public int[][] cali_rawdata = new int[3][];
    public boolean feedback1 = false;

    /*** Parameters for calibration ***/

    public static List<String> getPointsCommand()
    {
        List<String> pointsList = new ArrayList<>();
        for(int i=0;i < 25; i++)
        {
            pointsList.add("Point " + (i+1));
        }

        return pointsList;
    }

    public static List<String> getPathDrawCommand()
    {
        List<String> pointsList = new ArrayList<>();
        String[] position = {"Up","Left","Down","Right"};
        String[] gesture = {"G1","G2","G3"};
        Random random = new Random();
        int parameter = random.nextInt(4);
        for(int i = 0; i < 4 ; i++){
            for(int j = 0; j < 3; j++){
                pointsList.add("No.1|" + position[parameter + i > 3 ? parameter + i - 4 : parameter + i] + "|" + gesture[j]);
            }
        }
        for(int i = 0; i < 4 ; i++){
            for(int j = 0; j < 3; j++){
                pointsList.add("No.2|" + position[parameter + i > 3 ? parameter + i - 4 : parameter + i] + "|" + gesture[j]);
            }
        }
        return pointsList;
    }

    public static List<String> getPressureCommands()
    {
        List<String> pointsList = new ArrayList<>();
        String[] position = {"1","2","3","4","5","6","7","8","9"};
        String[] gesture = {"QF","SF","QR","SR"};
        Random random = new Random();
        int pIndex = random.nextInt(9);
        int gIndex = random.nextInt(4);
        for(int i = 0; i < 9 ; i++){
            for(int j = 0; j < 4; j++){
                pointsList.add("No.1|" + position[pIndex + i > 8 ? pIndex + i - 9 : pIndex + i] + "|"
                                       + gesture[gIndex + j > 3 ? gIndex + j - 4 : gIndex + j]);
            }
        }
        for(int i = 0; i < 9 ; i++){
            for(int j = 0; j < 4; j++){
                pointsList.add("No.2|" + position[pIndex + i > 8 ? pIndex + i - 9 : pIndex + i] + "|"
                                       + gesture[gIndex + j > 3 ? gIndex + j - 4 : gIndex + j]);
            }
        }
        return pointsList;
    }

    public static List<String> getStudyCommands()
    {
        List<String> pointsList = new ArrayList<>();
        pointsList.add("SF-QR");
        pointsList.add("SF-SR");
        pointsList.add("QF-QR");
        pointsList.add("QF-SR");

        Collections.shuffle(pointsList);

        return pointsList;
    }

}
