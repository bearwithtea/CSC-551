import java.util.HashMap;
import java.util.Scanner;

/**
 * Driver for the interactive SILLY Interpreter.
 * 
 * @author Dave Reed
 * @version 1/20/25
 */
public class Interpreter {

    public static MemorySpace MEMORY = new MemorySpace();
    private static HashMap<String, FunctionDecl> functions = new HashMap<>();

    /**
     * Registers a function declaration in the interpreter.
     * 
     * @param name     The name of the function
     * @param function The function declaration
     */
    public static void registerFunction(String name, FunctionDecl function) {
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
        System.out.print(
                "Enter the program file name or hit RETURN for interactive: ");
        Scanner input = new Scanner(System.in);
        String response = input.nextLine().strip();

        TokenStream inStream = new TokenStream();
        if (!response.equals("")) {
            inStream = new TokenStream(response);
        }

        while (response.equals("") || inStream.hasNext()) {
            System.out.print(">>> ");
            Statement stmt = Statement.getStatement(inStream); // for the interactive version

            if (!response.equals("")) {
                System.out.println(stmt);
            }

            try {
                stmt.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        input.close();
    }
}
