package source.model.border;

/**
 * Interface that represent the character patter of a border.
 * Can have a border of characters, double lines, or a given sequence.
 */

public interface Border {
    char nextChar();

    void populateSolid(char solidChar);

    void populateASCII();

    void populateSequence(String sequence);
}
