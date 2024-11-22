package com.example.nammatulunadu;
// Place.java
public class Category {
    private String name;

    // Constructor
    public Category(String name) {
        this.name = name;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}

