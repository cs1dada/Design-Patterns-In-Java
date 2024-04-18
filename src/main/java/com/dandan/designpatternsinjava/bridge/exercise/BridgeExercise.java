package com.dandan.designpatternsinjava.bridge.exercise;

//abstract class Shape
//{
//  public abstract String getName();
//}
//
//class Triangle extends Shape
//{
//  @Override
//  public String getName()
//  {
//    return "Triangle";
//  }
//}
//
//class Square extends Shape
//{
//  @Override
//  public String getName()
//  {
//    return "Square";
//  }
//}
//
//class VectorSquare extends Square
//{
//  @Override
//  public String toString()
//  {
//    return String.format("Drawing %s as lines", getName());
//  }
//}
//
//class RasterSquare extends Square
//{
//  @Override
//  public String toString()
//  {
//    return String.format("Drawing %s as pixels", getName());
//  }
//}

// imagine VectorTriangle and RasterTriangle are here too

interface Renderer {
    public String whatToRenderAs();
}

abstract class Shape {
    private Renderer renderer;
    public String name;

    public Shape(Renderer render) {
        this.renderer = render;
    }

    @Override
    public String toString() {
        return String.format("Drawing %s as %s", name, renderer.whatToRenderAs());
    }
}

class Triangle extends Shape {
    public Triangle(Renderer render) {
        super(render);
        name = "Triangle";
    }
}
class Square extends Shape {
    public Square(Renderer render) {
        super(render);
        name = "Square";
    }
}

class  RasterRenderer implements Renderer{

    @Override
    public String whatToRenderAs() {
        return "pixels";
    }
}

class  VectorRenderer implements Renderer{

    @Override
    public String whatToRenderAs() {
        return "lines";
    }
}

public class BridgeExercise {
    public static void main(String[] args) {
        //畫方塊 + 用vector的方法
        System.out.println(new Square(new VectorRenderer()).toString());

        System.out.println(new Triangle(new VectorRenderer()).toString());

        System.out.println(new Square(new RasterRenderer()).toString());

    }

}
