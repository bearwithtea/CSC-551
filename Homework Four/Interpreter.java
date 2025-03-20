import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Driver for the interactive SILLY Interpreter with output redirection to a
 * file.
 * 
 * @author Dave Reed (modified)
 * @version 1/20/25
 */
public class Interpreter {

    public static MemorySpace MEMORY = new MemorySpace();
    private static HashMap<String, FunctionDecl> functions = new HashMap<>();
    private static PrintWriter fileOutput;

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

    /**
     * Custom println method that writes only to file
     * 
     * @param message The message to print
     */
    public static void println(String message) {
        if (fileOutput != null) {
            fileOutput.println(message);
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            fileOutput = new PrintWriter(new FileWriter("actual.txt"));
        } catch (IOException e) {
            System.err.println("Error creating output file: " + e.getMessage());
            return;
        }

        System.out.print(
                "Enter the program file name or hit RETURN for interactive: ");
        Scanner input = new Scanner(System.in);
        String response = input.nextLine().strip();

        TokenStream inStream = new TokenStream();
        if (!response.equals("")) {
            inStream = new TokenStream(response);
        } else {
        }

        while (response.equals("") || inStream.hasNext()) {
            Statement stmt = Statement.getStatement(inStream);

            try {
                stmt.execute();
            } catch (Return.ReturnException re) {
                println("ERROR: Return statement outside of function");
            } catch (Exception e) {
                println(e.toString());
            }
        }

        input.close();
        fileOutput.close();
    }
}