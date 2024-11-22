package com.example.nammatulunadu;
// Place.java
public class Place {
    private String name, description, category;

    // Constructor
    public Place(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

