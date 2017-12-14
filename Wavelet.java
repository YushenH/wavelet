import com.sun.org.apache.xml.internal.serialize.TextSerializer;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Arrays;

public class Wavelet 
{
	private int nCoefficient;	//number of coefficient
	private double[] g;		//filter g for
	private double[] h;		//filter h for
	private double[] originalArray = new double[1024];


	//constructor
	public Wavelet(int ncoefficient, double[] h, double[] g, double[] array) {
		this.nCoefficient = ncoefficient;
		this.g = g;
		this.h = h;
		this.originalArray = array;
	}
	//copy constructor
	public Wavelet(Wavelet w1)
	{
		this.nCoefficient = w1.nCoefficient;
		this.g = w1.g;
		this.h = w1.h;
		this.originalArray = w1.originalArray;
	}

	public double[] getoriginalArray(){
		return originalArray;
	}

	public double[] prefix()
	{	
		
		double[] prefixArray =new double[nCoefficient];
		for(int i=0; i<nCoefficient; i++)
		{
			if(i==0)
				prefixArray[0]=originalArray[0];
			else if(i>0)
			{
				prefixArray[i]=originalArray[i]+prefixArray[i-1];
			}
				
		}

		return 	prefixArray;
	}

	public double[] haarWavelet(double[] prefixArray)
	{
		double[] filteredWavelet = new double[nCoefficient];
		int lengthOfHalfArry = nCoefficient/2;
		int haarWaveletIndex = 0;
		for (int m = 0; m < nCoefficient/2; m++)
		{

			for (int i = 0; i  < lengthOfHalfArry; i++)
			{
				filteredWavelet[lengthOfHalfArry+i] = prefixArray[haarWaveletIndex]*g[0] + prefixArray[haarWaveletIndex+1]*g[1];
				filteredWavelet[i] = prefixArray[haarWaveletIndex]*h[0] + prefixArray[haarWaveletIndex+1]*h[1];
				haarWaveletIndex+=2;
			}

			prefixArray = Arrays.copyOf(filteredWavelet,lengthOfHalfArry);
			lengthOfHalfArry/=2;
			haarWaveletIndex = 0;
		}

		return filteredWavelet;		
	}


	public double[] inverse(double[] waveletWithCoeff)
	{
		double[] waveletInverse = Arrays.copyOf(waveletWithCoeff, waveletWithCoeff.length);
		int lengthOfHalfArry = 1;
		for (int m = 0; m < waveletWithCoeff.length/2-1; m++)
		{
			double[] prefixArray = Arrays.copyOf(waveletInverse,lengthOfHalfArry*2);
			for (int i = 0; i  < lengthOfHalfArry ; i++)
			{
				double prev = prefixArray[i];
				double next = prefixArray[i+lengthOfHalfArry];

				waveletInverse[2*i] = prev + next;
				waveletInverse[2*i+1] = prev - next;

			}
			lengthOfHalfArry*=2;
		}

		return waveletInverse;
	}

	public double SumWavelet(double[] waveletInverse, int from, int to)
	{
		double sum = waveletInverse[to-1] - waveletInverse[from-2];
		return sum;

	}

	public int updateCost(int indexOfUpdate, double changeTo, int nofCoeff)
	{
		Wavelet w2 = new Wavelet(this);
		w2.originalArray = Arrays.copyOf(this.originalArray,this.nCoefficient);

		//System.out.println(Arrays.toString(this.originalArray));
		//System.out.println(Arrays.toString(w2.originalArray));
		w2.originalArray[indexOfUpdate-1] = changeTo;
		//System.out.println(Arrays.toString(this.originalArray));
		//System.out.println(Arrays.toString(w2.originalArray));

		double[] before = this.haarWavelet(this.prefix());
		ArrayList<Elements> maxValue = new ArrayList<Elements>(ElementArrayList.elementArrayList.subList( ElementArrayList.elementArrayList.size()- nofCoeff, ElementArrayList.elementArrayList.size()));
		int[] maxValueIndex=ElementArrayList.maxValueIndex(maxValue,nofCoeff);
		double[] w1WithCoeff= Tester.waveletWithCoeff(before,maxValueIndex);
		double[] w1w = this.inverse(w1WithCoeff);

		double[] after = w2.haarWavelet(w2.prefix());
		double[] w2WithCoeff= Tester.waveletWithCoeff(after,maxValueIndex);
		this.inverse(w2WithCoeff);
		double[] w2w = this.inverse(w2WithCoeff);
		//System.out.println(Arrays.toString(w1w));
		//System.out.println(Arrays.toString(w2w));
		int count = 0;
		for (int i = 0; i< this.nCoefficient; i++)
		{
			if (w1w[i] != w2w[i])
				count++;
		}
		return count;
	}

	public int queryCost(int from, int to)
	{
		return 2;
	}
}


