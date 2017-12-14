import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class Tester
{
    public static void main (String[] args) throws Exception
    {
        //double[] data = Orig();
        double [] arr1= {8,4,6,8,4,8,10,4};
        double [] h= {0.5,0.5};
        double [] g= {0.5,-0.5};
        //int ncoefficient = data.length;
        Wavelet w1=new Wavelet(8, h, g, arr1);
        System.out.println("Prefix sum cube of original cube is: ");
        double[] prefix = w1.prefix();
        System.out.println(Arrays.toString(prefix));
        System.out.println("Cube after Haar wavelet transfermation with filters h = " + Arrays.toString(h) + ", g = " + Arrays.toString(h) + " is: ");
        double[] wavelet = w1.haarWavelet(prefix);
        System.out.println(Arrays.toString(wavelet));
        ElementArrayList.convertArray(wavelet);
        ElementArrayList.sortArrayList(ElementArrayList.elementArrayList);
        //System.out.println(ElementArrayList.elementArrayList);
        //System.out.println("Please enter how many coefficients you want to keep:");
        // Scanner sc = new Scanner(System.in);
        int sc=3;
        ArrayList<Elements> maxValue = new ArrayList<Elements>(ElementArrayList.elementArrayList.subList( ElementArrayList.elementArrayList.size()-sc, ElementArrayList.elementArrayList.size()));
        double[] maxValueArray=ElementArrayList.maxValueArray(maxValue,sc);
        System.out.println("The coefficients you selected are: ");
        System.out.println(Arrays.toString(maxValueArray));
        int[] maxValueIndex=ElementArrayList.maxValueIndex(maxValue,sc);
        double[] waveletWithCoeff= waveletWithCoeff(wavelet,maxValueIndex);
        System.out.println("The wavelet cub with kept coefficients is: ");
        System.out.println(Arrays.toString(waveletWithCoeff));
        double[] inverse = w1.inverse(waveletWithCoeff);
        System.out.println("The prefix sum cube after inverse wavelet transformation is: ");
        System.out.println(Arrays.toString(inverse));
        double sumWavelet = w1.SumWavelet(inverse,2,6);
        System.out.println("Sum(A[2:6]): " + sumWavelet);
        double sum = w1.SumWavelet(prefix,2,6);
        System.out.println("Sum(A[2:6]): " + sum);
        int percent = (int)(100*(sum-sumWavelet)/sum);
        System.out.println("With error " + percent + "%");
        //w1.update(3, 9);

        System.out.println("Previous array" + Arrays.toString(w1.getoriginalArray()));
        System.out.println("requery array" + Arrays.toString(w1.getoriginalArray()));

        System.out.println("After update array" + w1.getoriginalArray());

        System.out.println("requery array" + w1.getoriginalArray());
        int cost = w1.updateCost(3,9, sc);
        System.out.println(cost);

        /*System.out.println("original array" + Arrays.toString(arr1));
        double queryResult = query(arr1,1,5);
        long query = queryCost(arr1,1,5);
        System.out.println("query result " + queryResult + "query cost " + query);

        update1(arr1,2,6);
        long update = updateCost(arr1,2,6);
        System.out.println("updated array" + Arrays.toString(arr1));
        System.out.println("update cost " + update);

        double queryResult1 = query(arr1,1,5);
        long query1 = queryCost(arr1,1,5);
        System.out.println("query result " + queryResult1 + "query cost " + query1);*/
    }

    public static double[] waveletWithCoeff(double[] haarWavelet, int[] maxValueIndex)
    {
        for (int i = 0; i < haarWavelet.length; i++)
        {
            if(contains(maxValueIndex, i))
                continue;
            haarWavelet[i] = 0;
        }
        return haarWavelet;
    }

    public  static boolean contains(final int[] array, final int v) {

        boolean result = false;

        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }

        return result;
    }

    public static double query(double[] origArray,int from, int to)
    {
        double sum = 0;
        for(int i = from; i < to; i++)
        {
            sum = origArray[i -1] + sum;
        }
        return sum;
    }

    public static long queryCost(double[] origArray,int from, int to)
    {

        long startTime = System.nanoTime();
        double sum = 0;
           for(int i = from; i < to; i++)
           {
               sum = origArray[i -1] + sum;
           }
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    public static void update1(double[] origArray,int indexOfUpdate, double changeTo)
    {
        origArray[indexOfUpdate-1] = changeTo;
    }



    public static long updateCost(double[] origArray, int indexOfUpdate, double changeTo)
    {
        double[] play = origArray;
        long startTime = System.nanoTime();
        play[indexOfUpdate-1] = changeTo;
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    public static double[] Orig() throws IOException
    {
        System.out.println("Please enter the whole path of your original data file:");
        Scanner pathInput = new Scanner(System.in);
        String path = pathInput.next();
        File rawData =  new File(path);
        Scanner countData = new Scanner(rawData);
        int count = 0;
        while (countData.hasNext())
            count++;
        countData.close();

        double[] origArray= new double[count];
        Scanner loadData = new Scanner(rawData);
        for (int i = 0; i<=count; count++)
            origArray[i] = loadData.nextDouble();
        loadData.close();

        return origArray;
    }
}

