package source.userInterface;

import java.awt.Color;

/**
 * Represent a 2D surface to render text and colored squares on. Size of the canvas is set by the constructor,
 * and then calls can be made to setCellText() and setCellColor() to add items to the canvas.
 */

public class Canvas {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final char FILL_CHARACTER = ' ';
    private final int sizeX;
    private final int sizeY;
    private final char[][] data;
    private final Color[][] color;

    public Canvas(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        data = new char[sizeY][sizeX];
        color = new Color[sizeY][sizeX];

        initializeArrays();
    }

    private void initializeArrays() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                data[y][x] = FILL_CHARACTER;
                color[y][x] = BACKGROUND_COLOR;
            }
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setCellText(int x, int y, char ch) {
        if (isCellInCanvas(x, y)) {
            data[y][x] = ch;
        }
    }

    public char getCellText(int x, int y) {
        return data[y][x];
    }

    public void setCellColor(int x, int y, Color colour) {
        if (isCellInCanvas(x, y)) {
            color[y][x] = colour;
        }
    }

    public Color getCellColor(int x, int y) {
        return color[y][x];
    }

    private boolean isCellInCanvas(int x, int y) {
        boolean isOkX = (x >= 0) && (x < sizeX);
        boolean isOkY = (y >= 0) && (y < sizeY);
        return isOkX && isOkY;
    }

    public void clear() {
        initializeArrays();
    }
}
