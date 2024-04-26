package source.model.border;

import java.util.ArrayList;

/**
 * Class that represents the character patter of a border that is bordered on one side.
 */

public class BorderPartial implements Border {
    final ArrayList<Character> borderCharacters = new ArrayList<>();
    private final int borderUnits;

    public BorderPartial(int width, int height) {
        this.borderUnits = width * height;
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
        for (int i = 0; i < borderUnits; i++) {
            borderCharacters.add('■');
        }
    }

    @Override
    public void populateSequence(String sequence) {
        for (int i = 0; i < borderUnits; i++) {
            borderCharacters.add(sequence.charAt(i % sequence.length()));
        }
    }
}
