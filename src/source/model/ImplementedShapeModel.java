package source.model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Class that implements ShapeModel for a set of rectangular shapes.
 * Creates and holds set of shapes from JSON file.
 * Provides redact and iterator for userInterface.
 */

public class ImplementedShapeModel implements ShapeModel {
    private List<DrawableShape> shapes = new ArrayList<>();

    // Load JSON Objects (not arrays) from file using inner class
    @Override
    public void populateFromJSON(File jsonFile) {
        try {
            FileReader reader = new FileReader(jsonFile);
            Gson gson = new Gson();
            Shapes shapesCollected = gson.fromJson(reader, Shapes.class);
            shapes = shapesCollected.toShapeList();
        } catch (FileNotFoundException error) {
            System.out.println("No such file exists");
        }
    }

    // Redact all our objects (UI updates after calling this)
    @Override
    public void redact() {
        for (DrawableShape shape : shapes) {
            shape.redact();
        }
    }

    // Provide iterator to shapes.
    @Override
    public Iterator<DrawableShape> iterator() {
        return shapes.iterator();
    }

    // Inner class that holds Shapes Object instead of Shape Array for JSON
    public class Shapes {
        public final Shape[] shapes;

        public Shapes(Shape[] shapes) {
            this.shapes = shapes;
        }

        public List<DrawableShape> toShapeList() {
            return new ArrayList<>(Arrays.asList(shapes));
        }
    }
}
