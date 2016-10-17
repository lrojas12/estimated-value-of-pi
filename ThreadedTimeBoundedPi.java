import java.util.*;
import java.io.*;

class ThreadedTimeBoundedPi implements Runnable {
	
	static long executionTime;
	static int numThreads; //total number of threads
	int threadNum; //current threads thread number
	static Results[] results;
		
	public ThreadedTimeBoundedPi() {
		//if there is no thread number, it set to -1
		this(-1);
	}
	
	public ThreadedTimeBoundedPi(int threadNum) {
		this.threadNum = threadNum;
	}
	
	public void run() {
		
		Random R = new Random();

		long inCircle = 0;
		double x   = 0;
		double y   = 0;
		double pi = 0;
		
		//gives start time
		long startTime = System.currentTimeMillis();

		long iteration = 0;
		
		while(System.currentTimeMillis() <= startTime + executionTime) {
			x = R.nextDouble();
			y = R.nextDouble();
			if(x*x + y*y <= 1) {
				inCircle += 1;
			}
			iteration += 1;
		}
		
		pi = (double)inCircle / iteration * 4.0;
		//instead of printing the result, save it to the results array
		results[threadNum] = new Results(iteration, pi);
	}
	
	public static void main(String[] args) {
		
		//check the arguments
		if (args.length == 0) {
			//no parameters given
			executionTime = 100; //default
			numThreads = 2; //default
		} else if (args.length == 1) {
			//only execution time given
			executionTime = Long.parseLong(args[0]);
			numThreads = 2;	//default
		} else {
			//everything is given
			executionTime = Long.parseLong(args[0]);
			numThreads = Integer.parseInt(args[1]);
		}
		
		//create results array for threads to store their results in
		results = new Results[numThreads];
		
		//holds all the actual thread objects
		List<Thread> threads = new ArrayList<Thread>(numThreads); //size numThreads
		
		//makes as many threads as the user wants
		for (int i = 0; i < numThreads; i++) {
			threads.add(new Thread(new ThreadedTimeBoundedPi(i)));
		}
		
		//run all the threads
		for (int i = 0; i < numThreads; i++) {
			threads.get(i).start();
		}
		
		//wait for all the threads to finish
		for (int i = 0; i < numThreads; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				//thread i is done
			}
		}
		
		long totalIterations = 0;
		double totalPi = 0;
		
		//add up all the individual	results
		for (int i = 0; i < numThreads; i++) {
			Results r = results[i];
			totalIterations += r.iteration;
			totalPi += r.pi;
		}
		
		//average pi
		double pi = totalPi/numThreads;
			
		//report the overall results
		System.out.println("Total iteration: " + totalIterations);
		System.out.println("Estimation: " + pi);
		System.out.println("Error: " + Math.abs(Math.PI - pi));	
	}
	
	//nested class
	public class Results {
		
		long iteration;
		double pi;
	
		//constructor
		public Results (long i, double p) {
			this.iteration = i;
			this.pi = p;
		}
	}
}