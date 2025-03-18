import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Derived class that represents a compound statement in the SILLY language.
 *
 * @author Dave Reed
 * @version 1/20/25
 */
public class Compound extends Statement {
    private static final Logger logger = Logger.getLogger(Compound.class.getName());

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
     * Executes the current compound statement.
     * Now properly handles return statements by propagating the ReturnException.
     */
    public void execute() throws Exception {
        logger.info("Beginning compound statement execution");
        Interpreter.MEMORY.beginNestedScope();
        logger.fine("Nested scope created");

        try {
            for (int i = 0; i < this.stmts.size(); i++) {
                Statement stmt = this.stmts.get(i);
                logger.fine("Executing statement " + (i + 1) + " of " + this.stmts.size() + ": "
                        + stmt.getClass().getSimpleName());
                stmt.execute();
                logger.fine("Statement " + (i + 1) + " executed normally");
            }
            logger.info("All statements in compound executed normally");
        } catch (Return.ReturnException re) {
            logger.log(Level.INFO, "Return exception caught in compound with value: {0}", re.getReturnValue());
            Interpreter.MEMORY.endCurrentScope();
            logger.fine("Nested scope ended due to return");
            logger.info("Re-throwing return exception from compound");
            throw re; // Critical: re-throw the exception
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in compound statement", e);
            Interpreter.MEMORY.endCurrentScope();
            logger.fine("Nested scope ended due to error");
            throw e;
        }

        Interpreter.MEMORY.endCurrentScope();
        logger.fine("Nested scope ended normally");
        logger.info("Compound statement execution completed");
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