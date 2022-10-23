package TodoApplication;

import static org.junit.jupiter.api.Assertions.*;

import TodoApplication.DisplayOption.DisplayBuilder;
import TodoApplication.Todo.Builder;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandInfoTest {
    CommandInfo testCommandInfo;
    Todo testTodo;
    List<Integer> testID = new ArrayList<>();
    DisplayOption testDisplayOption;

    @BeforeEach
    void setUp() {
        Todo.Builder builder = new Builder("test-todo");
        testTodo = builder.build();
        testID.add(1);
        DisplayOption.DisplayBuilder displayBuilder = new DisplayBuilder();
        testDisplayOption = displayBuilder.buildDisplayOption();
        testCommandInfo = new CommandInfo("csvFile.csv", testTodo, testID, testDisplayOption);
    }

    @Test
    void getCsvPath() {
        assertTrue(testCommandInfo.getCsvPath().equals("csvFile.csv"));
    }

    @Test
    void getNewTodo() {
        assertTrue(testCommandInfo.getNewTodo().equals(testTodo));
    }

    @Test
    void getCompletedTodoID() {
        assertTrue(testCommandInfo.getCompletedTodoID().equals(testID));
    }

    @Test
    void getDisplayOption() {
        assertTrue(testCommandInfo.getDisplayOption().equals(testDisplayOption));
    }

    @Test
    void testEquals_SameMemoryLocation() {
        assertTrue(testCommandInfo.equals(testCommandInfo));
    }

    @Test
    void testEquals_SameFields() {
        CommandInfo newTestCommandInfo = new CommandInfo("csvFile.csv", testTodo, testID, testDisplayOption);
        assertTrue(testCommandInfo.equals(newTestCommandInfo));
    }

    @Test
    void testEquals_NullObject() {
        assertFalse(testCommandInfo.equals(null));
    }

    @Test
    void testEquals_DifferentDataType() {
        assertFalse(testCommandInfo.equals(testTodo));
    }

    @Test
    void testEquals_DifferentCsvFile() {
        CommandInfo newTestCommandInfo = new CommandInfo("newCsvFile.csv", testTodo, testID, testDisplayOption);
        assertFalse(testCommandInfo.equals(newTestCommandInfo));
    }

    @Test
    void testEquals_DifferentTodo() {
        Todo.Builder newBuilder = new Builder("new-test-todo");
        Todo newTestTodo = newBuilder.build();
        CommandInfo newTestCommandInfo = new CommandInfo("csvFile.csv", newTestTodo, testID, testDisplayOption);
        assertFalse(testCommandInfo.equals(newTestCommandInfo));
    }

    @Test
    void testEquals_DifferentCompletedID() {
        List<Integer> newTestID = new ArrayList<>();
        newTestID.add(2);
        CommandInfo newTestCommandInfo = new CommandInfo("csvFile.csv", testTodo, newTestID, testDisplayOption);
        assertFalse(testCommandInfo.equals(newTestCommandInfo));
    }

    @Test
    void testEquals_DifferentDisplayOption() {
        DisplayOption.DisplayBuilder newDisplayBuilder = new DisplayBuilder();
        DisplayOption newTestDisplayOption = newDisplayBuilder.sortByDate().buildDisplayOption();
        CommandInfo newTestCommandInfo = new CommandInfo("csvFile.csv", testTodo, testID, newTestDisplayOption);
        assertFalse(testCommandInfo.equals(newTestCommandInfo));
    }

    @Test
    void testHashCode() {
        CommandInfo newTestCommandInfo = new CommandInfo("csvFile.csv", testTodo, testID, testDisplayOption);
        assertEquals(testCommandInfo.hashCode(), newTestCommandInfo.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "CommandInfo{" +
            "csvPath='" + "csvFile.csv" + '\'' +
            ", newTodo=" + testTodo.toString() +
            ", completedTodoID=" + testID +
            ", displayOption=" + testDisplayOption.toString() +
            '}';
        assertEquals(expectedString, testCommandInfo.toString());
    }
}