package TodoApplication;

import java.io.IOException;
import java.util.*;

public class Main {

    private static String errorMessage() {
        return "Correct example1: --csv-file todos.csv --add-todo hw9 --due 2022-02-22 --priority 2\n"
            + "Correct example2: --csv-file todos.csv --complete-todo 3\n"
            + "Correct example3: --csv-file todos.csv --display --sort-by-date\n";
    }

    public static void main(String[] args) {
        try {
            CommandLineParser parser = new CommandLineParser();
            CommandInfo commandInfo = parser.parse(args);
            TodoList todolist = Processor.readWrite(commandInfo);
            Processor.display(commandInfo, todolist);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("\n" + errorMessage());
        }
    }
}

