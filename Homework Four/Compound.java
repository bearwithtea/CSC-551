import java.util.ArrayList;

/**
 * Derived class that represents a compound statement in the SILLY language.
 *
 * @author Dave Reed & Owen McGrath
 * @version 1/20/25
 */
public class Compound extends Statement {
    private ArrayList<Statement> stmts;

    /**
     * Reads in a compound statement from the specified stream
     *
     * @param input the stream to be read from
     */
    public Compound(TokenStream input) throws Exception {
        if (!input.next().toString().equals("{")) {
            throw new Exception("SYNTAX ERROR: Malformed compound statement");
        }

        this.stmts = new ArrayList<Statement>();
        while (!input.lookAhead().toString().equals("}")) {
            this.stmts.add(Statement.getStatement(input));
        }
        input.next();
    }

    /**
     * Executes a compound statement by creating a new scope and executing all
     * contained statements sequentially.
     * 
     * @throws Return.ReturnException If a return statement is executed within the
     *                                compound block
     * @throws Exception              If any other exception occurs during statement
     *                                execution
     */
    public void execute() throws Exception {
        Interpreter.MEMORY.beginNestedScope(); // creates a new scope

        // try-catch block that attempts to iterate over the statements in the compound
        // block if it is unable to do so, it will close the scope and rethrow the error
        // indicating that a return statement was reached
        try {
            for (int i = 0; i < this.stmts.size(); i++) {
                Statement stmt = this.stmts.get(i);
                stmt.execute();
            }
        } catch (Return.ReturnException re) {
            Interpreter.MEMORY.endCurrentScope();
            throw re; // rethrow the return exception, indicate that a return statement was reached
        } catch (Exception e) {
            Interpreter.MEMORY.endCurrentScope();
            throw e; // rethrow the exception, indicating that an error occurred
        }

        Interpreter.MEMORY.endCurrentScope();
    }

    /**
     * Converts the current compound statement into a String.
     *
     * @return the String representation of this statement
     */
    public String toString() {
        String str = "{\n";
        for (Statement stmt : this.stmts) {
            str += "  " + stmt + "\n";
        }
        return str + "}";
    }
}