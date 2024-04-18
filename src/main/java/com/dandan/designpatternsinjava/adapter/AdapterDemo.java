package com.dandan.designpatternsinjava.adapter;

import java.util.*;

class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     在這個 Point 類中，equals 方法被重寫以比較兩個點是否相等。這個方法接受一個 Object 類型的參數，因為在 Java 中，所有類都是 Object 類的子類。
     在 equals 方法的實現中，首先檢查引用是否指向同一個對象，如果是的話，則返回 true，因為同一個對象肯定相等。接著檢查參數是否為空或者類型是否與目前類型不相符，如果是的話，則返回 false。
     最後，將參數強制轉換為 Point 類型，並比較兩個點的 x 和 y 坐標是否相等，如果都相等則返回 true，否則返回 false。
     這樣的重寫使得我們可以方便地使用 equals 方法來比較兩個 Point 對象是否相等，而不需要比較它們的引用。這是一種更符合我們實際需求的方式，因為我們通常希望比較對象的屬性而不是引用。
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public int hashCode()
    {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Line {
    public Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (!start.equals(line.start)) return false;
        return end.equals(line.end);
    }

    @Override
    public int hashCode()
    {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }

}

class VectorObject extends ArrayList<Line> {
}

class VectorRectangle extends VectorObject {
    public VectorRectangle(int x, int y, int width, int height) {
        add(new Line(new Point(x, y), new Point(x + width, y)));
        add(new Line(new Point(x + width, y), new Point(x + width, y + height)));
        add(new Line(new Point(x, y), new Point(x, y + height)));
        add(new Line(new Point(x, y + height), new Point(x + width, y + height)));
    }
}

class LineToPointAdapter extends ArrayList<Point> {
    private static int count = 0;
    private static Map<Integer, List<Point>> cache = new HashMap<>();
    private int hash;
    public LineToPointAdapter(Line line) {
        //新增Cache版本, 避免重複的點會重印
        hash = line.hashCode();
        if (cache.get(hash) != null) return; // we already have it

        System.out.println(
                String.format("%d: Generating points for line [%d,%d]-[%d,%d] (no caching)",
                        ++count, line.start.x, line.start.y, line.end.x, line.end.y));

        ArrayList<Point> points = new ArrayList<>();

        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int top = Math.min(line.start.y, line.end.y);
        int bottom = Math.max(line.start.y, line.end.y);
        int dx = right - left;
        int dy = line.end.y - line.start.y;

        if (dx == 0) {
            for (int y = top; y <= bottom; ++y) {
                add(new Point(left, y));
            }
        } else if (dy == 0) {
            for (int x = left; x <= right; ++x) {
                add(new Point(x, top));
            }
        }

        cache.put(hash, points);
    }
}

public class AdapterDemo {
    //init Rectangle, VectorRectangle => 4條line, 8個point
    private static final List<VectorObject> vectorObjects =
            new ArrayList<>(Arrays.asList(
//                    new VectorRectangle(1,1,10,10),
                    new VectorRectangle(3, 3, 6, 6)
            ));

    public static void drawPoint(Point p) {
//        System.out.print(".");
        System.out.print("." + p.toString());
    }

    private static void draw() {
        for (VectorObject vo : vectorObjects) {
            for (Line line : vo) {
                //把線上的點全部列出
                LineToPointAdapter adapter = new LineToPointAdapter(line);
                //列印點
                adapter.forEach(AdapterDemo::drawPoint);
            }
        }
    }

    public static void main(String[] args) {
        draw();
        draw();


//        VectorRectangle  rectangle = new VectorRectangle(1,1,10,10);
//        rectangle.forEach(a-> {
//                    System.out.println("start: "  + a.start.toString() + " end: "  +   a.end.toString());
//                });


    }
}
