public class Elements
{

    private  int index;
    private double value;
    private String timeModified;

    Elements(int index, double value)
    {
        this.index=index;
        this.value = value;
        this.timeModified = String.valueOf(System.nanoTime());
    }
    Elements()
    {
        this.index=-1;
        this.value = 0.0;
    }

    public void setTime(){
    this.timeModified = String.valueOf(System.nanoTime());
    }

    public void setIndex( int newIndex)
    {
        this.index=newIndex;
    }
    public void setValue(double newValue)
    {
        this.value=newValue;
    }
    public int getIndex()
    {
        return index;
    }
    public double getValue()
    {
        return value;
    }
    public String toString()
    {
        return "("+this.index+" , "+this.value+")";
    }





}
