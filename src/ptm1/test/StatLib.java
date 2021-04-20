package ptm1.test;


public class StatLib {



    // simple average
    public static float avg(float[] x){
        float sum=0;
        for(int i=0;i<x.length;i++)
            sum+=x[i];
        return sum/x.length;
    }

    // returns the variance of X and Y
    public static float var(float[] x){
        float sum=0;
        float v;
        for(int i=0;i<x.length;i++)
            sum+= Math.pow(x[i],2);
        v = sum/x.length;
        float a = avg(x);
        return v-a*a;
    }

    // returns the covariance of X and Y
    public static float cov(float[] x, float[] y){
        int i;
        float[] xy = new float[x.length];
        for (i=0; i<x.length;i++){
            xy[i]=x[i]*y[i];
        }
        return avg(xy) - avg(x)*avg(y);

    }


    // returns the Pearson correlation coefficient of X and Y
    public static float pearson(float[] x, float[] y){

        return (float) (cov(x,y)/(Math.sqrt(var(x))*Math.sqrt(var(y))));
    }

    // performs a linear regression and returns the line equation
    public static Line linear_reg(Point[] points){
        float[] xx = new float[points.length];
        float[] yy = new float[points.length];
        for(int i=0; i<points.length;i++){
            xx[i] = points[i].x;
            yy[i] = points[i].y;
        }
        float a = cov(xx,yy) / var(xx);
        float b = avg(yy) - a*avg(xx);
        Line l = new Line(a,b);
        return l;
    }

    // returns the deviation between point p and the line
    public static float dev(Point p,Line l){

        return Math.abs(l.a*p.x+l.b - p.y);
    }
    // returns the deviation between point p and the line equation of the points
    public static float dev(Point p,Point[] points){

        return dev(p,linear_reg(points));
    }



}
