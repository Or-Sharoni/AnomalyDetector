package Algorithms;


import test.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StatLib {

	

	// simple average
		public static float avg(ArrayList<Float> x) {
			float sum = 0;
			int len = x.size();
			for(int i = 0; i < len; i++) {
				sum += x.get(i);
			}
			if(len != 0)
				return sum/len;
			else
				return 0;
			
		}
		// returns the variance of X and Y
		public static float var(ArrayList<Float> x) {
			int len = x.size();
			if(len == 0)
				return 0;
			float avg = avg(x);
			float sum = 0;
			for(int i = 0 ; i < len; i++) {
				sum += Math.pow(x.get(i),2);
			}
			
			return (float)((sum/len) - Math.pow(avg, 2));
		}

		// returns the covariance of X and Y
		public static float cov(ArrayList<Float> x, ArrayList<Float> y) {
			int len = x.size();
			if(len == 0)
				return 0;
			float EX = avg(x);
			float EY = avg(y);
			float sum = 0;
			float tempX = 0;
			float tempY = 0;
			for(int i = 0; i < len; i++) {
				tempX = x.get(i) - EX;
				tempY = y.get(i) - EY;
				sum += tempX * tempY;
			}
			return sum / len;
		}
		// returns the Pearson correlation coefficient of X and Y
		public static float pearson(ArrayList<Float> x, ArrayList<Float> y) {
			return (float) (cov(x,y)/(Math.sqrt(var(x))*Math.sqrt(var(y))));
		}
		// performs a linear regression and returns the line equation
		public static Line linear_reg(Point[] points) {
			int len = points.length;
			if(len == 0) {
				return null;
			}
			float a = 0;
			float b = 0;
			ArrayList<Float> x = new ArrayList<>();
			ArrayList<Float> y = new ArrayList<>();
			for(int i = 0 ; i < len; i++) {
				x.add((float)points[i].x);
				y.add((float)points[i].y);
			}
			float varX = var(x);
			float covXY = cov(x,y);

			if(varX != 0) {
				a = (float) (covXY / varX);
			}
			float avgX = avg(x);
			float avgY = avg(y);
			b = (float) (avgY - a*avgX);
			Line linearFunc = new Line(a,b);
			return linearFunc;

		}
		// returns the deviation between point p and the line equation of the points
		public static float dev(Point p,Point[] points) {
			Line li = linear_reg(points);
			float dev = ((li.a * (float)p.x) - (float)p.y + li.b);
			return dev;
			
		}
		// returns the deviation between point p and the line
		public static float dev(Point p,Line l) {
			float dev=Math.abs((float)p.y-l.f((float)p.x));
			return dev;
		}
	
}
