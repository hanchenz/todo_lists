package TodoApplication;

import static org.junit.jupiter.api.Assertions.*;

import TodoApplication.DisplayOption.DisplayBuilder;
import TodoApplication.Todo.Builder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {
    CommandLineParser parser;
    CommandInfo info;

    @BeforeEach
    void setUp() {
        parser = new CommandLineParser();
    }

    private void testCommandLineParser(String[] args, Boolean isParsingSuccessful, CommandInfo expectedCommandInfo,
        String expectedExceptionMessage) {
        if (isParsingSuccessful) {
            assertTrue(expectedCommandInfo.equals(parser.parse(args)));
        } else {
            Exception exception = assertThrows(Exception.class, () -> {
                parser.parse(args);
            });
            assertTrue(exception.getMessage().equals(expectedExceptionMessage));
        }
    }

    @Test
    void parse_LegalTodo() {
        String[] args = {"--csv-file", "csvFile.csv", "--add-todo", "--todo-text", "hw9", "--due",
            "2022-04-19", "--category", "5004", "--completed", "--priority", "2"};
        Builder builder = new Builder("hw9").addCategory("5004").completeTodo().changePriority(2).
            addDue(LocalDate.of(2022, 4, 19));
        Todo todo = builder.build();
        List<Integer> completedTodoID = new ArrayList<>();
        info = new CommandInfo("csvFile.csv", todo, completedTodoID, null);
        testCommandLineParser(args, true, info, null);
    }

    @Test
    void parse_LegalTodo2() {
        String[] args = {"--csv-file", "csvFile.csv", "--add-todo", "--todo-text", "hw9", "--due",
            "2022-04-19"};
        Builder builder = new Builder("hw9").addDue(LocalDate.of(2022, 4, 19));
        Todo todo = builder.build();
        List<Integer> completedTodoID = new ArrayList<>();
        info = new CommandInfo("csvFile.csv", todo, completedTodoID, null);
        testCommandLineParser(args, true, info, null);
    }

    @Test
    void parse_LegalCompleteTodo() {
        String[] args = {"--csv-file", "csvFile.csv", "--complete-todo", "5"};
        List<Integer> completedTodoID = new ArrayList<>();
        completedTodoID.add(5);
        info = new CommandInfo("csvFile.csv", null, completedTodoID, null);
        testCommandLineParser(args, true, info, null);
    }

    @Test
    void parse_LegalDisplayOption() {
        String[] args = {"--csv-file", "csvFile.csv", "--display", "--show-incomplete", "--show-category", "5004", "--sort-by-date"};
        DisplayBuilder displayBuilder = new DisplayBuilder().showIncomplete().showCategory("5004").sortByDate();
        DisplayOption displayOption = displayBuilder.buildDisplayOption();
        List<Integer> completedTodoID = new ArrayList<>();
        info = new CommandInfo("csvFile.csv", null, completedTodoID, displayOption);
        testCommandLineParser(args, true, info, null);
    }

    @Test
    void parse_LegalDisplayOptionPriority() {
        String[] args = {"--csv-file", "csvFile.csv", "--display",  "--sort-by-priority"};
        DisplayBuilder displayBuilder = new DisplayBuilder().sortByPriority();
        DisplayOption displayOption = displayBuilder.buildDisplayOption();
        List<Integer> completedTodoID = new ArrayList<>();
        info = new CommandInfo("csvFile.csv", null, completedTodoID, displayOption);
        testCommandLineParser(args, true, info, null);
    }

    @Test
    void parse_IllegalPrefix() {
        String[] args = {"csv-file", "csvFile.csv", "--display",  "--sort-by-priority"};
        String expectedMessage = "Commands should start with '--'.";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_IllegalCommandLength() {
        String[] args = {"--csv-file", "csvFile.csv", "whatever", "--display",  "--sort-by-priority"};
        String expectedMessage = "Illegal command length.";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_IllegalCompleteID() {
        String[] args = {"--csv-file", "csvFile.csv", "--complete-todo", "five"};
        String expectedMessage = "Invalid ID after '--complete-todo'.";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_UnknownCommand() {
        String[] args = {"--csv-file", "csvFile.csv", "--complete-todo", "5", "--test"};
        String expectedMessage = "Unknown command: --test";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_DuplicatedCommand() {
        String[] args = {"--csv-file", "csvFile.csv", "--csv-file", "csvFile.csv"};
        String expectedMessage = "Duplicated command: --csv-file";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_MissingArgument() {
        String[] args = {"--csv-file", "csvFile.csv", "--display", "--show-incomplete", "--show-category"};
        String expectedMessage = "Missing argument for command: --show-category";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_RedundantArgument() {
        String[] args = {"--csv-file", "csvFile.csv", "--display", "--show-incomplete", "incomplete"};
        String expectedMessage = "Redundant argument for command: --show-incomplete";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_NoCsvFile() {
        String[] args = {"--complete-todo", "5"};
        String expectedMessage = "No csv file provided.";
        testCommandLineParser(args, false, null, expectedMessage);
    }


    @Test
    void parse_NoArgument() {
        String[] args = {"--csv-file", "csvFile.csv"};
        String expectedMessage = "Only csv file path provided, no argument provided.";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_NoTodoText() {
        String[] args = {"--csv-file", "csvFile.csv", "--add-todo", "--due", "2022-04-19", "--category", "5004"};
        String expectedMessage = "No '--todo-text' command provided.";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_InvalidDueTime() {
        String[] args = {"--csv-file", "csvFile.csv", "--add-todo", "--todo-text", "hw9", "--due", "today", "--category", "5004"};
        String expectedMessage = "Invalid due time.";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_IllegalPriorityType() {
        String[] args = {"--csv-file", "csvFile.csv", "--add-todo", "--todo-text", "hw9", "--due",
            "2022-04-19", "--category", "5004", "--priority", "three"};
        String expectedMessage = "Priority should be a number from 1 to 3.";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_IllegalPriorityNumberHigh() {
        String[] args = {"--csv-file", "csvFile.csv", "--add-todo", "--todo-text", "hw9", "--due",
            "2022-04-19", "--priority", "5"};
        String expectedMessage = "Priority should be a number from 1 to 3.";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void parse_IllegalPriorityNumberLow() {
        String[] args = {"--csv-file", "csvFile.csv", "--add-todo", "--todo-text", "hw9", "--category", "5004", "--priority", "0"};
        String expectedMessage = "Priority should be a number from 1 to 3.";
        testCommandLineParser(args, false, null, expectedMessage);
    }

    @Test
    void testEqual_SameMemoryLocation() {
        assertTrue(parser.equals(parser));
    }

    @Test
    void testEqual_NullObject() {
        assertFalse(parser.equals(null));
    }

    @Test
    void testEqual_DifferentDataType() {
        assertFalse(parser.equals(info));
    }

    @Test
    void testEqual_SameField() {
        CommandLineParser newParser = new CommandLineParser();
        assertTrue(parser.equals(newParser));
    }

    @Test
    void testEqual_DifferentMap() {
        String[] args = {"--csv-file", "csvFile.csv", "--add-todo", "--todo-text", "hw9", "--due",
            "2022-04-19"};
        CommandLineParser newParser = new CommandLineParser();
        newParser.parse(args);
        assertFalse(parser.equals(newParser));
    }

    @Test
    void testEqual_DifferentList() {
        String[] args = {"--csv-file", "csvFile.csv", "--complete-todo", "1"};
        String[] args2 = {"--csv-file", "csvFile.csv", "--complete-todo", "2"};
        CommandLineParser newParser = new CommandLineParser();
        newParser.parse(args);
        parser.parse(args2);
        assertFalse(parser.equals(newParser));
    }

    @Test
    void testHashCode() {
        CommandLineParser newParser = new CommandLineParser();
        assertEquals(newParser.hashCode(), parser.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "CommandLineParser{" +
            "commands=" + parser.getCommands()+
            ", completeID=" + parser.getCompleteID() +
            '}';
        assertEquals(expectedString, parser.toString());
    }

}