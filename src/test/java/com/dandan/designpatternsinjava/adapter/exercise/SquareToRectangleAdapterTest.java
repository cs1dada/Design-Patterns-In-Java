package com.dandan.designpatternsinjava.adapter.exercise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareToRectangleAdapterTest {

    @Test
    void getArea() {
        Square sq = new Square(11);
        SquareToRectangleAdapter adapter = new SquareToRectangleAdapter(sq);
        assertEquals(121, adapter.getArea());

    }

    @Test
    void getWidth() {
        Square sq = new Square(11);
        SquareToRectangleAdapter adapter = new SquareToRectangleAdapter(sq);
        assertEquals(sq.side, adapter.getWidth());
        assertNotEquals(123, adapter.getWidth());

    }

}