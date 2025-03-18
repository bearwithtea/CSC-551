/**
 * Derived class that represents a return statement in the SILLY language.
 *
 * @author Owen McGrath
 * @version 2/20/25
 */
public class Return extends Statement {

    private Expression expr;
    public static final Token RETURN_VALUE_TOKEN = new Token("__return__"); // escape token

    /**
     * Reads in a return statement from the specified stream
     *
     * @param input the stream to be read from
     */
    public Return(TokenStream input) throws Exception {
        if (!input.next().toString().equals("return")) {
            throw new Exception("SYNTAX ERROR: Malformed return statement");
        }
        this.expr = new Expression(input);
    }

    /**
     * Executes the current return statement.
     */
    @Override
    public void execute() throws Exception {
        DataValue value = this.expr.evaluate();

        if (Interpreter.MEMORY.isDeclared(RETURN_VALUE_TOKEN))
            Interpreter.MEMORY.storeValue(RETURN_VALUE_TOKEN, value);
    }

    /**
     * Converts the current return statement into a String.
     *
     * @return the String representation of this statement
     */
    @Override
    public String toString() {
        return "return " + this.expr;
    }
}
