package lt.vu.mif.error;

import org.springframework.stereotype.Component;

@Component
public class ConsoleErrorLogger implements IErrorLogger {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    @Override
    public void logError(String error) {
        System.out.println(ANSI_RED + "ERROR OCCURRED: " + error + ANSI_RESET);
    }
}