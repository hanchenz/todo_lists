package TodoApplication;

import TodoApplication.DisplayOption.DisplayBuilder;
import TodoApplication.Todo.Builder;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The CommandLineParser class provides a method to parse command line arguments. We assume a valid
 * argument could contain four parts, including
 * 1. csv file (eg: --csv-file &lt;path/to/file&gt;)
 * 2. new todo commands
 * 3. compete todo
 * 4. display todo
 * The csv file is required and other three are optional, but the user should provide at least one
 * operation command.
 */
public class CommandLineParser {
    private Map<String, String> commands;
    private List<Integer> completeID;

    private static final int FIRST = 1;
    private static final int SEC = 2;
    private static final int ONE_ELEMENT = 1;

    /**
     * Get commands
     * @return commands
     */
    public Map<String, String> getCommands() {
        return commands;
    }

    /**
     * Get complete ID
     * @return complete ID
     */
    public List<Integer> getCompleteID() {
        return completeID;
    }

    /**
     * Process the input arguments, put them into a map with commands as keys and corresponding
     * arguments as values, and the complete-todo ID add to an ArrayList.
     *
     * @param args - arguments, encoded as String Array
     * @throws IllegalArgumentException if the input argument is not start with '--'
     * @throws IllegalArgumentException if commands' lengths is larger than 2
     * @throws IllegalArgumentException if invalid ID provided for '--complete-todo' command
     * @throws IllegalArgumentException if unknown command provided
     * @throws IllegalArgumentException if duplicated commands provided (commands other than '--complete-todo')
     * @throws IllegalArgumentException if missing or redundant arguments provided
     */
    private void processArgs(String[] args) {
        this.commands = new HashMap<>();
        this.completeID = new ArrayList<>();

        int i = 0;
        // check if the arguments start with '--'
        if (!args[i].startsWith(Commands.PREFIX)) {
            throw new IllegalArgumentException("Commands should start with '--'.");
        }

        while (i < args.length) {
            String key;
            String value = null;
            if (args[i].startsWith(Commands.PREFIX)) {
                key = args[i];
                // check the commands' length, if a command has more than one argument, throw exception
                if ((i + FIRST) < args.length && !args[i + FIRST].startsWith(Commands.PREFIX)) {
                    value = args[i + FIRST];
                    if ((i + SEC) < args.length && !args[i + SEC].startsWith(Commands.PREFIX)) {
                        throw new IllegalArgumentException("Illegal command length.");
                    }
                }
                // if the command is '--compete-todo', add to completeID list
                if (key.equals(Commands.COMPLETE_TODO)) {
                    try {
                        completeID.add(Integer.parseInt(value));
                        i++;
                        continue;
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid ID after '--complete-todo'.");
                    }
                }

                // check if the command is an unknown command
                if (!Commands.VALID_COMMANDS.containsKey(key)) {
                    throw new IllegalArgumentException("Unknown command: " + key);
                }
                // check if the command is duplicated
                if (commands.containsKey(key)) {
                    throw new IllegalArgumentException("Duplicated command: " + key);
                }

                // check if the number of argument is right
                if ((value == null) == Commands.VALID_COMMANDS.get(key)) {
                    String prefix = Commands.VALID_COMMANDS.get(key) ? "Missing" : "Redundant";
                    throw new IllegalArgumentException(prefix + " argument for command: " + key);
                }
                // if everything is valid, add the command and argument to commands Map
                commands.put(key, value);
            }
            i++;
        }
    }

    /**
     * Parse the input arguments to a valid CommandInfo
     * @param args - arguments, encoded as String Array
     * @return a CommandInfo object
     * @throws IllegalArgumentException if no csv file provided
     * @throws IllegalArgumentException if only provide csv file path without other operation commands
     */
    public CommandInfo parse(String[] args) {
        processArgs(args);
        if (!commands.containsKey(Commands.CSV_FILE)) {
            throw new IllegalArgumentException("No csv file provided.");
        }
        if (commands.size() == ONE_ELEMENT && this.completeID.isEmpty()) {
            throw new IllegalArgumentException("Only csv file path provided, no argument provided.");
        }

        String csvPath = getCsvPath(commands);
        Todo newTodo = getTodoInfo(commands);
        List<Integer> completedTodoID = this.completeID;
        DisplayOption displayOption = getDisplayOption(commands);

        return new CommandInfo(csvPath, newTodo, completedTodoID, displayOption);
    }

    /**
     * Get the csv path
     * @param commands - commands, encoded as Map
     * @return the csv path
     */
    private String getCsvPath(Map<String, String> commands) {
        String csvPath = commands.get(Commands.CSV_FILE);
        return csvPath;
    }

    /**
     * Get the new todo information
     * @param commands - commands, encoded as Map
     * @return a Todo object, if no add-todo provided, return null
     */
    private Todo getTodoInfo(Map<String, String> commands) {
        Builder builder;
        if (!commands.containsKey(Commands.ADD_TODO)) {
            return null;
        }
        builder = buildTodo(commands);
        Todo todoInfo = builder.build();
        return todoInfo;
    }

    /**
     * Builder that help to build the Todo object
     * @param commands - commands, encoded as Map
     * @return a Builder object
     * @throws IllegalArgumentException if no '--todo-text' provided
     * @throws IllegalArgumentException if the due time is invalid, a valid time should be YYYY-MM-DD
     * @throws IllegalArgumentException if the priority is not valid, a valid priority should be 1, 2 or 3
     */
    private Builder buildTodo(Map<String, String> commands) {
        Builder builder;
        if (!commands.containsKey(Commands.TODO_TEXT)) {
            throw new IllegalArgumentException("No '--todo-text' command provided.");
        }
        builder = new Builder(commands.get(Commands.TODO_TEXT));

        if (commands.containsKey(Commands.COMPLETED)) {
            builder.completeTodo();
        }

        if (commands.containsKey(Commands.DUE)) {
            try {
                builder.addDue(LocalDate.parse(commands.get(Commands.DUE)));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid due time.");
            }
        }

        if (commands.containsKey(Commands.PRIORITY)) {
            try {
                int priority = Integer.parseInt(commands.get(Commands.PRIORITY));
                if (priority < Commands.HIGHEST_PRIORITY || priority > Commands.LOWEST_PRIORITY) {
                    throw new IllegalArgumentException("Priority should be a number from 1 to 3.");
                }
                builder.changePriority(Integer.parseInt(commands.get(Commands.PRIORITY)));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Priority should be a number from 1 to 3.");
            }
        }

        if (commands.containsKey(Commands.CATEGORY)) {
            builder.addCategory(commands.get(Commands.CATEGORY));
        }
        return builder;
    }

    /**
     * Get the information of display option
     * @param commands - commands, encoded as Map
     * @return a DisplayOption object, if no display information included in commands, return null
     */
    private DisplayOption getDisplayOption(Map<String, String> commands) {
        DisplayOption displayOption;
        DisplayBuilder displayBuilder;
        if (!commands.containsKey(Commands.DISPLAY)) {
            return null;
        }
        displayBuilder = buildDisplayOption(commands);
        displayOption = displayBuilder.buildDisplayOption();
        return displayOption;
    }

    /**
     * A display builder that help to build a DisplayOption
     * @param commands - commands, encoded as Map
     * @return a DisplayBuilder
     */
    private DisplayBuilder buildDisplayOption(Map<String, String> commands) {
        DisplayBuilder displayBuilder = new DisplayBuilder();
        if (commands.containsKey(Commands.SHOW_INCOMPLETE)) {
            displayBuilder.showIncomplete();
        }
        if (commands.containsKey(Commands.SHOW_CATEGORY)) {
            displayBuilder.showCategory(commands.get(Commands.SHOW_CATEGORY));
        }
        if (commands.containsKey(Commands.SORT_BY_DATE)) {
            displayBuilder.sortByDate();
        }
        if (commands.containsKey(Commands.SORT_BY_PRIORITY)) {
            displayBuilder.sortByPriority();
        }
        return displayBuilder;
    }

    /**
     * Check if o is equal to this
     * @param o - object to be compared
     * @return true if two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandLineParser that = (CommandLineParser) o;
        return Objects.equals(commands, that.commands) && Objects.equals(completeID,
            that.completeID);
    }

    /**
     * Get the hash code
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(commands, completeID);
    }

    /**
     * to string
     * @return the string
     */
    @Override
    public String toString() {
        return "CommandLineParser{" +
            "commands=" + commands +
            ", completeID=" + completeID +
            '}';
    }
}
