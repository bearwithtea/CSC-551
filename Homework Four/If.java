import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Derived class that represents an if statement in the SILLY language.
 * 
 * @author Dave Reed
 * @version 1/20/25
 */
public class If extends Statement {
    private static final Logger logger = Logger.getLogger(If.class.getName());

    private Expression test;
    private Compound ifBody;
    private Compound elseBody;

    /**
     * Reads in an if statement from the specified stream
     * 
     * @param input the stream to be read from
     */
    public If(TokenStream input) throws Exception {
        if (!input.next().toString().equals("if")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
        this.test = new Expression(input);
        this.ifBody = new Compound(input);

        if (!input.next().toString().equals("else")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }

        this.elseBody = new Compound(input);
    }

    /**
     * Executes the current if statement.
     */
    public void execute() throws Exception {
        logger.info("Executing if statement");

        DataValue test = this.test.evaluate();
        logger.fine("Test expression evaluated to: " + test);

        if (test.getType() != DataValue.Type.BOOLEAN) {
            logger.warning("Non-boolean test expression in if statement");
            throw new Exception("RUNTIME ERROR: If statement requires Boolean test.");
        }

        try {
            if (((Boolean) test.getValue())) {
                logger.fine("Condition true, executing if branch");
                this.ifBody.execute();
                logger.fine("If branch executed normally");
            } else {
                logger.fine("Condition false, executing else branch");
                this.elseBody.execute();
                logger.fine("Else branch executed normally");
            }
        } catch (Return.ReturnException re) {
            logger.log(Level.INFO, "Return exception caught in if statement with value: {0}", re.getReturnValue());
            logger.info("Re-throwing return exception from if statement");
            throw re; // Critical: re-throw the exception
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in if statement execution", e);
            throw e;
        }

        logger.info("If statement execution completed normally");
    }

    /**
     * Converts the current if statement into a String.
     * 
     * @return the String representation of this statement
     */
    public String toString() {
        return "if " + this.test + " " + this.ifBody + "\nelse " + this.elseBody;
    }
}