package TodoApplication;

import java.util.List;
import java.util.Objects;

/**
 * CommandInfo keeps track of csv file path, new todo item to be added, ID of todos which intended
 * to be completed, and the display option.
 */
public class CommandInfo {

    private final String csvPath;
    private final Todo newTodo;
    private final List<Integer> completedTodoID; // if no ID provided, completeTodoID would be an empty list
    private final DisplayOption displayOption;

    /**
     * Constructor, create a new CommandInfo object with given information.
     * @param csvPath - csv file path, encoded as String
     * @param newTodo - a new todo to be added, encoded as Todo
     * @param completedTodoID - ID of todo whose status intended to be completed, encoded as List of Integer
     * @param displayOption - display option, encoded as DisplayOption
     */
    public CommandInfo(String csvPath, Todo newTodo, List<Integer> completedTodoID,
        DisplayOption displayOption) {
        this.csvPath = csvPath;
        this.newTodo = newTodo;
        this.completedTodoID = completedTodoID;
        this.displayOption = displayOption;
    }

    /**
     * Get csv file path
     * @return csv file path
     */
    public String getCsvPath() {
        return csvPath;
    }

    /**
     * Get new todo
     * @return new todo
     */
    public Todo getNewTodo() {
        return newTodo;
    }

    /**
     * Get competed todo ID
     * @return competed todo ID
     */
    public List<Integer> getCompletedTodoID() {
        return completedTodoID;
    }

    /**
     * Get display option
     * @return display option
     */
    public DisplayOption getDisplayOption() {
        return displayOption;
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
        CommandInfo that = (CommandInfo) o;
        return Objects.equals(csvPath, that.csvPath) && Objects.equals(newTodo,
            that.newTodo) && Objects.equals(completedTodoID, that.completedTodoID)
            && Objects.equals(displayOption, that.displayOption);
    }

    /**
     * Get hash code
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(csvPath, newTodo, completedTodoID, displayOption);
    }

    /**
     * To string
     * @return string
     */
    @Override
    public String toString() {
        return "CommandInfo{" +
            "csvPath='" + csvPath + '\'' +
            ", newTodo=" + newTodo +
            ", completedTodoID=" + completedTodoID +
            ", displayOption=" + displayOption +
            '}';
    }
}