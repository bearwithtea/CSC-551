import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a string value.
 *  @author Owen McGrath
 *  @version 2/07/25
 */
public class StringValue extends ListValue {

    /**
     * Constructs a default string value.
     */
    public StringValue() {
        super(); //calls the ListValue constructor, initializes the value to an empty list
    }

    /**
     * Constructs a string value.
     *   @param str the string being stored
     */
    public StringValue(String str) {
        super();
        //for each character in the string, add a new CharValue to the list
        for (char c : str.toCharArray()) {
            @SuppressWarnings("unchecked")
            List<DataValue> value = (List<DataValue>) super.getValue();
            value.add(new CharValue(c));
        }
    }

    /**
     * Constructs a string value.
     * @param chars
     */
    public StringValue(ArrayList<DataValue> chars) {
        super(chars);
    }

    /**
     * Accesses the stored string value.
     *   @return the string value (as an Object)
     */
    @Override
    public DataValue.Type getType() {
        return DataValue.Type.STRING; //returns the type of the value
    }

    /**
     * Converts the string value to a String.
     *  @return a String representation of the string value
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        @SuppressWarnings("unchecked") //not fixing all that
        List<DataValue> value = (List<DataValue>) super.getValue();
        for (DataValue v : value) {
            result.append(v.toString()); //appends the string representation of each character in the list
        }
        return result.toString();
    }

    /**
     * Comparison method for StringValues.
     *   @param other the value being compared with
     */
    @Override
    public int compareTo(DataValue other) {
        return this.toString().compareTo(other.toString());
    }
}
