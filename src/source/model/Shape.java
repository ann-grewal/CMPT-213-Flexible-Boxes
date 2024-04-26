package source.model;

import source.userInterface.Canvas;
import source.model.border.*;

import java.awt.*;
import java.util.List;

/**
 * Class that holds information and how to draw a rectangular shape.
 * It implements DrawableShape by specifying canvas cells with rectangles color, border, and fill.
 * Redacts itself by setting its color, border, and fill to the redacting view.
 * Uses Border and RowStrings to deal with the sequencing of complicated borders and fill text.
 */

public class Shape implements DrawableShape {
    final int top;
    final int left;
    final int width;
    final int height;
    String background;
    String backgroundColor;
    String line;
    String lineChar;
    String fill;
    String fillText;

    private Shape(int top, int left, int width, int height, String background,
                  String backgroundColor, String line, String lineChar, String fill, String fillText) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
        this.background = background;
        this.backgroundColor = backgroundColor;
        this.line = line;
        this.lineChar = lineChar;
        this.fill = fill;
        this.fillText = fillText;
    }

    Color setJavaColor() {
        return switch (backgroundColor) {
            case "light gray" -> Color.LIGHT_GRAY;
            case "gray" -> Color.GRAY;
            case "dark gray" -> Color.DARK_GRAY;
            case "black" -> Color.BLACK;
            case "red" -> Color.RED;
            case "pink" -> Color.PINK;
            case "orange" -> Color.ORANGE;
            case "yellow" -> Color.YELLOW;
            case "green" -> Color.GREEN;
            case "magenta" -> Color.MAGENTA;
            case "cyan" -> Color.CYAN;
            case "blue" -> Color.BLUE;
            default -> Color.WHITE;
        };
    }

    private void drawSolidBackground(Canvas canvas) {
        Color color = setJavaColor();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvas.setCellColor(left + j, top + i, color);
            }
        }
    }

    private void drawCheckeredBackground(Canvas canvas) {
        Color color = setJavaColor();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean evenColoredBox = i % 2 == 0 && j % 2 == 0;
                boolean oddColoredBox = i % 2 == 1 && j % 2 == 1;
                if (evenColoredBox || oddColoredBox) {
                    canvas.setCellColor(left + j, top + i, color);
                }
            }
        }
    }

    private void drawTriangleBackground(Canvas canvas) {
        Color color = setJavaColor();
        for (int i = 0; i < height; i++) {
            for (int j = i; j < width; j++) {
                canvas.setCellColor(left + j, top + i, color);
            }
        }
    }

    private void drawBackground(Canvas canvas) {
        if (background.equals("checker")) {
            drawCheckeredBackground(canvas);
        } else if (background.equals("triangle")) {
            drawTriangleBackground(canvas);
        } else {
            drawSolidBackground(canvas);
        }
    }

    private void drawInnerWrapper(Canvas canvas) {
        List<String> innerRows = RowStrings.textToRowStrings(fillText, width - 2);
        int heightOfRows = Math.min(innerRows.size(), height - 2) + 1;
        for (int i = 1; i < heightOfRows; i++) {
            String innerString = innerRows.get(i - 1);
            for (int j = 1; j < width - 1; j++) {
                canvas.setCellText(left + j, top + i, innerString.charAt(j - 1));
            }
        }
    }

    private void drawInnerSolid(Canvas canvas, char innerChar) {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                canvas.setCellText(left + j, top + i, innerChar);
            }
        }
    }

    private void drawInner(Canvas canvas) {
        if (fill.equals("wrapper")) {
            drawInnerSolid(canvas, ' ');
            drawInnerWrapper(canvas);
        } else {
            drawInnerSolid(canvas, fillText.charAt(0));
        }
    }

    private void drawFullBorder(Canvas canvas, Border border) {
        for (int i = 0; i < width; i++) {
            canvas.setCellText(left + i, top, border.nextChar());
        }
        for (int i = 1; i < height - 1; i++) {
            canvas.setCellText(left + width - 1, top + i, border.nextChar());
        }
        for (int i = width - 1; i >= 0; i--) {
            canvas.setCellText(left + i, top + height - 1, border.nextChar());
        }
        for (int i = height - 1; i > 1; i--) {
            canvas.setCellText(left, top + i - 1, border.nextChar());
        }
    }

    private void drawBorder(Canvas canvas) {
        Border border;
        if (height > 1 && width > 1) {
            border = new BorderFull(width, height);
        } else {
            border = new BorderPartial(width, height);
        }
        if (line.equals("ascii line")) {
            border.populateASCII();
        } else if (line.equals("sequence")) {
            border.populateSequence("12345");
        } else {
            border.populateSolid(lineChar.charAt(0));
        }
        if (height == 1) {
            for (int i = 0; i < width; i++) {
                canvas.setCellText(left + i, top, border.nextChar());
            }
        } else if (width == 1) {
            for (int i = 0; i < height; i++) {
                canvas.setCellText(left, top + i, border.nextChar());
            }
        } else {
            drawFullBorder(canvas, border);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        drawBackground(canvas);
        drawInner(canvas);
        drawBorder(canvas);
    }

    @Override
    public void redact() {
        background = "solid";
        backgroundColor = "light gray";
        line = "char";
        lineChar = "+";
        fill = "solid";
        fillText = "X";
    }
}