/**
 * Class that represents a character value.
 *  @author Owen McGrath
 *  @version 2/4/25
 */
public class CharValue implements DataValue {

    protected final Character value;

    /**
     * Creates a default character value (empty).
     */
    public CharValue() {
        this('_');
    }

    /**
     * Constructs a character value.
     *   @param c the character being stored
     */
    public CharValue(char c) {
        this.value = c;
    }

    /**
     * Accesses the stored character value.
     *   @return the character value (as an Object)
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Identifies the actual type of the value.
     *   @return DataValue.Type.CHAR
     */
    public DataValue.Type getType() {
        return DataValue.Type.CHAR;
    }

    /**
     * Converts a character value to a String.
     *   @return a String representation of a character value
     */
    public String toString() {
        return "" + this.value;
    }

    /**
     * Comparison method for CharValues.
     *   @param other the value being compared with
     *   @return negative if <, 0 if ==, positive if >
     */
    public int compareTo(DataValue other) {
        return ((Character) this.getValue()).compareTo(
                (Character) other.getValue()
            );
    }
}
