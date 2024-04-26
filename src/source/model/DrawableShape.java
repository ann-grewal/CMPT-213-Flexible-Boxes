package source.model;

import source.userInterface.Canvas;

/**
 * Interface that represents a shapes able to draw or redact itself on a canvas.
 */

public interface DrawableShape {
    void draw(Canvas canvas);

    // Added to better interact with ShapeModel.
    void redact();
}
