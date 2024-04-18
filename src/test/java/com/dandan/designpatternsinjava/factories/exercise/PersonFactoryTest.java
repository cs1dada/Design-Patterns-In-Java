package com.dandan.designpatternsinjava.factories.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonFactoryTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void personFactory() {
        PersonFactory pf = new PersonFactory();
        Person p1 = pf.createPerson("bruce");
        assertEquals("bruce", p1.name);
        assertEquals(0, p1.id);

        Person p2 = pf.createPerson("danny");
        assertEquals("danny", p2.name);
        assertEquals(1, p2.id);
    }
}