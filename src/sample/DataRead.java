package sample;

import java.io.FileWriter;
import java.util.logging.*;


/**
 * Created by Shi Yilei
 */

public class DataRead
{
    SerialClass arduinoDue = new SerialClass();
    public static DataAnalysis da = new DataAnalysis();
    public static Variables var_list = new Variables();

    private Thread thread;
    private SerialDataListener listener;
    private boolean readData_status = false;

    private byte msg1 = 112, msg2 = 113, fb1 = 110, fb2 = 109;
    private long st = 0, en = 0;

    /*** Thread ***/
    public DataRead(SerialDataListener listener)
    {
        this.listener = listener;
        initializeThread();
    }

    private void initializeThread()
    {
        thread = new Thread(new Runnable(){
            @Override
            public void run() {
                int count = 0;
                long average_time = 0;
                while (true) {
                    try {
                        if (readData_status) {
                            /** Time checking **/
                            st = System.nanoTime();

                            /** Location tracking **/
                            var_list.data_f1 = findPosition(msg1);
                            var_list.data_f2 = findPosition(msg2);
//                            System.out.println(var_list.data_f1[0]/220.0*15);
//                            System.out.println(var_list.data_f1[1]/220.0*15);
                            if(var_list.data_f1[2] != 0) {
                                System.out.println("---------------");
                                System.out.println(var_list.data_f1[2]);
                            }
                            listener.plotTrack();

                            if(var_list.feedback1) {
                                findPosition(fb1);
                                thread.sleep(50);
                                var_list.feedback1 = false;
                            }
                            /** Time checking **/
                            en = System.nanoTime();
                        } else {
                            Thread.sleep(20);
                        }
                    }catch (Exception e){
                        System.out.println(e.toString());
                    }

                    average_time += en - st;
                    count = count + 1;
                    if(count == 100){
                        average_time /= 100;
//                        System.out.println(average_time);
                        count = 0;
                        average_time = 0;
                    }

                }
            }
        });

        thread.start();
    }

    public void startReadingData() { readData_status = true; }

    public void stopReadingData() { readData_status = false; }

    /*** Methods to track the location or force ***/
    public int[] findPosition(byte msg) throws Exception {

        int[] firstPeaks = {0,0,0};
        int[][] datas;

        /*** Read Data ***/
        int dataLen = 0, data_num, one_batch_number = 1000;
        byte[] data_byte_temp = new byte[500], data_byte_all = new byte[6 * one_batch_number];
        int re = arduinoDue.writeData(msg);
        if (re != 1) {
            System.out.println("sending msg failed");
        }

        while (dataLen < 6 * one_batch_number) {
            data_num = arduinoDue.inputStream.read(data_byte_temp);
            for (int i = 0; i < data_num; i++) {
                try {
                    data_byte_all[dataLen] = data_byte_temp[i];
                    dataLen++;
                }catch (Exception e){
                    System.out.println("Couldn't read data from Arduino");
                    return firstPeaks;
                }
            }
        }

        datas = new int[3][one_batch_number];
        for (int i = 0; i < one_batch_number; i++) {
            datas[0][i] += ((data_byte_all[6 * i + 0] < 0) ? data_byte_all[6 * i + 0] + 256 : data_byte_all[6 * i + 0]) * 256
                    + ((data_byte_all[6 * i + 1] < 0) ? data_byte_all[6 * i + 1] + 256 : data_byte_all[6 * i + 1]);

            datas[1][i] += ((data_byte_all[6 * i + 2] < 0) ? data_byte_all[6 * i + 2] + 256 : data_byte_all[6 * i + 2]) * 256
                    + ((data_byte_all[6 * i + 3] < 0) ? data_byte_all[6 * i + 3] + 256 : data_byte_all[6 * i + 3]);

            datas[2][i] += ((data_byte_all[6 * i + 4] < 0) ? data_byte_all[6 * i + 4] + 256 : data_byte_all[6 * i + 4]) * 256
                    + ((data_byte_all[6 * i + 5] < 0) ? data_byte_all[6 * i + 5] + 256 : data_byte_all[6 * i + 5]);
        }

        /*** Find the peaks and detect whether touch happens***/
        DataRead.var_list.rawData = datas;
        int[][] smoothedData = da.smoothData(datas, 20);
        int[][] rough_time = da.calRoughness(smoothedData);

        if(msg == 112) {
            var_list.touch1 = da.isTouch(rough_time[0][1]*rough_time[1][1]*rough_time[2][1]);
            if(var_list.touch1) {
                firstPeaks[0] = da.find_peak(smoothedData[0], rough_time[0][1]);
                firstPeaks[1] = da.find_peak(smoothedData[1], rough_time[1][1]);
                firstPeaks[2] = da.find_peak(smoothedData[2], rough_time[2][1]);
                var_list.force_level_1 = da.signal_strenth(smoothedData, firstPeaks);
//                da.printArray(firstPeaks);
            }
        }

        if(msg == 113){
            var_list.touch2 = da.isTouch(rough_time[0][1]*rough_time[1][1]*rough_time[2][1]);
            if(var_list.touch2) {
                firstPeaks[0] = da.find_peak(smoothedData[0], rough_time[0][1]);
                firstPeaks[1] = da.find_peak(smoothedData[1], rough_time[1][1]);
                firstPeaks[2] = da.find_peak(smoothedData[2], rough_time[2][1]);
                var_list.force_level_2 = da.signal_strenth(smoothedData, firstPeaks);
            }
        }
        listener.plotRawData(smoothedData, firstPeaks, msg);

        int[] position = {10000,10000};
        int[] result = {10000, 10000, 0};
        if((var_list.touch1 && msg == 112) || (var_list.touch2 && msg == 113)){
            position = da.localizing_SGD(firstPeaks[0], firstPeaks[1], firstPeaks[2]);
            result[0] = position[0];
            result[1] = position[1];
            result[2] = da.signal_strenth(datas, firstPeaks);
        }

        return result;
    }

    public int[] findFinalPosition(byte msg) throws Exception{

//        long st = System.nanoTime();

        int dataNo = 9;
        /*** Read the data and calculate the position ***/
        int[] position = findPosition(msg);
        int[][] data = new int[dataNo][2];
        for(int i = 0; i < dataNo; i++) {
            for (int j = 0; j < 2; j++) {
                data[i][j] = 10000;
            }
        }

        int[] result = new int[3];


        int i = msg - 112;
        if (position[0] == 10000 || position[1] == 10000) {
            if (var_list.bd_pre[i] < dataNo) {
                var_list.bd_pre[i]++;
            }else if (var_list.bd_pre[i] == dataNo) {
                var_list.f[i] = false;
                var_list.gd_pre[i] = 0;
                var_list.data_count[i] = 0;
            }
        } else {
            var_list.bd_pre[i] = 0;
            if (var_list.gd_pre[i] < dataNo) {
                var_list.gd_pre[i]++;
                var_list.positionArray[i][var_list.gd_pre[i] - 1] = position;

                result[0] = position[0];
                result[1] = position[1];
                result[2] = 0;

                return result;
            } else if (var_list.gd_pre[i] == dataNo) {
                for (int j = 0; j < dataNo-1; j++) {
                    var_list.positionArray[i][j] = var_list.positionArray[i][j + 1];
                    data[j][0] = var_list.positionArray[i][j][0];
                    data[j][1] = var_list.positionArray[i][j][1];
                }
                var_list.positionArray[i][dataNo-1] = position;
                data[dataNo-1][0] = var_list.positionArray[i][dataNo-1][0];
                data[dataNo-1][1] = var_list.positionArray[i][dataNo-1][1];
                var_list.f[i] = true;
            }
        }

        /*** Look for the No.3 position ***/
        if (var_list.f[i]) {
            for (int j = 0; j < dataNo; j++) {
                int temp;
                for (int k = 0; k < dataNo; k++) {
                    if (data[j][0] > data[k][0]) {
                        temp = data[j][0];
                        data[j][0] = data[k][0];
                        data[k][0] = temp;
                    }
                    if (data[j][1] > data[k][1]) {
                        temp = data[j][1];
                        data[j][1] = data[k][1];
                        data[k][1] = temp;
                    }
                }
            }
        }

        result[0] = data[dataNo/2][0];
        result[1] = data[dataNo/2][1];
        result[2] = position[2];

//        long en = System.nanoTime();
//        if(en - st < 10000000){
//            Thread.sleep((10000000 - (en-st))/1000000, (int)(10000000 - (en-st))%1000000);
//        }
        return result;
    }


    /*** Method to calibrate the system ***/
    public void calibrate() throws Exception{

        /*** Calculate the edge ***/
        byte msg = 112;
        int sam_len = 100;
        int[] T;
        int[][][][] s = new int[3][6][sam_len][50];
        int[][][] t = new int[3][6][sam_len];
        int[][] L = new int[3][sam_len];
        for(int i = 0; i < sam_len; i++) {
            L[0][i] = i;
            L[1][i] = i;
            L[2][i] = i;
        }

        for(int ti = 0;ti<3;ti++) {
            while (da.cal_std(L[ti]) > 3) {
                T = findPosition(msg);
                if (T[0] != 0 && T[1] != 0 && T[2] != 0) {
                    for (int i = 0; i < sam_len - 1; i++) {
                        L[ti][i] = L[ti][i + 1];
                        s[0][ti][i] = s[0][ti][i + 1];
                        s[1][ti][i] = s[1][ti][i + 1];
                        s[2][ti][i] = s[2][ti][i + 1];

                        t[0][ti][i] = t[0][ti][i + 1];
                        t[1][ti][i] = t[1][ti][i + 1];
                        t[2][ti][i] = t[2][ti][i + 1];
                    }
                    L[ti][sam_len - 1] = Math.abs(T[ti] - T[ti+1>2?0:ti+1]);
                    s[0][ti][sam_len - 1] = da.get_sub_array(var_list.cali_rawdata[0],T[0],var_list.cali_rawdata[0].length-T[0]);
                    s[1][ti][sam_len - 1] = da.get_sub_array(var_list.cali_rawdata[1],T[1],var_list.cali_rawdata[1].length-T[1]);
                    s[2][ti][sam_len - 1] = da.get_sub_array(var_list.cali_rawdata[2],T[2],var_list.cali_rawdata[2].length-T[2]);

                    t[0][ti][sam_len-1] = T[0];
                    t[1][ti][sam_len-1] = T[1];
                    t[2][ti][sam_len-1] = T[2];
                }

            }
            Thread.sleep(2000);
        }

        var_list.L1 = da.meanV(L[0]);
        var_list.L2 = da.meanV(L[1]);
        var_list.L3 = da.meanV(L[2]);

        /*** calibrate for localization ***/
        var_list.s1[0] = -var_list.L1;
        var_list.s1[1] = 0;
        var_list.s2[0] = 0;
        var_list.s2[1] = 0;
        var_list.s3[0] = -var_list.L2 * (var_list.L1*var_list.L1 + var_list.L2*var_list.L2 - var_list.L3*var_list.L3)
                /(2*var_list.L1*var_list.L2);
        var_list.s3[1] = -(int) Math.sqrt(var_list.L2*var_list.L2 - var_list.s3[0] * var_list.s3[0]);

        int[] center = new int[2];
        center[0] = - var_list.L1 / 2;
        if(var_list.s3[0] == 0){
            center[1] = var_list.s3[1] / 2;
        }else {
            center[1] = var_list.s3[1]/2+(var_list.s3[0]-var_list.s2[0])*(var_list.s3[0]/2-center[0])/(var_list.s3[1]-var_list.s2[1]);
        }

        var_list.s1[0] -= center[0];
        var_list.s1[1] -= center[1];
        var_list.s2[0] -= center[0];
        var_list.s2[1] -= center[1];
        var_list.s3[0] -= center[0];
        var_list.s3[1] -= center[1];

        System.out.println(String.valueOf(var_list.L1)+","+String.valueOf(var_list.L2)+","+String.valueOf(var_list.L3));
        System.out.println(String.valueOf(var_list.D1)+","+String.valueOf(var_list.D2)+","+String.valueOf(var_list.D3));
        System.out.println("print sensors' location");
        da.printArray(var_list.s1);
        da.printArray(var_list.s2);
        da.printArray(var_list.s3);
    }

}
