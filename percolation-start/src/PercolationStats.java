import java.util.*;
import java.awt.Point;
/**
 * Compute statistics on Percolation after performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 */
//Doruk Doganay dsd20
public class PercolationStats {
	//initialize variables and randoms to do calculations with later
	public static int RANDOM_SEED = 1234;
	double[] s;
	public static Random myRandom = new Random(RANDOM_SEED);
	public double openSites = 0;
	public ArrayList<Point> list = new ArrayList<Point>();
	public double numberOfTrials;
	public double size;
		
	public PercolationStats(int n, int t){
		
		int ngrid = n*n;
		IUnionFind finder = new QuickFind(ngrid); //define finder
		if(n<=0 || t<=0){
			throw new IllegalArgumentException(); //n and t "out of bounds", sort of
		}
		//assign n and t values
		numberOfTrials = t;
		size = n;
		//start count, if conditions passed increment at the end
		int count = 0;
		//start variable s for statistical calculations
		s = new double[t];
		while (count < numberOfTrials) {
			//check conditions and shuffle each time, add the point to the list
			openSites = 0;
			for(int i = 0 ; i<n; i++) {  
				for(int j =0; j< n ; j++) {
					list.add(new Point(i,j));
				}
			}
			
			IPercolate perc = new PercolationUF(n, finder);
			
			
			Collections.shuffle(list, myRandom); //given in the walkthrough for the assignment
			
			
			for(int k = 0 ; k<list.size(); k++) {
				Point x = list.get(k);
				if(perc.percolates()){
					//if system percolates we can stop and move on to the calculations
					break;
				}
				else {
					//if not, open and update, then increment count
					perc.open((int)x.getX(), (int)x.getY());	
				}
			}
			s[count] = perc.numberOfOpenSites()/(size*size);
			count++;
		}
	}
	
	
	//find mean
	public double mean(){
		if(s==null){
			return -1;
		}
		return StdStats.mean(s);
		
	}
	
	//find standard deviation
	public double stddev(){
		if(s==null){
			return -1;
		}
		return StdStats.stddev(s);
		
	}
	
	//find low confidence limit (-)
	public double confidenceLow(){
		double val = Math.sqrt(numberOfTrials);
		return mean() - 1.96*stddev()/val;
		
	}
	
	//find high confidence limit (+)
	public double confidenceHigh(){
		double val = Math.sqrt(numberOfTrials);
		return mean() + 1.96*stddev()/val;
		
	}
	public static void main(String[] args) {
		double start =  System.nanoTime();
		PercolationStats ps = new PercolationStats(200,100);
		double end =  System.nanoTime();
		double time =  (end-start)/1e9;
		System.out.printf("mean: %1.4f, time: %1.4f\n",ps.mean(),time);
	}
}
