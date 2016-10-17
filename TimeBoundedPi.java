import java.util.*;
import java.io.*;

class TimeBoundedPi {
	
	public static void main(String[] args) {
		
		long executionTime;
		Random R = new Random();

		if (args.length == 0) {
			executionTime = 100;
		} else {
			executionTime = Long.parseLong(args[0]);
		}
		
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
	
		System.out.println("Total iteration: " + iteration);
		System.out.println("Estimation: " + pi);
		System.out.println("Error: " + Math.abs(Math.PI - pi));
	
	}
}