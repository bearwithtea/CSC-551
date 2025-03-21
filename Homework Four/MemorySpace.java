import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines the memory space for the SILLY interpreter.
 *
 * @author Dave Reed & Owen McGrath
 * @version 1/20/25, updated 3/10/25
 */
public class MemorySpace {
    private Stack<ScopeRec> runtimeStack; // represents the nested scopes
    private Map<String, FunctionDecl> functionMap; // create a map that stores the name of the function and the function
                                                   // declaration

    /**
     * Constructs a memory space with a single (global) scope record.
     */
    public MemorySpace() {
        this.runtimeStack = new Stack<ScopeRec>();
        this.runtimeStack.push(new ScopeRec(null));
        this.functionMap = new HashMap<String, FunctionDecl>();
    }

    /**
     * Adds a new scope to the top of the runtime stack (linked to previous top).
     */
    public void beginNestedScope() {
        this.runtimeStack.push(new ScopeRec(this.runtimeStack.peek())); // adds a new scope to the stack
    }

    /**
     * Removes the current scope from the top of the runtime stack.
     */
    public void endCurrentScope() {
        this.runtimeStack.pop(); // pops the current scope off the stack
    }

    /**
     * Declares a variable (without storing an actual value).
     *
     * @param variable the variable to be declared
     */
    public void declareVariable(Token variable) {
        this.runtimeStack.peek().storeInScope(variable, null);
    }

    /**
     * Determines if a variable is already declared.
     *
     * @param variable the variable to be found
     * @return true if it is declared and/or assigned; else, false
     */
    public boolean isDeclared(Token variable) {
        return (this.findScopeinStack(variable) != null);
    }

    /**
     * Stores a variable/value in the runtime stack.
     *
     * @param variable the variable name
     * @param val      the value to be stored under that name
     */
    public void storeValue(Token variable, DataValue val) {
        ScopeRec currentScope = this.runtimeStack.peek();
        ScopeRec existingScope = findScopeinStack(variable);

        // this code block handles scoping
        // 1. if the variable is declared the value is updated
        // 2. if the variable is not declared, it is declared in the current scope
        // 3. if the variable is found in any parent scope, it is updated there
        if (currentScope.declaredInScope(variable)) {
            currentScope.storeInScope(variable, val);
        } else if (existingScope == null) {
            declareVariable(variable);
            currentScope.storeInScope(variable, val);
        } else {
            existingScope.storeInScope(variable, val);
        }
    }

    /**
     * Determines the value associated with a variable in memory.
     *
     * @param variable the variable to look up
     * @return the value associated with that variable
     */
    public DataValue lookupValue(Token variable) {
        return this.findScopeinStack(variable).lookupInScope(variable);
    }

    /////////////////////////////////////////////////////////////////////////////

    /**
     * Locates the Scope in the stackSegment that contains the specified variable.
     *
     * @param variable the variable being searched for
     * @return the Scope containing that variable
     */
    private ScopeRec findScopeinStack(Token variable) {
        ScopeRec stepper = this.runtimeStack.peek();
        while (stepper != null && !stepper.declaredInScope(variable)) {
            stepper = stepper.getParentScope();
        }
        return stepper;
    }

    /**
     * Stores a function declaration in the function map.
     * 
     * @param functionName the name of the function
     * @param functionDecl the function declaration object
     */
    public void storeFunction(String functionName, FunctionDecl functionDecl) {
        functionMap.put(functionName, functionDecl);
    }

    /**
     * Retrieves a function declaration from the function map.
     * 
     * @param functionName the name of the function to retrieve
     * @return the function declaration associated with the name, or null if not
     *         found
     */
    public FunctionDecl lookupFunction(String functionName) {
        return functionMap.get(functionName);
    }

    /**
     * Determines if a function is already declared.
     * 
     * @param functionName the name of the function to check
     * @return true if the function is declared; else, false
     */
    public boolean isFunctionDeclared(String functionName) {
        return functionMap.containsKey(functionName);
    }
}
