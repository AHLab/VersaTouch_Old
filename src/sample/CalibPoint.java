package sample;

/**
 * Created by thisum_kankanamge on 18/9/18.
 */
public class CalibPoint
{
    private double pressureThr = 250.0d;
    private int x;
    private int y;
    private double pressure;
    private SoundPlayable soundFile;
    private String imgFile;

    public CalibPoint(int x, int y, double pressure)
    {
        this.x = x;
        this.y = y;
        this.pressure = pressure;
    }

    public CalibPoint(int x, int y, SoundPlayable soundFile, String imgFile)
    {
        this.x = x;
        this.y = y;
        this.soundFile = soundFile;
        this.imgFile = imgFile;
    }

    public SoundPlayable getSoundFile()
    {
        return soundFile;
    }

    public void setSoundFile(SoundPlayable soundFile)
    {
        this.soundFile = soundFile;
    }

    public String getImgFile()
    {
        return imgFile;
    }

    public void setImgFile(String imgFile)
    {
        this.imgFile = imgFile;
    }

    public double getPressure()
    {
        return pressure;
    }

    public void setPressure(double pressure)
    {
        this.pressure = pressure;
    }

    public boolean isHighPressure()
    {
        return this.pressure > pressureThr;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj)
    {
        CalibPoint that = (CalibPoint) obj;
        double distance = Math.pow((this.x - that.x), 2) + Math.pow((this.y - that.y), 2);
        if(distance < 12*12)
        {
            return true;
        }
        return super.equals(obj);
    }
}
