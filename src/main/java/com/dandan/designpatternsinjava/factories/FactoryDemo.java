package com.dandan.designpatternsinjava.factories;

enum CoordinateSystem
{
    CARTESIAN,
    POLAR
}

class Point {
    private double x, y;

    //private constructor force user to use factory method
    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // bad constructor
    public Point(double a,
                 double b, // names do not communicate intent
                 CoordinateSystem cs)
    {
        switch (cs)
        {
            case CARTESIAN:
                this.x = a;
                this.y = b;
                break;
            case POLAR:
                this.x = a * Math.cos(b);
                this.y = a * Math.sin(b);
                break;
        }
    }

    // factory method
    public static Point newCartesianPoint(double x, double y)
    {
        return new Point(x,y);
    }

    public static Point newPolarPoint(double rho, double theta)
    {
        return new Point(rho*Math.cos(theta), rho*Math.sin(theta));
    }

    //nested class
    public static class Factory {
        public static Point newCartesianPoint(double x, double y)
        {
            return new Point(x,y);
        }

        public static Point newPolarPoint(double rho, double theta)
        {
            return new Point(rho*Math.cos(theta), rho*Math.sin(theta));
        }

    }
}

public class FactoryDemo {
    public static void main(String[] args) {
        //factory method
        Point point1 = Point.newCartesianPoint(1, 2);
        Point point2 = Point.newPolarPoint(2, 3);

        //nested class
        Point point3 = Point.Factory.newCartesianPoint(3,4);
    }
}
