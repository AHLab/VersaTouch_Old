package sample;

/**
 * Created by Shi Yilei
 */
public class DataAnalysis
{

    public int[] localizing(int d1, int d2, int d3)
    {
        int[] position = new int[2];

        double dd1 = d1 - DataRead.var_list.D1;
        double dd2 = d2 - DataRead.var_list.D2;
        double dd3 = d3 - DataRead.var_list.D3;

        int[][] tem_positions = new int[3][];
        tem_positions[0] = cal_intersection(DataRead.var_list.s1, DataRead.var_list.s2, DataRead.var_list.s3, dd1, dd2, dd3);
        tem_positions[1] = cal_intersection(DataRead.var_list.s2, DataRead.var_list.s3, DataRead.var_list.s1, dd2, dd3, dd1);
        tem_positions[2] = cal_intersection(DataRead.var_list.s3, DataRead.var_list.s1, DataRead.var_list.s2, dd3, dd1, dd2);

        for( int i = 0; i < 3; i++ )
        {
            if( tem_positions[i][0] == 0 && tem_positions[i][1] == 0 )
            {
                position[0] = (tem_positions[0][0] + tem_positions[1][0] + tem_positions[2][0]) / 2;
                position[1] = (tem_positions[0][1] + tem_positions[1][1] + tem_positions[2][1]) / 2;
                return position;
            }
        }

        position[0] = (tem_positions[0][0] + tem_positions[1][0] + tem_positions[2][0]) / 3;
        position[1] = (tem_positions[0][1] + tem_positions[1][1] + tem_positions[2][1]) / 3;
        return position;
    }

    public int[] localizing_SGD(int d1, int d2, int d3)
    {



        d1 = d1 - DataRead.var_list.D1;
        d2 = d2 - DataRead.var_list.D2;
        d3 = d3 - DataRead.var_list.D3;

        /*** New modification ***/
        double[][] seed;
        if( DataRead.var_list.s2[0] == DataRead.var_list.s3[0] )
        {
            int[] x = new int[2];
            int[] y = new int[2];

            x[0] = DataRead.var_list.s1[0];
            x[1] = DataRead.var_list.s3[0];

            y[0] = DataRead.var_list.s3[1];
            y[1] = DataRead.var_list.s1[1];

            seed = new double[9][2];

            seed[0][0] = x[0] - 20;
            seed[0][1] = y[0] - 20;

            seed[1][0] = (x[0] + x[1]) / 2;
            seed[1][1] = y[0] - 20;

            seed[2][0] = x[1] + 20;
            seed[2][1] = y[0] - 20;

            seed[3][0] = x[0] - 20;
            seed[3][1] = (y[0] + y[1]) / 2;

            seed[4][0] = (x[0] + x[1]) / 2;
            seed[4][1] = (y[0] + y[1]) / 2;

            seed[5][0] = x[1] + 20;
            seed[5][1] = (y[0] + y[1]) / 2;

            seed[6][0] = x[0] - 20;
            seed[6][1] = y[1] + 20;

            seed[7][0] = (x[0] + x[1]) / 2;
            seed[7][1] = y[1] + 20;

            seed[8][0] = x[1] + 20;
            seed[8][1] = y[1] + 20;

        }
        else
        {
            seed = new double[12][2];
            int[] x = new int[3];
            int[] y = new int[2];
            x[0] = Math.min(Math.min(DataRead.var_list.s1[0], DataRead.var_list.s2[0]), DataRead.var_list.s3[0]);
            x[1] = Math.max(Math.min(DataRead.var_list.s2[0], DataRead.var_list.s3[0]), DataRead.var_list.s1[0]);
            x[2] = Math.max(Math.max(DataRead.var_list.s1[0], DataRead.var_list.s2[0]), DataRead.var_list.s3[0]);

            y[0] = Math.min(DataRead.var_list.s1[1], DataRead.var_list.s3[1]);
            y[1] = Math.max(DataRead.var_list.s1[1], DataRead.var_list.s3[1]);

            seed[0][0] = x[0] - 20;
            seed[0][1] = y[0] - 20;

            seed[1][0] = (x[0] + x[1]) / 2;
            seed[1][1] = y[0] - 20;

            seed[2][0] = (x[1] + x[2]) / 2;
            seed[2][1] = y[0] - 20;

            seed[3][0] = x[2] + 20;
            seed[3][1] = y[0] - 20;

            seed[4][0] = x[0] - 20;
            seed[4][1] = (y[0] + y[1]) / 2;

            seed[5][0] = (x[0] + x[1]) / 2;
            seed[5][1] = (y[0] + y[1]) / 2;

            seed[6][0] = (x[1] + x[2]) / 2;
            seed[6][1] = (y[0] + y[1]) / 2;

            seed[7][0] = x[2] + 20;
            seed[7][1] = (y[0] + y[1]) / 2;

            seed[8][0] = x[0] - 20;
            seed[8][1] = y[1] + 20;

            seed[9][0] = (x[0] + x[1]) / 2;
            seed[9][1] = y[1] + 20;

            seed[10][0] = (x[1] + x[2]) / 2;
            seed[10][1] = y[1] + 20;

            seed[11][0] = x[2] + 20;
            seed[11][1] = y[1] + 20;
        }

        double final_loss = 10000000;
        int[] final_p = new int[2];
        double threshold = 0.001;
        for( int i = 0; i < seed.length; i++ )
        {
            double[] o_p = seed[i];
            double[] n_p = cal_SGD(o_p[0], o_p[1], d1, d2, d3, 0.01);
            double n_d_loss = Math.abs(cal_loss(d1, d2, d3, n_p) - cal_loss(d1, d2, d3, o_p));
            long st, en, timeout;
            st = System.nanoTime();
            while( n_d_loss > threshold )
            {
                o_p = n_p;
                n_p = cal_SGD(o_p[0], o_p[1], d1, d2, d3, 0.01);
                n_d_loss = Math.abs(cal_loss(d1, d2, d3, n_p) - cal_loss(d1, d2, d3, o_p));
                if( cal_loss(d1, d2, d3, n_p) < final_loss )
                {
                    final_loss = cal_loss(d1, d2, d3, n_p);
                    final_p[0] = ( int ) n_p[0];
                    final_p[1] = ( int ) n_p[1];

                }
                timeout = System.nanoTime();
                if(timeout - st > 1000000){
//                    System.out.println("time out");
                    break;
                }
            }
        }

        return final_p;
    }

    /*** Find all the peaks of a signal ***/
    public int find_peak (int[] data, int index) throws Exception
    {
        int peak_time = 0;
        int accu_val = 1000000000;
        for(int i = index - 50; i < index + 50; i++){
            int temp_val = 0;
            for(int j = -20; j < 21; j++){
                temp_val += data[i+j];
            }

            if (temp_val < accu_val){
                accu_val = temp_val;
                peak_time = i;
            }
        }
        /***Finalise the peak time array***/
        return peak_time;
    }

    /*** Detect whether the touch has been performed ***/
    public boolean isTouch(int data)
    {
        if(data>0){
            return true;
        }else {
            return false;
        }

//        for(int n = 0; n < data[0])
//
//        if((cal_std(data[0])+cal_std(data[1])+cal_std(data[2]))/3 > 50){
//            return true;
//        }else {
//            return false;
//        }


    }

    /*** Calculate the signal strength ***/
    public int signal_strenth(int[][] data, int[] peak)
    {
        int power1 = data[0][peak[0]+140] - data[0][peak[0]];
        int power2 = data[1][peak[1]+140] - data[1][peak[1]];
        int power3 = data[2][peak[2]+140] - data[2][peak[2]];
        int power = (power1 + power2 + power3) / 3;
        return power;
    }

    /*** Get a sub array from a data array ***/
    public int[] get_sub_array(int[] data, int start, int len)
    {
        int[] re = new int[len];
        for( int i = 0; i < len; i++ )
        {
            re[i] = data[i + start];
        }

        return re;
    }

    /*** Calculate the standard deviation of an data array ***/
    public float cal_std(int[] data)
    {
        float mean = 0, std = 0;
        for( int item : data )
        {
            mean += item;
        }

        mean /= data.length;
        for( int item : data )
        {
            std += (item - mean) * (item - mean);
        }
        std /= data.length;
        std = ( float ) Math.sqrt(std);

        return std;
    }

    /*** Calculate the distance of two points ***/
    public double cal_dis(int x1, int y1, int x2, int y2)
    {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    /*** Calculate the loss ***/
    public double cal_loss(int d1, int d2, int d3, double[] data)
    {

        double x = data[0];
        double y = data[1];

        double x1 = DataRead.var_list.s1[0];
        double y1 = DataRead.var_list.s1[1];
        double x2 = DataRead.var_list.s2[0];
        double y2 = DataRead.var_list.s2[1];
        double x3 = DataRead.var_list.s3[0];
        double y3 = DataRead.var_list.s3[1];

        double loss = (-d1 + Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1))) * (-d1 + Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1)))
                + (-d2 + Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2))) * (-d2 + Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2)))
                + (-d3 + Math.sqrt((x - x3) * (x - x3) + (y - y3) * (y - y3))) * (-d3 + Math.sqrt((x - x3) * (x - x3) + (y - y3) * (y - y3)));

        return loss;
    }

    /*** Calculate the intersection of two circles ***/
    public int[] cal_intersection(int[] center1, int[] center2, int[] center3, double r1, double r2, double r3)
    {
        int[] position = new int[2];
        double center_dis = Math.sqrt((center1[0] - center2[0]) * (center1[0] - center2[0]) +
                                              (center1[1] - center2[1]) * (center1[1] - center2[1]));
        if( Math.abs(Math.abs(r1 - r2) - center_dis) < 16 )
        {
            return position;
        }

        double a = center1[0], b = center1[1], c = r1, d = center2[0], e = center2[1], f = r2;
        double x1, x2, y1, y2;
        if( e == b )
        {
            x1 = (f * f - c * c + a * a) / (2 * a);
            x2 = x1;
            y1 = -Math.sqrt(f * f - x1 * x1);
            y2 = Math.sqrt(f * f - x1 * x1);

        }
        else
        {
            double n = (a - d) / (e - b), m = ((c * c - a * a - b * b) - (f * f - d * d - e * e)) / (2 * e - 2 * b);
            double A = n * n + 1, B = 2 * n * (m - b) - 2 * a, C = a * a + (m - b) * (m - b) - c * c;
            x1 = (-B + Math.sqrt(B * B - 4 * A * C)) / (2 * A);
            x2 = (-B - Math.sqrt(B * B - 4 * A * C)) / (2 * A);
            y1 = n * x1 + m;
            y2 = n * x2 + m;
        }
        if( pointToCenter(x1, y1, center3, r3) < pointToCenter(x2, y2, center3, r3) )
        {
            position[0] = ( int ) x1;
            position[1] = ( int ) y1;
        }
        else
        {
            position[0] = ( int ) x2;
            position[1] = ( int ) y2;
        }
        return position;
    }

    /*** Find the maximum value from a data array ***/
    public int maxV(int[] data)
    {
        int ma = 0;
        for( int item : data )
        {
            ma = Math.max(ma, item);
        }
        return ma;
    }

    /*** Find the minimum value from a data array ***/
    public int minV(int[] data)
    {
        int mi = data[0];
        for( int item : data )
        {
            mi = Math.min(mi, item);
        }
        return mi;
    }

    public int meanV(int[] data)
    {
        int mean = 0;
        for( int item : data )
        {
            mean += item;
        }
        mean = mean / data.length;
        return mean;
    }

    /*** Calculate the  ***/
    public double pointToCenter(double x, double y, int[] center, double radius)
    {
        double distance = Math.abs((x - center[0]) * (x - center[0]) + (y - center[1]) * (y - center[1]) - radius * radius);
        return distance;
    }

    /*** Algorithm to smooth the data ***/
    public int[][] smoothData(int[][] data, int smoNo)
    {
        int[][] re = new int[data.length][data[0].length - smoNo + 1];
        for(int index = 0; index < data.length; index++) {
            for (int i = 0; i < data[0].length - smoNo + 1; i++) {
                for (int j = 0; j < smoNo; j++) {
                    re[index][i] += data[index][i + j];
                }
                re[index][i] /= smoNo;
            }
        }
        return re;
    }

    /*** step calculator for Gradient Descent method ***/
    public double[] cal_SGD(double x, double y, int t1, int t2, int t3, double r)
    {
        double px, py, dx1, dx2, dx3, dy1, dy2, dy3;

        dx1 = x - DataRead.var_list.s1[0];
        dy1 = y - DataRead.var_list.s1[1];
        dx2 = x - DataRead.var_list.s2[0];
        dy2 = y - DataRead.var_list.s2[1];
        dx3 = x - DataRead.var_list.s3[0];
        dy3 = y - DataRead.var_list.s3[1];

        px = 2 * (-t1 + Math.sqrt(dx1 * dx1 + dy1 * dy1)) * dx1 / Math.sqrt(dx1 * dx1 + dy1 * dy1)
                + 2 * (-t2 + Math.sqrt(dx2 * dx2 + dy2 * dy2)) * dx2 / Math.sqrt(dx2 * dx2 + dy2 * dy2)
                + 2 * (-t3 + Math.sqrt(dx3 * dx3 + dy3 * dy3)) * dx3 / Math.sqrt(dx3 * dx3 + dy3 * dy3);

        py = 2 * (-t1 + Math.sqrt(dx1 * dx1 + dy1 * dy1)) * dy1 / Math.sqrt(dx1 * dx1 + dy1 * dy1)
                + 2 * (-t2 + Math.sqrt(dx2 * dx2 + dy2 * dy2)) * dy2 / Math.sqrt(dx2 * dx2 + dy2 * dy2)
                + 2 * (-t3 + Math.sqrt(dx3 * dx3 + dy3 * dy3)) * dy3 / Math.sqrt(dx3 * dx3 + dy3 * dy3);

        double[] new_coordinate = new double[2];
        new_coordinate[0] = x - r * px;
        new_coordinate[1] = y - r * py;

        return new_coordinate;
    }

    /*** System out print a data array ***/
    public static void printArray(int[] data)
    {
        if( data != null && data.length > 0 )
        {
            for( int i = 0; i < data.length - 1; i++ )
            {
                System.out.print(data[i]);
                System.out.print(",");
            }
            System.out.println(data[data.length - 1]);
        }
    }

    public static void printdoubleArray(double[] data)
    {
        if( data != null && data.length > 0 )
        {
            for( int i = 0; i < data.length - 1; i++ )
            {
                System.out.print(( int ) data[i]);
                System.out.print(",");
            }
            System.out.println(data[data.length - 1]);
        }
    }

    public int[][] calRoughness(int[][] data){

        int[][] result = new int[3][2];
        boolean checker;
        for(int index = 0; index < data.length; index++) {
            int roughness = 500, optimal_point = 0;
            int temperary_rough = 0;
            float maxSTD = 0, tempSTD;

            for(int n = 0; n < data[index].length - 140; n++){
                tempSTD = cal_std(get_sub_array(data[index],n,140));
                if (tempSTD > maxSTD){
                    maxSTD = tempSTD;
                }
            }

            checker = true;
            for (int i = 0; i < data[0].length - 210; i++) {
                int[] data_segment = this.get_sub_array(data[index], i, 211);
                temperary_rough = 0;
                for (int step = 1; step < 6; step++) {
                    for (int j = 70; j >= step; j--) {
                        if ((data_segment[j] > data_segment[j - step])) {
                            temperary_rough++;
                        }
                    }
                    for (int j = 70; j < 211 - step; j++) {
                        if ((data_segment[j] > data_segment[j + step])) {
                            temperary_rough++;
                        }
                    }
                }
                if (temperary_rough < 60 && data_segment[70] == minV(data_segment)) {
                    if(cal_std(get_sub_array(data_segment,70,140)) > 0.6*maxSTD) {
                        if(checker) {
                            roughness = temperary_rough;
                            optimal_point = i + 70;
                            checker = false;
                        }
                    }
                }
            }


            result[index][0] = roughness;
            result[index][1] = optimal_point;
        }
        return result;
    }

    public float[] get_feature(int[] data, int index){

        int[] back = new int[70];
        int[] forward = new int[140];
        float[] result = new float[4];

        for (int k = 0; k < 70; k++){
            back[k] = data[index-k-1] - data[index-k];
        }

        for (int k = 0; k < 140; k++){
            forward[k] = data[index+k] - data[index+k-1];
        }

        result[0] = meanV(back);
        result[1] = cal_std(back);
        result[2] = meanV(forward);
        result[3] = cal_std(forward);

        return result;
    }
}
