//package sample;
//
//import java.util.Arrays;
//
///**
// * Created by thisum_kankanamge on 19/9/18.
// */
//public class WallLight implements Runnable
//{
//    private String writingPort[] = {"/dev/tty.usbserial-AH0105VN"};
//    private SerialClass serialPort;
//
//    public WallLight()
//    {
//        serialPort = new SerialClass(writingPort);
//    }
//
//    @Override
//    public void run()
//    {
//        boolean touching = false;
//        int touchCount = 0;
//        int distinceCount = 0;
//        int pressureCount = 0;
//        int pressureAry[] = new int[]{0,0,0,0,0,0,0,0,0,0};
//        int touchedPoints[] = new int[]{0,0,0,0,0,0,0,0,0,0};
//        int distancePoints[] = new int[]{0,0,0,0,0,0,0,0,0,0};
//        int pressure = 0;
//        int distance = 0;
//
//        while(true)
//        {
//            try
//            {
//                DataRead.find_final_position();
////                distancePoints[distinceCount] = DataRead.find_arrival_time(1)[0];
//                distancePoints[distinceCount] = DataRead.var_list.distance;
//                touchedPoints[touchCount] = DataRead.var_list.lighttouch ? 1 : 0;
//                distinceCount++;
//                touchCount++;
//                distinceCount = distinceCount == 10 ? 0 : distinceCount;
//                touchCount = touchCount == 10 ? 0 : touchCount;
//
//                touching = Arrays.stream(touchedPoints).average().getAsDouble() > 0.9;
//
//                if(touching)
//                {
//                    pressureAry[pressureCount] = DataRead.var_list.lightbrightness;
//                    pressureCount++;
//                    pressureCount = pressureCount == 10 ? 0 : pressureCount;
//
////                    distance = (int)Arrays.stream(distancePoints).average().getAsDouble();
////                    pressure = (int)Arrays.stream(pressureAry).average().getAsDouble();
//                    distance = (int)Arrays.stream(distancePoints).max().getAsInt();
//                    pressure = (int)Arrays.stream(pressureAry).max().getAsInt();
//                    System.out.println("distance: " + distance + "  pressure: " + pressure);
//                    serialWriteMessage(distance + "," + pressure + "\n");
//                }
//            }
//            catch( Exception e )
//            {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void serialWriteMessage( String msg )
//    {
//        int re = serialPort.writeData(msg.getBytes());
//        if (re != 1) {
//            System.out.println("sending msg failed");
//        }
//    }
//}
