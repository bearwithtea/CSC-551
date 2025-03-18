import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Driver for the interactive SILLY Interpreter.
 * 
 * @author Dave Reed
 * @version 1/20/25
 */
public class Interpreter {
    private static final Logger logger = Logger.getLogger(Interpreter.class.getName());

    public static MemorySpace MEMORY = new MemorySpace();
    private static HashMap<String, FunctionDecl> functions = new HashMap<>();

    /**
     * Registers a function declaration in the interpreter.
     * 
     * @param name     The name of the function
     * @param function The function declaration
     */
    public static void registerFunction(String name, FunctionDecl function) {
        logger.fine("Registering function: " + name);
        functions.put(name, function);
    }

    /**
     * Gets a function declaration by name.
     * 
     * @param name The name of the function
     * @return The function declaration, or null if not found
     */
    public static FunctionDecl getFunction(String name) {
        return functions.get(name);
    }

    public static void main(String[] args) throws Exception {
        // Configure logging
        LoggerSetup.configureLogging();
        logger.info("SILLY Interpreter starting");

        System.out.print(
                "Enter the program file name or hit RETURN for interactive: ");
        Scanner input = new Scanner(System.in);
        String response = input.nextLine().strip();

        TokenStream inStream = new TokenStream();
        if (!response.equals("")) {
            logger.info("Loading program from file: " + response);
            inStream = new TokenStream(response);
        } else {
            logger.info("Starting interactive mode");
        }

        while (response.equals("") || inStream.hasNext()) {
            System.out.print(">>> ");
            Statement stmt = Statement.getStatement(inStream); // for the interactive version

            if (!response.equals("")) {
                System.out.println(stmt);
            }

            try {
                logger.fine("Executing statement: " + stmt);
                stmt.execute();
                logger.fine("Statement executed successfully");
            } catch (Exception e) {
                logger.severe("Error executing statement: " + e.getMessage());
                System.out.println(e);
            }
        }

        logger.info("SILLY Interpreter shutting down");
        input.close();
    }
}