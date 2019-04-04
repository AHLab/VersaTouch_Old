//package sample;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.Arrays;
//
///**
// * Created by thisum_kankanamge on 19/9/18.
// */
//public class FileTransfer implements Runnable
//{
//    private String srcFile = "/Users/thisum/Desktop/test.txt";
//    private String desFile = "/Users/thisum/Dropbox/DEMO/test.txt";
//
//    public void copyPaste(String src, String dest)
//    {
//        try
//        {
//            System.out.println("Copy Paste");
//            Path path = Paths.get(src);
//            Files.copy(path, Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
//        }
//        catch( Exception e )
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public void cutPaste(String src, String dest)
//    {
//        try
//        {
//            System.out.println("Cut Paste");
//            copyPaste(src, dest);
//            Path path = Paths.get(src);
//            Files.delete(path);
//        }
//        catch( Exception e )
//        {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void run()
//    {
//        int count = 0;
//        boolean touched = false;
//        boolean touching = false;
//        int pressureAry[] = new int[]{0,0,0,0,0};
//        int touchedPoints[] = new int[]{0,0,0,0,0};
//        int pressureCount = 0;
//        boolean hardTouch = false;
//
//        while(true)
//        {
//            try
//            {
//                DataRead.find_final_position();
//                touchedPoints[count] = DataRead.isTouched() ? 1 : 0;
//                count++;
//                count = count == 5 ? 0 : count;
//
//                touching = Arrays.stream(touchedPoints).average().getAsDouble() > 0.9;
//
//                if(touching & !touched)
//                {
//                    pressureAry[pressureCount] = DataRead.readPressureValue();
//                    pressureCount++;
//
//                    if(pressureCount == 5)
//                    {
//                        hardTouch = Arrays.stream(pressureAry).average().getAsDouble() > 200;
//                        touched = touching;
//                        pressureCount = 0;
//                    }
//                }
//                else if(!touching && touched)
//                {
//                    if(hardTouch)
//                    {
//                        cutPaste(srcFile, desFile);
//                    }
//                    else
//                    {
//                        copyPaste(srcFile, desFile);
//                    }
//                    touched = touching;
//                }
//            }
//            catch( Exception e )
//            {
//                e.printStackTrace();
//            }
//        }
//    }
//}
