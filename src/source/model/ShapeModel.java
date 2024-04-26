package source.model;

import java.io.File;
import java.util.Iterator;

/**
 * Interface required by the UI to support the program's operations.
 */

public interface ShapeModel {
    void populateFromJSON(File jsonFile);

    void redact();

    Iterator<? extends DrawableShape> iterator();
}
