package com.dandan.designpatternsinjava.builder.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeBuilderTest {

    private String preprocess(String text)
    {
        return text.replace("\r\n", "\n").trim();
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    public void emptyTest() {
        CodeBuilder cb = new CodeBuilder("Foo");
        System.out.println(cb.toString());

    }

    @Test
    public void personTest()
    {
        CodeBuilder cb = new CodeBuilder("Person")
                .addField("String", "name")
                .addField("int", "age");
        System.out.println(cb.toString());

        assertEquals("public class Person {\n" +
                        "  public String name;\n" +
                        "  public int age;\n}",
                preprocess(cb.toString()));
    }
}