package sample;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Test extends JFrame implements KeyListener
{

    /**
     * @param args the command line arguments
     */
    final Image backgroundImage;
    String filePath = new File("").getAbsolutePath();

    Test() {
        try {
            backgroundImage = javax.imageio.ImageIO.read(getClass().getClassLoader().getResource("resources/keyBoard.jpg"));
            setContentPane(new JPanel(new BorderLayout()) {
                @Override
                public void paintComponent(Graphics g) {
                    g.drawImage(backgroundImage, 0, 0, null);
                }
            });
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }

        addKeyListener(this);
        setSize(547, 191);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //BorderLayout layout = new BorderLayout();
        //setLayout(layout);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A) { // c code

            try
            {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./src/resources/39175__jobro__piano-ff-028.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-7.0f); // Reduce volume by 10 decibels.
                clip.start();
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
            }

        }

        if (keyCode == KeyEvent.VK_S) {

            try
            {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./src/resources/39179__jobro__piano-ff-032.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-7.0f); // Reduce volume by 10 decibels.
                clip.start();
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
            }

        }

        if (keyCode == KeyEvent.VK_D) {


            try
            {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./src/resources/39179__jobro__piano-ff-032.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(6.0f); // Reduce volume by 10 decibels.
                clip.start();
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
            }

        }

        if(keyCode == KeyEvent.VK_F){

            try
            {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./src/resources/39182__jobro__piano-ff-035.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-7.0f); // Reduce volume by 10 decibels.
                clip.start();
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
            }
        }

        if(keyCode == KeyEvent.VK_G){

            try
            {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./src/resources/39182__jobro__piano-ff-035.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(6.0f); // Reduce volume by 10 decibels.
                clip.start();
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
            }
        }
        if (keyCode == KeyEvent.VK_1) {
            InputStream inputStream;
            try {
                inputStream = getClass().getResourceAsStream("/resources/sound1.wav");
                AudioStream audioStream = new AudioStream(inputStream);
                AudioPlayer.player.start(audioStream);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (keyCode == KeyEvent.VK_2) {
            InputStream inputStream;
            try {
                inputStream = getClass().getResourceAsStream("/resources/sound_2.wav");
                AudioStream audioStream = new AudioStream(inputStream);
                AudioPlayer.player.start(audioStream);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

//        if (keyCode == KeyEvent.VK_Q) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39180__jobro__piano-ff-033.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_2) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39181__jobro__piano-ff-034.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();            }
//
//        }
//
//        if (keyCode == KeyEvent.VK_3) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39183__jobro__piano-ff-036.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();            }
//
//        }

//        if (keyCode == KeyEvent.VK_R) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39186__jobro__piano-ff-039.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_5) {
//        }
//        if (keyCode == KeyEvent.VK_T) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39187__jobro__piano-ff-040.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_6) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39188__jobro__piano-ff-041.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_Y) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39189__jobro__piano-ff-042.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_7) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39190__jobro__piano-ff-043.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_U) {
////            InputStream inputStream;
////            try {
////                inputStream = getClass().getResourceAsStream("/resources/39191__jobro__piano-ff-044.wav");
////                AudioStream audioStream = new AudioStream(inputStream);
////                AudioPlayer.player.start(audioStream);
////            } catch (Exception ex) {
////                ex.printStackTrace();
////            }
//
//            try
//            {
//                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./src/resources/39191__jobro__piano-ff-044.wav"));
//                Clip clip = AudioSystem.getClip();
//                clip.open(audioInputStream);
//                FloatControl gainControl =
//                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//                gainControl.setValue(-4.0f); // Reduce volume by 10 decibels.
//                clip.start();
//            }
//            catch( Exception ex )
//            {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_8) {
//        }
//        if (keyCode == KeyEvent.VK_I) {
//            try
//            {
//                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( new File("./src/resources/39191__jobro__piano-ff-044.wav"));
//                Clip clip = AudioSystem.getClip();
//                clip.open(audioInputStream);
//                FloatControl gainControl =
//                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//                gainControl.setValue(6.0f); // Reduce volume by 10 decibels.
//                clip.start();
//            }
//            catch( Exception ex )
//            {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_9) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39194__jobro__piano-ff-046.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_O) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39195__jobro__piano-ff-047.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_0) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39196__jobro__piano-ff-048.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_P) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39197__jobro__piano-ff-049.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_MINUS) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39198__jobro__piano-ff-050.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_OPEN_BRACKET) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39199__jobro__piano-ff-051.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_EQUALS) {
//        }
//        if (keyCode == KeyEvent.VK_CLOSE_BRACKET) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39200__jobro__piano-ff-052.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//
//
//        if (keyCode == KeyEvent.VK_G) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39178__jobro__piano-ff-031.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_V) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39177__jobro__piano-ff-030.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_F) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39176__jobro__piano-ff-029.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_X) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39174__jobro__piano-ff-027.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_S) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39173__jobro__piano-ff-026.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_Z) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39172__jobro__piano-ff-025.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }
//        if (keyCode == KeyEvent.VK_A) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39171__jobro__piano-ff-024.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//
//        if (keyCode == KeyEvent.VK_H) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39201__jobro__piano-ff-053.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_N) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39202__jobro__piano-ff-054.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_J) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39203__jobro__piano-ff-055.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_M) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39204__jobro__piano-ff-056.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_COMMA) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39205__jobro__piano-ff-057.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_L) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39206__jobro__piano-ff-058.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_PERIOD) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39207__jobro__piano-ff-059.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_SEMICOLON) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39208__jobro__piano-ff-060.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_SLASH) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39209__jobro__piano-ff-061.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (keyCode == KeyEvent.VK_QUOTE) {
//            InputStream inputStream;
//            try {
//                inputStream = getClass().getResourceAsStream("/resources/39210__jobro__piano-ff-062.wav");
//                AudioStream audioStream = new AudioStream(inputStream);
//                AudioPlayer.player.start(audioStream);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {

        Test test = new Test();

    }
}
