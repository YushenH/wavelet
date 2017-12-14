import java.util.ArrayList;
import java.util.Collections;


public class ElementArrayList
{
    static ArrayList<Elements> elementArrayList;

    static {
        elementArrayList = new ArrayList<>();
    }

    public static void convertArray(double[] arr)
    {
        for(int i=0; i<arr.length; i++)
        {;
            elementArrayList.add(new Elements(i, arr[i]));
        }
    }

    public static ArrayList<Elements> sortArrayList(ArrayList<Elements>elementArrayList )
    {
        Collections.sort(elementArrayList,(eLements1, elements2) -> Double.compare(Math.abs(eLements1.getValue()), Math.abs(elements2.getValue())));

        return elementArrayList;
    }

    public static int[] maxValueIndex(ArrayList<Elements>maxValue, int sc)
    {
        int [] maxValueIndex= new int [sc];
        for(int i=0;i<sc; i++)
        {
            maxValueIndex[i]=maxValue.get(i).getIndex();
        }
        return maxValueIndex;
    }
    public static double[] maxValueArray(ArrayList<Elements>maxValue, int sc)
    {
        double [] maxValueArray= new double [sc];
        for(int i=0;i<sc; i++)
        {
            maxValueArray[i]=maxValue.get(i).getValue();
        }
        return maxValueArray;
    }

}
