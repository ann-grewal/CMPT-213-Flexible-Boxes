package source.model.border;

import java.util.ArrayList;

/**
 * Class that represents the character patter of a border that is bordered on all sides.
 */

public class BorderFull implements Border {
    final ArrayList<Character> borderCharacters = new ArrayList<>();
    private final int innerWidth;
    private final int innerHeight;
    private final int borderUnits;

    public BorderFull(int width, int height) {
        this.innerWidth = width - 2;
        this.innerHeight = height - 2;
        this.borderUnits = (width + innerHeight) * 2;
    }

    @Override
    public char nextChar() {
        return borderCharacters.removeFirst();
    }

    @Override
    public void populateSolid(char solidChar) {
        for (int i = 0; i < borderUnits; i++) {
            borderCharacters.add(solidChar);
        }
    }

    @Override
    public void populateASCII() {
        borderCharacters.add('╔');
        for (int i = 0; i < innerWidth; i++) {
            borderCharacters.add('═');
        }
        borderCharacters.add('╗');
        for (int i = 0; i < innerHeight; i++) {
            borderCharacters.add('║');
        }
        borderCharacters.add('╝');
        for (int i = 0; i < innerWidth; i++) {
            borderCharacters.add('═');
        }
        borderCharacters.add('╚');
        for (int i = 0; i < innerHeight; i++) {
            borderCharacters.add('║');
        }
    }

    @Override
    public void populateSequence(String sequence) {
        for (int i = 0; i < borderUnits; i++) {
            borderCharacters.add(sequence.charAt(i % sequence.length()));
        }
    }
}
