import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Derived class that represents a while statement in the SILLY language.
 * 
 * @author Dave Reed & Owen McGrath
 * @version 1/20/25
 */
public class While extends Statement {
    private static final Logger logger = Logger.getLogger(While.class.getName());

    private Expression expr;
    private Compound body;

    /**
     * Reads in a while statement from the specified stream
     * 
     * @param input the stream to be read from
     */
    public While(TokenStream input) throws Exception {
        if (!input.next().toString().equals("while")) {
            throw new Exception("SYNTAX ERROR: Malformed while statement");
        }
        this.expr = new Expression(input);
        this.body = new Compound(input);
    }

    /**
     * Executes the current while statement.
     */
    public void execute() throws Exception {
        boolean keepLooping = true;
        int iteration = 0;

        logger.info("Starting while loop execution");

        while (keepLooping && iteration < 20) { // Add iteration limit for safety
            iteration++;
            logger.fine("While loop iteration " + iteration);

            DataValue eVal = this.expr.evaluate();
            if (eVal.getType() != DataValue.Type.BOOLEAN) {
                logger.warning("Non-boolean test expression in while loop");
                throw new Exception("RUNTIME ERROR: while statement requires Boolean test.");
            }

            if ((Boolean) eVal.getValue()) {
                logger.fine("Loop condition true, executing body");
                try {
                    this.body.execute();
                    logger.fine("Body executed without return");
                } catch (Return.ReturnException re) {
                    logger.log(Level.INFO, "Return exception caught in while loop with value: {0}",
                            re.getReturnValue());
                    logger.info("Re-throwing return exception");
                    throw re; // Critical: re-throw the exception
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error executing loop body", e);
                    throw e;
                }
            } else {
                logger.fine("Loop condition false, terminating loop");
                keepLooping = false;
            }
        }

        if (iteration >= 20) {
            logger.warning("Loop limit reached - possible infinite loop");
        }

        logger.info("While loop execution completed");
    }

    /**
     * Converts the current while statement into a String.
     * 
     * @return the String representation of this statement
     */
    public String toString() {
        return "while " + this.expr + " " + this.body;
    }
}