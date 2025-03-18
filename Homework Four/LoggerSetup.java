import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerSetup {
    public static void configureLogging() {
        Logger rootLogger = Logger.getLogger("");

        // Remove existing handlers
        for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }

        try {
            // Create and configure file handler
            FileHandler fileHandler = new FileHandler("silly_interpreter.log");
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            rootLogger.addHandler(fileHandler);

            // Set default log level
            rootLogger.setLevel(Level.INFO);

            // Configure individual class loggers
            Logger.getLogger(While.class.getName()).setLevel(Level.FINE);
            Logger.getLogger(If.class.getName()).setLevel(Level.FINE);
            Logger.getLogger(Compound.class.getName()).setLevel(Level.FINE);
            Logger.getLogger(Return.class.getName()).setLevel(Level.FINE);
        } catch (IOException e) {
            System.err.println("Could not create log file: " + e.getMessage());
        }
    }
}