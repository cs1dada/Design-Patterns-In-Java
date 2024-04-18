package com.dandan.designpatternsinjava.builder.exercise;

import java.util.ArrayList;
import java.util.List;

class Field {
    public String type, name;

    public Field(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return String.format("public %s %s;", type, name);
    }
}

class Class {

    public String name;
    public List<Field> fields = new ArrayList<>();

    public Class() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String ls = System.lineSeparator();
        sb.append("public class ").append(name).append(" {").append(ls);
        for (Field field : fields) {
            sb.append("  ").append(field).append(ls);
        }
        sb.append("}");


        return sb.toString();
    }
}

class CodeBuilder {
    private Class theClass = new Class();

    public CodeBuilder(String className) {
        theClass.name = className;
    }

    public CodeBuilder addField(String type, String name) {
        Field f = new Field(type, name);
        theClass.fields.add(f);
        return this;
    }

    @Override
    public String toString() {
        return theClass.toString();
    }
}
