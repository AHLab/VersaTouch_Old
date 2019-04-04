package sample;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.*;
import java.util.Enumeration;

public class SerialClass {
    //public class SerialClass implements SerialPortEventListener {
    SerialPort serialPort;
    private String arduinoNano = null;
    /** The port we're normally going to use. */
    public String[] PORT_NAMES = {
            "/dev/cu.usbmodem14231",
            "/dev/cu.usbmodem1421",
            "/dev/cu.usbmodem1411",
            "/dev/cu.usbmodem14111",
            "/dev/cu.usbmodem14121",
            "/dev/cu.usbmodem14221",
            "/dev/cu.usbmodem14211",
            "/dev/cu.usbmodem3255901",
            "/dev/cu.usbmodem142411",
            "/dev/cu.usbmodem142441",
            "/dev/cu.usbmodem185",
            "/dev/cu.usbmodem142101",
            "/dev/tty.usbmodem14111",
            "/dev/tty.usbmodem3255901"
    };

    /**
     * A BufferedReader which will be fed by a InputStreamReader
     * converting the bytes into characters
     * making the displayed results codepage independent
     */
    public BufferedReader input;
    private InputStreamReader inputChar;
    private ByteArrayInputStream inputByte;
    public InputStream inputStream;
    private DataInputStream dataInputStream;
    /** The output stream to the port */
    private OutputStream outputStream;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 115200;
    /** Values to be used in the class**/
    private String inputLine = null;
    private byte inputAry[] = new byte[2];
    private int byte_data[] = {0,0,0,0,0,0};
    private int data, lowbyte, highbyte;


    public SerialClass(){
        initialize();
    }

    public void initialize() {
        // the next line is for Raspberry Pi and
        // gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
//        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                                                  TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                                           SerialPort.DATABITS_8,
                                           SerialPort.STOPBITS_1,
                                           SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()), 6);
            dataInputStream = new DataInputStream(serialPort.getInputStream());
            inputChar = new InputStreamReader(serialPort.getInputStream());
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();

            // add event listeners
//			serialPort.addEventListener(this);
//			serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
//	public synchronized void serialEvent(SerialPortEvent oEvent) {
//		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//			try {
//				String inputLine=input.readLine();
//				System.out.println(inputLine);
//			} catch (Exception e) {
//				System.err.println(e.toString());
//			}
//		}
//		// Ignore all the other eventTypes, but you should consider the other ones.
//	}

    /** Clean the port for read line **/
    public void clearPort(){
        long start = System.nanoTime();
        long end = System.nanoTime();
        while((end - start)<2000000000){
            this.getLine();
            end = System.nanoTime();
        }
        System.out.println("---Port has been cleaned!---");
    }

    /** Read a line from the serial port **/
    public String getLine(){
        try{
            inputLine = input.readLine();
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return inputLine;
    }

    /** Read data bytes by bytes **/
    public int getData(){
        try {
            data = inputStream.read();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return data;
    }

    public int writeData(byte data[]){
        try{
            outputStream.write(data);
        }catch (Exception e){
            System.out.println(e.toString());
            return -1;
        }
        return 1;
    }

    /** Write bytes data to the serial port **/
    public int writeData(byte data){
        try{
            outputStream.write(data);
        }catch (Exception e){
            System.out.println(e.toString());
            return -1;
        }
        return 1;
    }
}

